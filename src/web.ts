import { WebPlugin } from '@capacitor/core';

import type { LpapiPluginPlugin, PrinterAddress, PrinterOptions } from './definitions';

export class LpapiPluginWeb extends WebPlugin implements LpapiPluginPlugin {

  async getPrinterState(): Promise<any> {
    return Promise.resolve();
  }

  async printText1DBarcode(options: PrinterOptions): Promise<any> {
    return options;
  }

  async printText(options: PrinterOptions): Promise<any> {
    return options;
  }

  async disconnectPrinter(): Promise<any> {
    return Promise.resolve();
  }

  async connectPrinter(options: PrinterAddress): Promise<any> {
    return options;
  }

  async getPairedPrinters(): Promise<any> {
    return Promise.resolve();
  }

}
