import { IPrintingCentreSig } from 'app/entities/printing-centre-sig/printing-centre-sig.model';

export interface IUser {
  id: number | null;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  printingCentre?: Pick<IPrintingCentreSig, 'printingCentreId' | 'printingCentreName' | 'event'> | null;
}

export class User implements IUser {
  constructor(
    public id: number | null,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public printingCentre?: Pick<IPrintingCentreSig, 'printingCentreId' | 'printingCentreName' | 'event'> | null
  ) {}
}
