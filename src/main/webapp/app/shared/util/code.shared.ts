import * as qrcode from 'qrcode';

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

  static getBarCodeData(data: string): string {
    var qrCode = '';

    return qrCode;
  }
}
