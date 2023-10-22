import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { IAccreditationSig } from '../accreditation-sig.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';
import { Authority } from 'app/config/authority.constants';
import { RECORD_ITEMS } from 'app/config/pagination.constants';
import { HttpResponse } from '@angular/common/http';
import { Observable, forkJoin, map, of, switchMap } from 'rxjs';
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
import { FormControl, FormGroup } from '@angular/forms';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { TranslateService } from '@ngx-translate/core';
import { Entity, OperationType } from 'app/config/operationType.contants';
import dayjs from 'dayjs/esm';
import { AccountService } from 'app/core/auth/account.service';
import { OperationHistorySigService } from 'app/entities/operation-history-sig/service/operation-history-sig.service';
import { Account } from 'app/core/auth/account.model';
import { IAccreditationMassUpdateSig } from '../accreditation-mass-update-sig.model';

@Component({
  selector: 'sigma-accreditation-sig-mass-update',
  templateUrl: './accreditation-sig-mass-update.component.html',
})
export class AccreditationSigMassUpdateComponent implements OnInit {
  accreditationsIds?: number[];
  isUpdateLoading = false;
  isDataLoading = false;

  massUpdateForm = new FormGroup({
    accreditationOccupation: new FormControl(),
    accreditationStat: new FormControl(),
    accreditationActivated: new FormControl(),
    accreditationDateStart: new FormControl(),
    accreditationDateEnd: new FormControl(),
    category: new FormControl(),
    fonction: new FormControl(),
    accreditationType: new FormControl(),
    sites: new FormControl(),
    organiz: new FormControl(),
    sexe: new FormControl(),
    civility: new FormControl(),
    nationality: new FormControl(),
    event: new FormControl(),
    status: new FormControl(),
    country: new FormControl(),
    city: new FormControl(),
    accreditationDescription: new FormControl(),
    accreditationParams: new FormControl(),
    accreditationAttributs: new FormControl(),
    attachement: new FormControl(),
    code: new FormControl(),
    dayPassInfo: new FormControl(),
    accreditationPhoto: new FormControl(),
    accreditationPhotoContentType: new FormControl(),
    accreditationUpdateSites: new FormControl(false),
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

  authority = Authority;
  currentAccount: Account | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected accreditationService: AccreditationSigService,
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
    protected router: Router,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected translateService: TranslateService,
    private accountService: AccountService,
    private operationHistorySigService: OperationHistorySigService
  ) {
    const state = history.state;
    if (state && state.ids) {
      const ids = state.ids;
      this.accreditationsIds = ids;
      localStorage.setItem('accreditationsIds', JSON.stringify(this.accreditationsIds));
    }
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    const storedData = localStorage.getItem('accreditationsIds');
    if (storedData) {
      this.accreditationsIds = JSON.parse(storedData);
    }
    if (this.accreditationsIds) {
      this.loadRelationshipsOptions();
    } else {
      this.router.navigate(['404']);
    }
  }

  protected loadRelationshipsOptions(): void {
    this.isDataLoading = true; // Set isLoading to true

    const serviceCalls = [
      this.siteService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ISiteSig[]>) => res.body ?? [])),
      this.eventService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? [])),
      this.civilityService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ICivilitySig[]>) => res.body ?? [])),
      this.sexeService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ISexeSig[]>) => res.body ?? [])),
      this.nationalityService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<INationalitySig[]>) => res.body ?? [])),
      this.countryService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? [])),
      this.cityService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? [])),
      this.categoryService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<ICategorySig[]>) => res.body ?? [])),
      this.fonctionService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? [])),
      this.organizService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<IOrganizSig[]>) => res.body ?? [])),
      this.accreditationTypeService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<IAccreditationTypeSig[]>) => res.body ?? [])),
      this.statusService.query({ size: RECORD_ITEMS }).pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? [])),
      this.attachementService.query().pipe(map((res: HttpResponse<IAttachementSig[]>) => res.body ?? [])),
      this.codeService.query().pipe(map((res: HttpResponse<ICodeSig[]>) => res.body ?? [])),
      this.dayPassInfoService.query().pipe(map((res: HttpResponse<IDayPassInfoSig[]>) => res.body ?? [])),
    ];

    forkJoin(serviceCalls).subscribe(results => {
      // All service calls are complete, and results is an array of the data from each call.

      this.sitesSharedCollection = results[0] as ISiteSig[];
      this.eventsSharedCollection = results[1] as IEventSig[];
      this.civilitiesSharedCollection = results[2] as ICivilitySig[];
      this.sexesSharedCollection = results[3] as ISexeSig[];
      this.nationalitiesSharedCollection = results[4] as INationalitySig[];
      this.countriesSharedCollection = results[5] as ICountrySig[];
      this.citiesSharedCollection = results[6] as ICitySig[];
      this.categoriesSharedCollection = results[7] as ICategorySig[];
      this.fonctionsSharedCollection = results[8] as IFonctionSig[];
      this.organizsSharedCollection = results[9] as IOrganizSig[];
      this.accreditationTypesSharedCollection = results[10] as IAccreditationTypeSig[];
      this.statusesSharedCollection = results[11] as IStatusSig[];
      this.attachementsSharedCollection = results[12] as IAttachementSig[];
      this.codesSharedCollection = results[13] as ICodeSig[];
      this.dayPassInfosSharedCollection = results[14] as IDayPassInfoSig[];
      this.isDataLoading = false; // Set isLoading to false when all service calls are complete
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    if (this.accreditationsIds && this.accreditationsIds.length > 0) {
      this.isUpdateLoading = true;
      const updatedValues = this.getUpdatedValues(); // Extract updated values from the form fields
      const accreditationMassUpdate = this.createAccreditationMassUpdate(updatedValues, this.accreditationsIds);
      accreditationMassUpdate.accreditationIds = this.accreditationsIds;

      if (this.massUpdateForm.get('accreditationDateStart')?.value) {
        accreditationMassUpdate.accreditationDateStart = dayjs(this.massUpdateForm.get('accreditationDateStart')?.value);
      }

      if (this.massUpdateForm.get('accreditationDateEnd')?.value) {
        accreditationMassUpdate.accreditationDateEnd = dayjs(this.massUpdateForm.get('accreditationDateEnd')?.value);
      }

      this.accreditationService.massUpdate(accreditationMassUpdate).subscribe(result => {
        if (result) {
          this.operationHistorySigService
            .createNewOperation(
              Entity.Accreditation,
              0,
              dayjs(),
              this.currentAccount?.printingCentre?.event!,
              OperationType.MassUpdate,
              this.currentAccount?.login!,
              this.accreditationsIds!,
              this.currentAccount?.printingCentre?.printingCentreId!,
              ''
            )
            .subscribe(result => {
              this.isUpdateLoading = false;
              this.previousState();
            });
        } else {
          alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.errorMassUpdate'));
          this.isUpdateLoading = false;
        }
      });
    } else {
      alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.errorMassUpdate'));
    }
  }

  private getUpdatedValues(): Partial<IAccreditationMassUpdateSig> {
    const fieldsToUpdate: (keyof IAccreditationMassUpdateSig)[] = [
      'accreditationOccupation',
      'accreditationStat',
      'accreditationActivated',
      'accreditationDateStart',
      'accreditationDateEnd',
      'category',
      'fonction',
      'accreditationType',
      'sites',
      'organiz',
      'sexe',
      'civility',
      'nationality',
      'event',
      'status',
      'country',
      'city',
      'accreditationDescription',
      'accreditationParams',
      'accreditationAttributs',
      'attachement',
      'code',
      'dayPassInfo',
      'accreditationPhoto',
      'accreditationPhotoContentType',
      'accreditationUpdateSites',
    ];

    const updatedValues: Partial<IAccreditationMassUpdateSig> = {};

    fieldsToUpdate.forEach(field => {
      const value = this.massUpdateForm.get(field)?.value;
      if (value !== null) {
        updatedValues[field] = value;
      }
    });

    return updatedValues;
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.massUpdateForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sigmaEventsApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  createAccreditationMassUpdate(
    updatedValues: Partial<IAccreditationMassUpdateSig>,
    accreditationIds: number[]
  ): IAccreditationMassUpdateSig {
    return {
      ...updatedValues,
      accreditationIds,
    };
  }

  protected loadFonctionsRelationshipsOptions(): void {
    const selectedCategory: any = (this.massUpdateForm.get('category') as FormControl).value;
    if (selectedCategory) {
      this.fonctionsSharedCollection = [];
      const req = {
        size: RECORD_ITEMS,
        'categoryId.equals': selectedCategory.categoryId,
      };
      this.fonctionService
        .query(req)
        .pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? []))
        .subscribe((fonctions: IFonctionSig[]) => (this.fonctionsSharedCollection = fonctions));
    } else {
      this.fonctionService
        .query()
        .pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? []))
        .subscribe((fonctions: IFonctionSig[]) => (this.fonctionsSharedCollection = fonctions));
    }
  }
}
