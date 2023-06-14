import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { AccreditationTypeSigService } from 'app/entities/accreditation-type-sig/service/accreditation-type-sig.service';
import { AttachementSigService } from 'app/entities/attachement-sig/service/attachement-sig.service';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { CivilitySigService } from 'app/entities/civility-sig/service/civility-sig.service';
import { CodeSigService } from 'app/entities/code-sig/service/code-sig.service';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { DayPassInfoSigService } from 'app/entities/day-pass-info-sig/service/day-pass-info-sig.service';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { FonctionSigService } from 'app/entities/fonction-sig/service/fonction-sig.service';
import { NationalitySigService } from 'app/entities/nationality-sig/service/nationality-sig.service';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { SexeSigService } from 'app/entities/sexe-sig/service/sexe-sig.service';
import { SiteSigService } from 'app/entities/site-sig/service/site-sig.service';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { Authority } from 'app/config/authority.constants';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { IAccreditationSig, NewAccreditationSig } from '../accreditation-sig.model';
import { TranslateService } from '@ngx-translate/core';
import * as XLSX from 'xlsx';
import { ExportUtil } from 'app/shared/util/export.shared';
import dayjs from 'dayjs/esm';
import { RECORD_ITEMS } from 'app/config/pagination.constants';
import { Account } from 'app/core/auth/account.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  templateUrl: './accreditation-sig-import-dialog.component.html',
  styleUrls: ['./accreditation-sig-import-dialog.component.scss'],
})
export class AccreditationSigImportDialogComponent implements OnInit {
  accreditations?: IAccreditationSig[] = [];
  selectedFile: File | undefined;
  status?: IStatusSig;
  authority = Authority;
  errorsMap: Map<number, { firstName: string; lastName: string; occupation: string; errors: string; hasErrors: boolean }> = new Map();
  isLoading = false;
  isImporting = false;
  currentAccount: Account | null = null;

  importForm = new FormGroup({
    event: new FormControl(),
  });

  sitesSharedCollection: ISiteSig[] = [];
  eventsSharedCollection: IEventSig[] = [];
  civilitiesSharedCollection: ICivilitySig[] = [];
  sexesSharedCollection: ISexeSig[] = [];
  nationalitiesSharedCollection: INationalitySig[] = [];
  countriesSharedCollection: ICountrySig[] = [];
  citiesSharedCollection: ICitySig[] = [];
  categoriesSharedCollection: ICategorySig[] = [];
  fonctionsSharedCollection: IFonctionSig[] = [];
  organizsSharedCollection: IOrganizSig[] = [];
  accreditationTypesSharedCollection: IAccreditationTypeSig[] = [];
  statusesSharedCollection: IStatusSig[] = [];
  attachementsSharedCollection: IAttachementSig[] = [];
  codesSharedCollection: ICodeSig[] = [];
  dayPassInfosSharedCollection: IDayPassInfoSig[] = [];

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService,
    protected siteService: SiteSigService,
    protected eventService: EventSigService,
    protected civilityService: CivilitySigService,
    protected sexeService: SexeSigService,
    protected nationalityService: NationalitySigService,
    protected countryService: CountrySigService,
    protected cityService: CitySigService,
    protected categoryService: CategorySigService,
    protected fonctionService: FonctionSigService,
    protected organizService: OrganizSigService,
    protected accreditationTypeService: AccreditationTypeSigService,
    protected statusService: StatusSigService,
    protected attachementService: AttachementSigService,
    protected codeService: CodeSigService,
    protected dayPassInfoService: DayPassInfoSigService,
    protected translateService: TranslateService,
    private cdr: ChangeDetectorRef,
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmImport(): void {}

  protected loadRelationshipsOptions(): void {
    this.siteService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ISiteSig[]>) => res.body ?? []))
      .subscribe((sites: ISiteSig[]) => (this.sitesSharedCollection = sites));

    if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
      this.eventService
        .query({ size: RECORD_ITEMS })
        .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
        .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
    }

    this.civilityService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICivilitySig[]>) => res.body ?? []))
      .subscribe((civilities: ICivilitySig[]) => (this.civilitiesSharedCollection = civilities));

    this.sexeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ISexeSig[]>) => res.body ?? []))
      .subscribe((sexes: ISexeSig[]) => (this.sexesSharedCollection = sexes));

    this.nationalityService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<INationalitySig[]>) => res.body ?? []))
      .subscribe((nationalities: INationalitySig[]) => (this.nationalitiesSharedCollection = nationalities));

    this.countryService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? []))
      .subscribe((countries: ICountrySig[]) => (this.countriesSharedCollection = countries));

    this.cityService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? []))
      .subscribe((cities: ICitySig[]) => (this.citiesSharedCollection = cities));

    this.categoryService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICategorySig[]>) => res.body ?? []))
      .subscribe((categories: ICategorySig[]) => (this.categoriesSharedCollection = categories));

    this.fonctionService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? []))
      .subscribe((fonctions: IFonctionSig[]) => (this.fonctionsSharedCollection = fonctions));

    this.organizService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IOrganizSig[]>) => res.body ?? []))
      .subscribe((organizs: IOrganizSig[]) => (this.organizsSharedCollection = organizs));

    this.accreditationTypeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IAccreditationTypeSig[]>) => res.body ?? []))
      .subscribe((accreditationTypes: IAccreditationTypeSig[]) => (this.accreditationTypesSharedCollection = accreditationTypes));

    this.statusService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? []))
      .subscribe((statuses: IStatusSig[]) => (this.statusesSharedCollection = statuses));

    this.attachementService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IAttachementSig[]>) => res.body ?? []))
      .subscribe((attachements: IAttachementSig[]) => (this.attachementsSharedCollection = attachements));

    this.codeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICodeSig[]>) => res.body ?? []))
      .subscribe((codes: ICodeSig[]) => (this.codesSharedCollection = codes));

    this.dayPassInfoService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IDayPassInfoSig[]>) => res.body ?? []))
      .subscribe((dayPassInfos: IDayPassInfoSig[]) => (this.dayPassInfosSharedCollection = dayPassInfos));
  }

  downloadModelFile() {
    const columnHeaders = [
      //Required fields
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationFirstName'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationLastName'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationBirthDay'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationSexe'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationOccupation'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationType'),
      this.translateService.instant('sigmaEventsApp.accreditation.site'),
      this.translateService.instant('sigmaEventsApp.accreditation.organiz'),
      this.translateService.instant('sigmaEventsApp.accreditation.nationality'),
      //No Required fields
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationSecondName'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationPhoto'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationDescription'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationCinId'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationPasseportId'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationCartePresseId'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationCarteProfessionnelleId'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationEmail'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationTel'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationDateStart'),
      this.translateService.instant('sigmaEventsApp.accreditation.accreditationDateEnd'),
      this.translateService.instant('sigmaEventsApp.accreditation.civility'),
      this.translateService.instant('sigmaEventsApp.accreditation.country'),
      this.translateService.instant('sigmaEventsApp.accreditation.city'),
      this.translateService.instant('sigmaEventsApp.accreditation.attachement'),
      this.translateService.instant('sigmaEventsApp.accreditation.code'),
      this.translateService.instant('sigmaEventsApp.accreditation.dayPassInfo'),
    ];
    ExportUtil.initialize(this.translateService);
    // Create a worksheet with the column headers
    ExportUtil.downloadAccreditationModelFile(
      columnHeaders,
      'sigmaEventsApp.accreditation.home.title',
      'sigmaEventsApp.accreditation.home.upload',
      'sigmaEventsApp.accreditation.home.title'
    );
  }

  handleFileInput(event: any) {
    this.errorsMap.clear();
    this.selectedFile = event.target.files[0];
  }

  loadData() {
    if (this.accountService.hasAnyAuthority([Authority.ADMIN]) && !this.importForm.get('event')?.value) {
      alert('Please Select Event');
      return;
    }
    this.errorsMap.clear();
    this.accreditations = [];
    if (this.selectedFile) {
      this.isLoading = true;
      const fileReader = new FileReader();
      fileReader.onload = (e: any) => {
        const data = new Uint8Array(e.target.result);
        const workbook = XLSX.read(data, { type: 'array' });
        const worksheet = workbook.Sheets[workbook.SheetNames[0]];
        const jsonData: any[][] = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

        if (jsonData.length > 1) {
          const promises: Promise<void>[] = [];
          for (let rowIndex = 1; rowIndex < jsonData.length; rowIndex++) {
            const rowData = jsonData[rowIndex];
            console.log(rowData);
            const loadedValues: Partial<IAccreditationSig> = this.getAccreditationValues(rowData);
            const accreditationId: number = loadedValues['accreditationId'] || 0;
            const loadedAccreditation: IAccreditationSig = { ...loadedValues, accreditationId };

            const errors: string[] = this.controlAccreditation(loadedAccreditation);
            if (errors.length === 0) {
              this.accreditations?.push(loadedAccreditation);
              this.errorsMap.set(rowIndex, {
                firstName: loadedAccreditation.accreditationFirstName!,
                lastName: loadedAccreditation.accreditationLastName!,
                occupation: loadedAccreditation.accreditationOccupation!,
                errors: this.translateService.instant('sigmaEventsApp.accreditation.upload.loaded'),
                hasErrors: false,
              });
            } else {
              console.log(`Errors for accreditation at row ${rowIndex}: ${errors.join(', ')}`);
              this.errorsMap.set(rowIndex, {
                firstName: loadedAccreditation.accreditationFirstName!,
                lastName: loadedAccreditation.accreditationLastName!,
                occupation: loadedAccreditation.accreditationOccupation!,
                errors: errors.join(', '),
                hasErrors: true,
              });
            }
            const rowPromise = new Promise<void>(resolve => resolve());
            promises.push(rowPromise);
          }

          Promise.all(promises).then(() => {
            console.log(this.accreditations);
            this.cdr.detectChanges();
            this.isLoading = false;
          });
        } else {
          alert('Please insert Data');
        }
      };
      fileReader.readAsArrayBuffer(this.selectedFile);
    }
  }

  private getAccreditationValues(row: any[]): Partial<IAccreditationSig> {
    const loadedValues: Partial<IAccreditationSig> = {};
    loadedValues['status'] = this.status;
    loadedValues['accreditationFirstName'] = row[0].toString();
    loadedValues['accreditationLastName'] = row[1].toString();
    loadedValues['accreditationBirthDay'] = dayjs(XLSX.SSF.format('DD/MM/YYYY', row[2]), 'DD/MM/YYYY');
    loadedValues['sexe'] = this.sexesSharedCollection.find(sexe => sexe.sexeValue === row[3].toString());
    loadedValues['accreditationOccupation'] = row[4].toString();
    loadedValues['fonction'] = this.fonctionsSharedCollection.find(
      fonction => fonction.fonctionName === loadedValues['accreditationOccupation']
    );
    loadedValues['category'] = loadedValues['fonction']?.category;
    loadedValues['accreditationType'] = this.accreditationTypesSharedCollection.find(
      accreditationType => accreditationType.accreditationTypeValue === row[5].toString()
    );
    // loadedValues['sites'] = row[6];
    loadedValues['organiz'] = this.organizsSharedCollection.find(organiz => organiz.organizName === row[7].toString());
    loadedValues['nationality'] = this.nationalitiesSharedCollection.find(
      nationalities => nationalities.nationalityValue === row[8].toString()
    );
    if (row[9]) {
      loadedValues['accreditationSecondName'] = row[9].toString();
    }
    if (row[10]) {
      this.dataUtils
        .loadImageFromFile(row[10].toString())
        .then(base64String => {
          loadedValues['accreditationPhoto'] = base64String;
          loadedValues['accreditationPhotoContentType'] = this.dataUtils.getContentType(row[10].toString());
        })
        .catch(error => {
          console.error(error);
        });
    }
    if (row[11]) {
      loadedValues['accreditationDescription'] = row[11].toString();
    }
    if (row[12]) {
      loadedValues['accreditationCinId'] = row[12].toString();
    }
    if (row[13]) {
      loadedValues['accreditationPasseportId'] = row[13].toString();
    }
    if (row[14]) {
      loadedValues['accreditationCartePresseId'] = row[14].toString();
    }
    if (row[15]) {
      loadedValues['accreditationCarteProfessionnelleId'] = row[15].toString();
    }
    if (row[16]) {
      loadedValues['accreditationEmail'] = row[16].toString();
    }
    if (row[17]) {
      loadedValues['accreditationTel'] = row[17].toString();
    }
    if (row[18]) {
      loadedValues['accreditationDateStart'] = row[18];
    }
    if (row[19]) {
      loadedValues['accreditationDateEnd'] = row[19];
    }
    if (row[20]) {
      loadedValues['civility'] = this.civilitiesSharedCollection.find(civility => civility.civilityValue === row[20].toString());
    }
    if (row[21]) {
      loadedValues['country'] = this.countriesSharedCollection.find(country => country.countryName === row[21].toString());
    }
    if (row[22]) {
      loadedValues['city'] = this.citiesSharedCollection.find(citie => citie.cityName === row[22].toString());
    }
    if (row[23]) {
      loadedValues['attachement'] = this.attachementsSharedCollection.find(attachment => attachment.attachementName === row[23].toString());
    }
    if (row[24]) {
      loadedValues['code'] = this.codesSharedCollection.find(code => code.codeValue === row[24].toString());
    }
    if (row[25]) {
      loadedValues['dayPassInfo'] = this.dayPassInfosSharedCollection.find(
        dayPassInfo => dayPassInfo.dayPassInfoName === row[25].toString()
      );
    }

    if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
      if (this.importForm.get('event')?.value) {
        loadedValues['event'] = this.importForm.get('event')?.value;
      }
    } else {
      if (this.currentAccount?.printingCentre?.event) {
        loadedValues['event'] = this.currentAccount?.printingCentre?.event;
      }
    }

    return loadedValues;
  }

  importData(): void {
    if (this.accreditations && this.accreditations.length > 0) {
      this.isImporting = true;
      this.errorsMap.clear();
      const promises: Promise<void>[] = [];

      this.accreditations.forEach((accreditation: IAccreditationSig, index) => {
        const newAccreditation: NewAccreditationSig = {
          accreditationId: null, // Provide the default or null value based on your requirement
          status: accreditation.status,
          accreditationFirstName: accreditation.accreditationFirstName,
          accreditationLastName: accreditation.accreditationLastName,
          accreditationBirthDay: accreditation.accreditationBirthDay,
          sexe: accreditation.sexe,
          accreditationOccupation: accreditation.accreditationOccupation,
          category: accreditation.category,
          fonction: accreditation.fonction,
          accreditationType: accreditation.accreditationType,
          sites: accreditation.sites,
          organiz: accreditation.organiz,
          nationality: accreditation.nationality,
          // Assign other properties from the accreditation object
          accreditationSecondName: accreditation.accreditationSecondName,
          accreditationDescription: accreditation.accreditationDescription,
          accreditationCinId: accreditation.accreditationCinId,
          accreditationPasseportId: accreditation.accreditationPasseportId,
          accreditationCartePresseId: accreditation.accreditationCartePresseId,
          accreditationCarteProfessionnelleId: accreditation.accreditationCarteProfessionnelleId,
          accreditationEmail: accreditation.accreditationEmail,
          accreditationTel: accreditation.accreditationTel,
          accreditationDateStart: accreditation.accreditationDateStart,
          accreditationDateEnd: accreditation.accreditationDateEnd,
          civility: accreditation.civility,
          city: accreditation.city,
          attachement: accreditation.attachement,
          code: accreditation.code,
          dayPassInfo: accreditation.dayPassInfo,
          event: accreditation.event,

          accreditationPhoto: accreditation.accreditationPhoto,
          accreditationPhotoContentType: accreditation.accreditationPhotoContentType,
        };

        const createPromise = this.accreditationService.create(newAccreditation).toPromise();
        promises.push(
          createPromise
            .then((response: any) => {
              const acc: IAccreditationSig = response.body;
              this.errorsMap.set(acc.accreditationId, {
                firstName: acc.accreditationFirstName!,
                lastName: acc.accreditationLastName!,
                occupation: acc.accreditationOccupation!,
                errors: this.translateService.instant('sigmaEventsApp.accreditation.upload.imported'),
                hasErrors: false,
              });
              console.log('Accreditation created:', response);
            })
            .catch((error: any) => {
              this.errorsMap.set(index, { firstName: '', lastName: '', occupation: '', errors: error, hasErrors: true });
              console.error('Error creating accreditation:', error);
            })
        );
      });

      Promise.all(promises).then(() => {
        this.isImporting = false;
        this.accreditations = [];
        this.cdr.detectChanges();
        // Your code to execute after all promises are resolved
      });
    } else {
      alert('No accreditations to create.');
    }
  }

  controlAccreditation(accreditation: Partial<IAccreditationSig>): string[] {
    const errors: string[] = [];

    if (!accreditation.accreditationFirstName) {
      errors.push('First Name is required');
    }

    if (!accreditation.accreditationLastName) {
      errors.push('Last Name is required');
    }

    if (!accreditation.accreditationBirthDay) {
      errors.push('Birth Day is required');
    }

    if (!accreditation.sexe) {
      errors.push('Sexe is required');
    }

    if (!accreditation.accreditationOccupation) {
      errors.push('Occupation is required');
    }

    if (!accreditation.fonction) {
      errors.push('Fonction is required');
    }

    if (!accreditation.category) {
      errors.push('Category is required');
    }

    if (!accreditation.accreditationType) {
      errors.push('Accreditation Type is required');
    }

    if (!accreditation.organiz) {
      errors.push('Organiz is required');
    }

    if (!accreditation.nationality) {
      errors.push('Nationality is required');
    }

    return errors;
  }
}
