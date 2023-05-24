import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';

export class Util {
  static createCopy(objectToCopy: IAccreditationSig): IAccreditationSig {
    return JSON.parse(JSON.stringify(objectToCopy));
  }
  static extractNumericValue(value: string, unite: string): number {
    const numericPart = value.substring(0, value.indexOf(unite));
    return parseInt(numericPart, 10);
  }
}
