import * as qrcode from 'qrcode';
import JsBarcode from 'jsbarcode';

export class CodeUtil {
  static getQrCodeData(data: string): any {
    var qrCode;
    qrcode.toDataURL(data, (error: any, qrCodeDataURL: any) => {
      if (error) {
        console.error('Error generating QR code:', error);
      } else {
        console.log('QR code generated successfully');
        qrCode = qrCodeDataURL;
      }
    });
    return qrCode;
  }

  static getBarCodeData(data: string, format: string, displayValue: boolean, width: number, height: number): string {
    console.log('START getBarCodeData()');
    console.log(data);
    console.log(format);
    console.log(displayValue);
    console.log(width);
    console.log(height);
    var qrCode = '';
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');

    const options = {
      format: format ? format : 'CODE128',
      displayValue: displayValue ? displayValue : false,
      width: width ? width : 100,
      height: height ? height : 30,
    };

    console.log(canvas);
    console.log(data);
    console.log(options);
    JsBarcode(canvas, data, options);
    qrCode = canvas.toDataURL();

    console.log(qrCode);
    console.log('END getBarCodeData()');
    return qrCode;
  }
}
