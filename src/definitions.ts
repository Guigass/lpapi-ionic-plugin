import {Plugin} from "@capacitor/core/types/definitions";
export interface LpapiPluginPlugin extends Plugin {
  getPairedPrinters(): Promise<any>;
  connectPrinter(options: PrinterAddress): Promise<any>;
  disconnectPrinter(): Promise<any>;
  printText(options: PrinterOptions): Promise<any>;
  printText1DBarcode(options: PrinterOptions): Promise<any>;
  getPrinterState(): Promise<any>;
}

export interface PrinterAddress {
  name: string;
  address: string;
}

export interface PrinterOptions {
  text: string;
  x: number;
  y: number;
  width: number;
  height: number;
  textHeight: number;
  labelWidth: number;
  labelHeight: number;
}
