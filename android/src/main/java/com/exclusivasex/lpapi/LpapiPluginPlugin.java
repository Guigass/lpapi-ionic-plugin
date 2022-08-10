package com.exclusivasex.lpapi;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.lpapi.LPAPI.BarcodeType;
import com.dothantech.printer.IDzPrinter;
import com.dothantech.printer.IDzPrinter.PrintParamName;
import com.dothantech.printer.IDzPrinter.PrintProgress;
import com.dothantech.printer.IDzPrinter.PrinterAddress;
import com.dothantech.printer.IDzPrinter.PrinterInfo;
import com.dothantech.printer.IDzPrinter.PrinterState;
import com.dothantech.printer.IDzPrinter.ProgressInfo;

import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(name = "LpapiPlugin")
public class LpapiPluginPlugin extends Plugin {

  private final LPAPI.Callback mCallback = new LPAPI.Callback() {
    @Override
    public void onStateChange(PrinterAddress arg0, PrinterState arg1) {
      JSObject ret = new JSObject();

      ret.put("PrinterState", arg1);

      notifyListeners("PrinterState", ret);
    }

    @Override
    public void onProgressInfo(ProgressInfo arg0, Object arg1) {
    }

    @Override
    public void onPrinterDiscovery(PrinterAddress arg0, PrinterInfo arg1) {
    }

    @Override
    public void onPrintProgress(PrinterAddress address, Object bitmapData, PrintProgress progress, Object addiInfo) {
      JSObject ret = new JSObject();

      ret.put("PrintProgress", progress);

      notifyListeners("PrintProgress", ret);
    }
  };

  private LPAPI api = LPAPI.Factory.createInstance(mCallback);

  private List<PrinterAddress> pairedPrinters = new ArrayList<PrinterAddress>();
  private PrinterAddress mPrinterAddress = null;

  @PluginMethod
  public void getPairedPrinters(PluginCall call) {
    pairedPrinters = api.getAllPrinterAddresses(null);

    JSArray jsArray = new JSArray();

    for (PrinterAddress item : pairedPrinters) {
      JSObject printer = new JSObject();

      printer.put("name", item.shownName);
      printer.put("address", item.macAddress);

      jsArray.put(printer);
    }

    JSObject ret = new JSObject();
    ret.put("printers", jsArray);

    call.resolve(ret);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @PluginMethod
  public void connectPrinter(PluginCall call) {
    String name = call.getString("name");
    String address = call.getString("address");

    mPrinterAddress = pairedPrinters.stream()
      .filter(printer -> address.equals(printer.macAddress))
      .findAny()
      .orElse(null);

    api.openPrinterByAddress(mPrinterAddress);

    call.resolve();
  }

  @PluginMethod
  public void disconnectPrinter(PluginCall call) {
    api.closePrinter();

    call.resolve();
  }

  @PluginMethod
  public void printText(PluginCall call) {
    String text = "1234567890";

    api.startJob(48, 50, 0);

    api.drawText(text, 4, 5, 40, 40, 4);
    api.commitJob();

    call.resolve();
  }

  @PluginMethod
  public void printText1DBarcode(PluginCall call) {
    String text = call.getString("text");
    double x = call.getDouble("x");
    double y = call.getDouble("y");
    double width = call.getDouble("width");
    double height = call.getDouble("height");
    double textHeight = call.getDouble("textHeight");
    double labelWidth = call.getDouble("labelWidth");
    double labelHeight = call.getDouble("labelHeight");

    api.startJob(labelWidth, labelHeight, 0);
    api.draw1DBarcode(text, BarcodeType.CODE128, x, y, width, height, textHeight);

    api.commitJob();

    call.resolve();
  }

  @PluginMethod
  public void getPrinterState(PluginCall call) {
    PrinterState state = api.getPrinterState();

    JSObject ret = new JSObject();

    ret.put("PrinterState", state);

    notifyListeners("PrinterState", ret);

    call.resolve();
  }
}
