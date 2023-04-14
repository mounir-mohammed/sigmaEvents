import { IPrintingCentreSig } from 'app/entities/printing-centre-sig/printing-centre-sig.model';
export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public imageUrl: string | null,
    public printingCentre?: Pick<IPrintingCentreSig, 'printingCentreId' | 'printingCentreName' | 'event'> | null
  ) {}
}
