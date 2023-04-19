import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AccreditationSigFormService, AccreditationSigFormGroup } from './accreditation-sig-form.service';
import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { SiteSigService } from 'app/entities/site-sig/service/site-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { CivilitySigService } from 'app/entities/civility-sig/service/civility-sig.service';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { SexeSigService } from 'app/entities/sexe-sig/service/sexe-sig.service';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { NationalitySigService } from 'app/entities/nationality-sig/service/nationality-sig.service';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { FonctionSigService } from 'app/entities/fonction-sig/service/fonction-sig.service';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { AccreditationTypeSigService } from 'app/entities/accreditation-type-sig/service/accreditation-type-sig.service';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { AttachementSigService } from 'app/entities/attachement-sig/service/attachement-sig.service';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { CodeSigService } from 'app/entities/code-sig/service/code-sig.service';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';
import { DayPassInfoSigService } from 'app/entities/day-pass-info-sig/service/day-pass-info-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';
import { Status } from 'app/config/status.contants';

@Component({
  selector: 'sigma-accreditation-sig-update',
  templateUrl: './accreditation-sig-update.component.html',
})
export class AccreditationSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  accreditation: IAccreditationSig | null = null;

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

  editForm: AccreditationSigFormGroup = this.accreditationFormService.createAccreditationSigFormGroup();
  authority = Authority;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected accreditationService: AccreditationSigService,
    protected accreditationFormService: AccreditationSigFormService,
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
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareSiteSig = (o1: ISiteSig | null, o2: ISiteSig | null): boolean => this.siteService.compareSiteSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  compareCivilitySig = (o1: ICivilitySig | null, o2: ICivilitySig | null): boolean => this.civilityService.compareCivilitySig(o1, o2);

  compareSexeSig = (o1: ISexeSig | null, o2: ISexeSig | null): boolean => this.sexeService.compareSexeSig(o1, o2);

  compareNationalitySig = (o1: INationalitySig | null, o2: INationalitySig | null): boolean =>
    this.nationalityService.compareNationalitySig(o1, o2);

  compareCountrySig = (o1: ICountrySig | null, o2: ICountrySig | null): boolean => this.countryService.compareCountrySig(o1, o2);

  compareCitySig = (o1: ICitySig | null, o2: ICitySig | null): boolean => this.cityService.compareCitySig(o1, o2);

  compareCategorySig = (o1: ICategorySig | null, o2: ICategorySig | null): boolean => this.categoryService.compareCategorySig(o1, o2);

  compareFonctionSig = (o1: IFonctionSig | null, o2: IFonctionSig | null): boolean => this.fonctionService.compareFonctionSig(o1, o2);

  compareOrganizSig = (o1: IOrganizSig | null, o2: IOrganizSig | null): boolean => this.organizService.compareOrganizSig(o1, o2);

  compareAccreditationTypeSig = (o1: IAccreditationTypeSig | null, o2: IAccreditationTypeSig | null): boolean =>
    this.accreditationTypeService.compareAccreditationTypeSig(o1, o2);

  compareStatusSig = (o1: IStatusSig | null, o2: IStatusSig | null): boolean => this.statusService.compareStatusSig(o1, o2);

  compareAttachementSig = (o1: IAttachementSig | null, o2: IAttachementSig | null): boolean =>
    this.attachementService.compareAttachementSig(o1, o2);

  compareCodeSig = (o1: ICodeSig | null, o2: ICodeSig | null): boolean => this.codeService.compareCodeSig(o1, o2);

  compareDayPassInfoSig = (o1: IDayPassInfoSig | null, o2: IDayPassInfoSig | null): boolean =>
    this.dayPassInfoService.compareDayPassInfoSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ accreditation }) => {
      this.accreditation = accreditation;
      if (accreditation) {
        this.updateForm(accreditation);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sigmaEventsApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(event: any): void {
    if (event.submitter.name == 'Save') {
      this.isSaving = true;
      this.editForm.patchValue({ accreditationDateStart: this.currentAccount?.printingCentre?.event?.eventdateStart?.toString() });
      this.editForm.patchValue({ accreditationDateEnd: this.currentAccount?.printingCentre?.event?.eventdateEnd?.toString() });
      const accreditation = this.accreditationFormService.getAccreditationSig(this.editForm);
      if (!this.accountService.hasAnyAuthority(Authority.ADMIN)) {
        accreditation.event = this.currentAccount?.printingCentre?.event;
      }
      if (accreditation.accreditationId !== null) {
        this.subscribeToSaveResponse(this.accreditationService.update(accreditation));
      } else {
        accreditation.status = this.statusesSharedCollection.filter(status => status.statusAbreviation == Status.INPROGRESS).shift();
        this.subscribeToSaveResponse(this.accreditationService.create(accreditation));
      }
    } else {
      console.log('TODO SAVE AND PRINT');
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccreditationSig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(accreditation: IAccreditationSig): void {
    this.accreditation = accreditation;
    this.accreditationFormService.resetForm(this.editForm, accreditation);

    this.sitesSharedCollection = this.siteService.addSiteSigToCollectionIfMissing<ISiteSig>(
      this.sitesSharedCollection,
      ...(accreditation.sites ?? [])
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      accreditation.event
    );
    this.civilitiesSharedCollection = this.civilityService.addCivilitySigToCollectionIfMissing<ICivilitySig>(
      this.civilitiesSharedCollection,
      accreditation.civility
    );
    this.sexesSharedCollection = this.sexeService.addSexeSigToCollectionIfMissing<ISexeSig>(this.sexesSharedCollection, accreditation.sexe);
    this.nationalitiesSharedCollection = this.nationalityService.addNationalitySigToCollectionIfMissing<INationalitySig>(
      this.nationalitiesSharedCollection,
      accreditation.nationality
    );
    this.countriesSharedCollection = this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(
      this.countriesSharedCollection,
      accreditation.country
    );
    this.citiesSharedCollection = this.cityService.addCitySigToCollectionIfMissing<ICitySig>(
      this.citiesSharedCollection,
      accreditation.city
    );
    this.categoriesSharedCollection = this.categoryService.addCategorySigToCollectionIfMissing<ICategorySig>(
      this.categoriesSharedCollection,
      accreditation.category
    );
    this.fonctionsSharedCollection = this.fonctionService.addFonctionSigToCollectionIfMissing<IFonctionSig>(
      this.fonctionsSharedCollection,
      accreditation.fonction
    );
    this.organizsSharedCollection = this.organizService.addOrganizSigToCollectionIfMissing<IOrganizSig>(
      this.organizsSharedCollection,
      accreditation.organiz
    );
    this.accreditationTypesSharedCollection =
      this.accreditationTypeService.addAccreditationTypeSigToCollectionIfMissing<IAccreditationTypeSig>(
        this.accreditationTypesSharedCollection,
        accreditation.accreditationType
      );
    this.statusesSharedCollection = this.statusService.addStatusSigToCollectionIfMissing<IStatusSig>(
      this.statusesSharedCollection,
      accreditation.status
    );
    this.attachementsSharedCollection = this.attachementService.addAttachementSigToCollectionIfMissing<IAttachementSig>(
      this.attachementsSharedCollection,
      accreditation.attachement
    );
    this.codesSharedCollection = this.codeService.addCodeSigToCollectionIfMissing<ICodeSig>(this.codesSharedCollection, accreditation.code);
    this.dayPassInfosSharedCollection = this.dayPassInfoService.addDayPassInfoSigToCollectionIfMissing<IDayPassInfoSig>(
      this.dayPassInfosSharedCollection,
      accreditation.dayPassInfo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.siteService
      .query()
      .pipe(map((res: HttpResponse<ISiteSig[]>) => res.body ?? []))
      .pipe(
        map((sites: ISiteSig[]) => this.siteService.addSiteSigToCollectionIfMissing<ISiteSig>(sites, ...(this.accreditation?.sites ?? [])))
      )
      .subscribe((sites: ISiteSig[]) => (this.sitesSharedCollection = sites));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.accreditation?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

    this.civilityService
      .query()
      .pipe(map((res: HttpResponse<ICivilitySig[]>) => res.body ?? []))
      .pipe(
        map((civilities: ICivilitySig[]) =>
          this.civilityService.addCivilitySigToCollectionIfMissing<ICivilitySig>(civilities, this.accreditation?.civility)
        )
      )
      .subscribe((civilities: ICivilitySig[]) => (this.civilitiesSharedCollection = civilities));

    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexeSig[]>) => res.body ?? []))
      .pipe(map((sexes: ISexeSig[]) => this.sexeService.addSexeSigToCollectionIfMissing<ISexeSig>(sexes, this.accreditation?.sexe)))
      .subscribe((sexes: ISexeSig[]) => (this.sexesSharedCollection = sexes));

    this.nationalityService
      .query()
      .pipe(map((res: HttpResponse<INationalitySig[]>) => res.body ?? []))
      .pipe(
        map((nationalities: INationalitySig[]) =>
          this.nationalityService.addNationalitySigToCollectionIfMissing<INationalitySig>(nationalities, this.accreditation?.nationality)
        )
      )
      .subscribe((nationalities: INationalitySig[]) => (this.nationalitiesSharedCollection = nationalities));

    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountrySig[]) =>
          this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(countries, this.accreditation?.country)
        )
      )
      .subscribe((countries: ICountrySig[]) => (this.countriesSharedCollection = countries));

    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? []))
      .pipe(map((cities: ICitySig[]) => this.cityService.addCitySigToCollectionIfMissing<ICitySig>(cities, this.accreditation?.city)))
      .subscribe((cities: ICitySig[]) => (this.citiesSharedCollection = cities));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategorySig[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorySig[]) =>
          this.categoryService.addCategorySigToCollectionIfMissing<ICategorySig>(categories, this.accreditation?.category)
        )
      )
      .subscribe((categories: ICategorySig[]) => (this.categoriesSharedCollection = categories));

    this.fonctionService
      .query()
      .pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? []))
      .pipe(
        map((fonctions: IFonctionSig[]) =>
          this.fonctionService.addFonctionSigToCollectionIfMissing<IFonctionSig>(fonctions, this.accreditation?.fonction)
        )
      )
      .subscribe((fonctions: IFonctionSig[]) => (this.fonctionsSharedCollection = fonctions));

    this.organizService
      .query()
      .pipe(map((res: HttpResponse<IOrganizSig[]>) => res.body ?? []))
      .pipe(
        map((organizs: IOrganizSig[]) =>
          this.organizService.addOrganizSigToCollectionIfMissing<IOrganizSig>(organizs, this.accreditation?.organiz)
        )
      )
      .subscribe((organizs: IOrganizSig[]) => (this.organizsSharedCollection = organizs));

    this.accreditationTypeService
      .query()
      .pipe(map((res: HttpResponse<IAccreditationTypeSig[]>) => res.body ?? []))
      .pipe(
        map((accreditationTypes: IAccreditationTypeSig[]) =>
          this.accreditationTypeService.addAccreditationTypeSigToCollectionIfMissing<IAccreditationTypeSig>(
            accreditationTypes,
            this.accreditation?.accreditationType
          )
        )
      )
      .subscribe((accreditationTypes: IAccreditationTypeSig[]) => (this.accreditationTypesSharedCollection = accreditationTypes));

    this.statusService
      .query()
      .pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? []))
      .pipe(
        map((statuses: IStatusSig[]) =>
          this.statusService.addStatusSigToCollectionIfMissing<IStatusSig>(statuses, this.accreditation?.status)
        )
      )
      .subscribe((statuses: IStatusSig[]) => (this.statusesSharedCollection = statuses));

    this.attachementService
      .query()
      .pipe(map((res: HttpResponse<IAttachementSig[]>) => res.body ?? []))
      .pipe(
        map((attachements: IAttachementSig[]) =>
          this.attachementService.addAttachementSigToCollectionIfMissing<IAttachementSig>(attachements, this.accreditation?.attachement)
        )
      )
      .subscribe((attachements: IAttachementSig[]) => (this.attachementsSharedCollection = attachements));

    this.codeService
      .query()
      .pipe(map((res: HttpResponse<ICodeSig[]>) => res.body ?? []))
      .pipe(map((codes: ICodeSig[]) => this.codeService.addCodeSigToCollectionIfMissing<ICodeSig>(codes, this.accreditation?.code)))
      .subscribe((codes: ICodeSig[]) => (this.codesSharedCollection = codes));

    this.dayPassInfoService
      .query()
      .pipe(map((res: HttpResponse<IDayPassInfoSig[]>) => res.body ?? []))
      .pipe(
        map((dayPassInfos: IDayPassInfoSig[]) =>
          this.dayPassInfoService.addDayPassInfoSigToCollectionIfMissing<IDayPassInfoSig>(dayPassInfos, this.accreditation?.dayPassInfo)
        )
      )
      .subscribe((dayPassInfos: IDayPassInfoSig[]) => (this.dayPassInfosSharedCollection = dayPassInfos));
  }
}
