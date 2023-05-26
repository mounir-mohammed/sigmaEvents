import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';

export class Util {
  static createCopy(objectToCopy: IAccreditationSig): IAccreditationSig {
    return JSON.parse(JSON.stringify(objectToCopy));
  }
  static extractNumericValue(value: string, unite: string): number {
    const numericPart = value.substring(0, value.indexOf(unite));
    return parseInt(numericPart, 10);
  }

  static detectMobileDevice(): boolean {
    const userAgent = navigator.userAgent.toLowerCase();

    // Check for common keywords indicating mobile devices
    if (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(userAgent)) {
      return true;
    }

    // Check for specific keywords or screen size for further validation
    // For example, you can check for 'Mobile' in the userAgent string or use window.innerWidth

    return false;
  }
}
