import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';

export class Util {
  static createCopy(objectToCopy: IAccreditationSig): IAccreditationSig {
    return JSON.parse(JSON.stringify(objectToCopy));
  }
}
