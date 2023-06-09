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

@Component({
  selector: 'sigma-accreditation-sig-mass-update',
  templateUrl: './accreditation-sig-mass-update.component.html',
})
export class AccreditationSigMassUpdateComponent implements OnInit {
  accreditationsIds?: number[] = [];

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
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.data) {
        const accreditationsIds = JSON.parse(atob(params.data));
        if (accreditationsIds) {
          this.accreditationsIds = accreditationsIds;
          console.log(accreditationsIds);
          this.loadRelationshipsOptions();
        } else {
          this.router.navigate(['404']);
        }
      }
    });
  }

  protected loadRelationshipsOptions(): void {
    this.siteService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ISiteSig[]>) => res.body ?? []))
      .subscribe((sites: ISiteSig[]) => (this.sitesSharedCollection = sites));

    this.eventService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

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
      .query()
      .pipe(map((res: HttpResponse<IAttachementSig[]>) => res.body ?? []))
      .subscribe((attachements: IAttachementSig[]) => (this.attachementsSharedCollection = attachements));

    this.codeService
      .query()
      .pipe(map((res: HttpResponse<ICodeSig[]>) => res.body ?? []))
      .subscribe((codes: ICodeSig[]) => (this.codesSharedCollection = codes));

    this.dayPassInfoService
      .query()
      .pipe(map((res: HttpResponse<IDayPassInfoSig[]>) => res.body ?? []))
      .subscribe((dayPassInfos: IDayPassInfoSig[]) => (this.dayPassInfosSharedCollection = dayPassInfos));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    if (this.accreditationsIds && this.accreditationsIds.length > 0) {
      const updatedValues: Partial<IAccreditationSig> = this.getUpdatedValues();
      const updateRequests: Observable<any>[] = [];

      this.accreditationsIds.forEach((accreditationId: number) => {
        updateRequests.push(
          this.accreditationService.find(accreditationId).pipe(
            map((res: HttpResponse<IAccreditationSig>) => res.body ?? []),
            switchMap(accreditation => {
              if (accreditation && this.massUpdateForm.valid) {
                const updatedAccreditation: IAccreditationSig = { ...accreditation, ...updatedValues, accreditationId };
                return this.accreditationService.update(updatedAccreditation);
              } else {
                return of(null);
              }
            })
          )
        );
      });

      forkJoin(updateRequests).subscribe(
        () => {
          console.log('All accreditations updated');
          this.previousState();
        },
        (error: any) => {
          console.error('Error updating accreditations', error);
          alert('ERROR WHILE UPDATING');
        }
      );
    } else {
      alert('ERROR WHILE UPDATING');
    }
  }

  private getUpdatedValues(): Partial<IAccreditationSig> {
    const fieldsToUpdate: (keyof IAccreditationSig)[] = [
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
    ];

    const updatedValues: Partial<IAccreditationSig> = {};

    fieldsToUpdate.forEach(field => {
      const value = this.massUpdateForm.get(field)?.value;
      if (value !== null) {
        updatedValues[field] = value;
      }
    });

    return updatedValues;
  }
}
