import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { FilterOptions, IFilterOptions } from 'app/shared/filter/filter.model';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
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
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { DayPassInfoSigService } from 'app/entities/day-pass-info-sig/service/day-pass-info-sig.service';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { FonctionSigService } from 'app/entities/fonction-sig/service/fonction-sig.service';
import { NationalitySigService } from 'app/entities/nationality-sig/service/nationality-sig.service';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { SexeSigService } from 'app/entities/sexe-sig/service/sexe-sig.service';
import { SiteSigService } from 'app/entities/site-sig/service/site-sig.service';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { Authority } from 'app/config/authority.constants';
import dayjs from 'dayjs';
import { RECORD_ITEMS } from 'app/config/pagination.constants';

@Component({
  templateUrl: './accreditation-sig-search-dialog.component.html',
  styleUrls: ['./accreditation-sig-search-dialog.component.scss'],
})
export class AccreditationSigSearchDialogComponent implements OnInit {
  authority = Authority;
  searchForm = new FormGroup({
    accreditationId: new FormControl(),
    age: new FormControl(),
    accreditationFirstName: new FormControl(),
    accreditationSecondName: new FormControl(),
    accreditationLastName: new FormControl(),
    accreditationBirthDay: new FormControl(),
    accreditationCinId: new FormControl(),
    accreditationPasseportId: new FormControl(),
    accreditationCartePresseId: new FormControl(),
    accreditationCarteProfessionnelleId: new FormControl(),
    accreditationOccupation: new FormControl(),
    accreditationStat: new FormControl(),
    accreditationActivated: new FormControl(),
    accreditationPrinted: new FormControl(),
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
    attachement: new FormControl(),
    code: new FormControl(),
    dayPassInfo: new FormControl(),
    accreditationEmail: new FormControl(),
    accreditationTel: new FormControl(),
  });

  filters = new Map();
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
  dayPassInfosSharedCollection: IDayPassInfoSig[] = [];

  FiltredSitesSharedCollection: ISiteSig[] = [];
  FiltredCategoriesSharedCollection: ICategorySig[] = [];
  FiltredFonctionsSharedCollection: IFonctionSig[] = [];
  FiltredOrganizsSharedCollection: IOrganizSig[] = [];
  FiltredAccreditationTypesSharedCollection: IAccreditationTypeSig[] = [];

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
    protected dayPassInfoService: DayPassInfoSigService
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmSearch(): void {
    if (this.searchForm.get('accreditationId')?.value) {
      this.filters.set('accreditationId.equals', this.searchForm.get('accreditationId')?.value);
    }

    if (this.searchForm.get('accreditationFirstName')?.value) {
      this.filters.set('accreditationFirstName.contains', this.searchForm.get('accreditationFirstName')?.value);
    }

    if (this.searchForm.get('code')?.value) {
      this.filters.set('code.contains', this.searchForm.get('code')?.value);
    }

    if (this.searchForm.get('accreditationSecondName')?.value) {
      this.filters.set('accreditationSecondName.contains', this.searchForm.get('accreditationSecondName')?.value);
    }

    if (this.searchForm.get('accreditationLastName')?.value) {
      this.filters.set('accreditationLastName.contains', this.searchForm.get('accreditationLastName')?.value);
    }

    if (this.searchForm.get('accreditationBirthDay')?.value) {
      this.filters.set('accreditationBirthDay.equals', dayjs(this.searchForm.get('accreditationBirthDay')?.value).format('YYYY-MM-DD'));
    }

    if (this.searchForm.get('accreditationCinId')?.value) {
      this.filters.set('accreditationCinId.contains', this.searchForm.get('accreditationCinId')?.value);
    }

    if (this.searchForm.get('accreditationPasseportId')?.value) {
      this.filters.set('accreditationPasseportId.contains', this.searchForm.get('accreditationPasseportId')?.value);
    }

    if (this.searchForm.get('accreditationCartePresseId')?.value) {
      this.filters.set('accreditationCartePresseId.contains', this.searchForm.get('accreditationCartePresseId')?.value);
    }

    if (this.searchForm.get('accreditationCarteProfessionnelleId')?.value) {
      this.filters.set('accreditationCarteProfessionnelleId.contains', this.searchForm.get('accreditationCarteProfessionnelleId')?.value);
    }

    if (this.searchForm.get('accreditationOccupation')?.value) {
      this.filters.set('accreditationOccupation.contains', this.searchForm.get('accreditationOccupation')?.value);
    }

    if (this.searchForm.get('accreditationStat')?.value) {
      if (this.searchForm.get('accreditationStat')?.value == 'true') {
        this.filters.set('accreditationStat.equals', [true]);
      } else if (this.searchForm.get('accreditationStat')?.value == 'false') {
        this.filters.set('accreditationStat.equals', [false]);
      }
    }

    if (this.searchForm.get('accreditationActivated')?.value) {
      if (this.searchForm.get('accreditationActivated')?.value == 'true') {
        this.filters.set('accreditationActivated.equals', [true]);
      } else if (this.searchForm.get('accreditationActivated')?.value == 'false') {
        this.filters.set('accreditationActivated.equals', [false]);
      }
    }

    if (this.searchForm.get('accreditationPrinted')?.value) {
      if (this.searchForm.get('accreditationPrinted')?.value == 'true') {
        this.filters.set('accreditationPrintStat.equals', [true]);
      } else if (this.searchForm.get('accreditationPrinted')?.value == 'false') {
        this.filters.set('accreditationPrintStat.equals', [false]);
      }
    }

    if (this.searchForm.get('accreditationDateStart')?.value) {
      this.filters.set(
        'accreditationDateStart.greaterThanOrEqual',
        dayjs(this.searchForm.get('accreditationDateStart')?.value).format('YYYY-MM-DDTHH:mm:ss.SSSZ')
      );
    }

    if (this.searchForm.get('accreditationDateEnd')?.value) {
      this.filters.set(
        'accreditationDateEnd.lessThanOrEqual',
        dayjs(this.searchForm.get('accreditationDateEnd')?.value).format('YYYY-MM-DDTHH:mm:ss.SSSZ')
      );
    }

    if (this.searchForm.get('category')?.value) {
      this.filters.set('categoryId.in', this.searchForm.get('category')?.value);
    }

    if (this.searchForm.get('fonction')?.value) {
      this.filters.set('fonctionId.in', this.searchForm.get('fonction')?.value);
    }

    if (this.searchForm.get('accreditationType')?.value) {
      this.filters.set('accreditationTypeId.in', this.searchForm.get('accreditationType')?.value);
    }

    if (this.searchForm.get('sites')?.value) {
      this.filters.set('siteId.in', this.searchForm.get('sites')?.value);
    }

    if (this.searchForm.get('organiz')?.value) {
      this.filters.set('organizId.in', this.searchForm.get('organiz')?.value);
    }

    if (this.searchForm.get('sexe')?.value) {
      this.filters.set('sexeId.in', this.searchForm.get('sexe')?.value);
    }

    if (this.searchForm.get('civility')?.value) {
      this.filters.set('civilityId.in', this.searchForm.get('civility')?.value);
    }

    if (this.searchForm.get('nationality')?.value) {
      this.filters.set('nationalityId.in', this.searchForm.get('nationality')?.value);
    }

    if (this.searchForm.get('event')?.value) {
      if (this.searchForm.get('event')?.value == 'noEvent') {
        this.filters.set('eventId.specified', [false]);
      } else {
        this.filters.set('eventId.in', this.searchForm.get('event')?.value);
      }
    }

    if (this.searchForm.get('status')?.value) {
      this.filters.set('statusId.in', this.searchForm.get('status')?.value);
    }

    if (this.searchForm.get('country')?.value) {
      this.filters.set('countryId.in', this.searchForm.get('country')?.value);
    }

    if (this.searchForm.get('city')?.value) {
      this.filters.set('cityId.in', this.searchForm.get('city')?.value);
    }

    if (this.searchForm.get('attachement')?.value) {
      this.filters.set('attachementId.in', this.searchForm.get('attachement')?.value);
    }

    if (this.searchForm.get('dayPassInfo')?.value) {
      this.filters.set('dayPassInfoId.in', this.searchForm.get('dayPassInfo')?.value);
    }

    if (this.searchForm.get('accreditationEmail')?.value) {
      this.filters.set('accreditationEmail.contains', this.searchForm.get('accreditationEmail')?.value);
    }

    if (this.searchForm.get('accreditationTel')?.value) {
      this.filters.set('accreditationTel.contains', this.searchForm.get('accreditationTel')?.value);
    }

    if (this.filters) {
      this.activeModal.close(this.filters);
    }
  }

  protected loadRelationshipsOptions(): void {
    this.siteService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ISiteSig[]>) => res.body ?? []))
      .subscribe((sites: ISiteSig[]) => {
        this.sitesSharedCollection = sites;
        this.FiltredSitesSharedCollection = sites;
      });

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
      .subscribe((nationalities: INationalitySig[]) => {
        // ✅ Ajoute la propriété disabled selon le statut
        this.nationalitiesSharedCollection = nationalities.map(nat => ({
          ...nat,
          disabled: nat.nationalityStat === false, // 🔹 désactive si inactif
        }));
      });

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
      .subscribe((categories: ICategorySig[]) => {
        // ✅ Ajoute la propriété disabled selon le statut
        this.categoriesSharedCollection = categories.map(cat => ({
          ...cat,
          disabled: cat.categoryStat === false, // 🔹 désactive les catégories inactives
        }));

        // ✅ Initialise la liste filtrée avec tout le contenu
        this.FiltredCategoriesSharedCollection = this.categoriesSharedCollection;
      });

    this.fonctionService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IFonctionSig[]>) => res.body ?? []))
      .subscribe((fonctions: IFonctionSig[]) => {
        // ✅ Ajoute la propriété disabled selon le statut
        this.fonctionsSharedCollection = fonctions.map(f => ({
          ...f,
          disabled: f.fonctionStat === false, // 🔹 désactive si inactif
        }));

        // ✅ Initialise la liste filtrée
        this.FiltredFonctionsSharedCollection = this.fonctionsSharedCollection;
      });

    this.organizService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IOrganizSig[]>) => res.body ?? []))
      .subscribe((organizs: IOrganizSig[]) => {
        // ✅ Ajoute la propriété disabled selon le statut
        this.organizsSharedCollection = organizs.map(org => ({
          ...org,
          disabled: org.organizStat === false, // 🔹 désactive si inactif
        }));

        // ✅ Initialise la liste filtrée
        this.FiltredOrganizsSharedCollection = this.organizsSharedCollection;
      });

    this.accreditationTypeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IAccreditationTypeSig[]>) => res.body ?? []))
      .subscribe((accreditationTypes: IAccreditationTypeSig[]) => {
        // ✅ Ajoute la propriété disabled selon le statut
        this.accreditationTypesSharedCollection = accreditationTypes.map(type => ({
          ...type,
          disabled: type.accreditationTypeStat === false, // 🔹 désactive si inactif
        }));

        // ✅ Initialise la liste filtrée
        this.FiltredAccreditationTypesSharedCollection = this.accreditationTypesSharedCollection;
      });

    this.statusService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? []))
      .subscribe((statuses: IStatusSig[]) => (this.statusesSharedCollection = statuses));

    this.attachementService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IAttachementSig[]>) => res.body ?? []))
      .subscribe((attachements: IAttachementSig[]) => (this.attachementsSharedCollection = attachements));

    this.dayPassInfoService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IDayPassInfoSig[]>) => res.body ?? []))
      .subscribe((dayPassInfos: IDayPassInfoSig[]) => (this.dayPassInfosSharedCollection = dayPassInfos));
  }

  filterByEvent() {
    if (this.searchForm.get('event')?.value) {
      this.FiltredAccreditationTypesSharedCollection = this.filterAccreditationTypesByEventId(
        this.accreditationTypesSharedCollection,
        this.searchForm.get('event')?.value
      );
      this.FiltredCategoriesSharedCollection = this.filterCategoriesByEventId(
        this.categoriesSharedCollection,
        this.searchForm.get('event')?.value
      );
      this.FiltredFonctionsSharedCollection = this.filterFonctionsByEventId(
        this.fonctionsSharedCollection,
        this.searchForm.get('event')?.value
      );
      this.FiltredOrganizsSharedCollection = this.filterOrganizsByEventId(
        this.organizsSharedCollection,
        this.searchForm.get('event')?.value
      );
      this.FiltredSitesSharedCollection = this.filterSitesByEventId(this.sitesSharedCollection, this.searchForm.get('event')?.value);
    } else {
      this.FiltredAccreditationTypesSharedCollection = this.accreditationTypesSharedCollection;
      this.FiltredCategoriesSharedCollection = this.categoriesSharedCollection;
      this.FiltredFonctionsSharedCollection = this.fonctionsSharedCollection;
      this.FiltredOrganizsSharedCollection = this.organizsSharedCollection;
      this.FiltredSitesSharedCollection = this.sitesSharedCollection;
    }
  }

  filterAccreditationTypesByEventId(accreditationTypes: IAccreditationTypeSig[], eventId: number): IAccreditationTypeSig[] {
    return accreditationTypes.filter(type => type.event?.eventId === eventId);
  }
  filterOrganizsByEventId(organizs: IOrganizSig[], eventId: number): IOrganizSig[] {
    return organizs.filter(organiz => organiz.event?.eventId === eventId);
  }
  filterCategoriesByEventId(categories: ICategorySig[], eventId: number): ICategorySig[] {
    return categories.filter(category => category.event?.eventId === eventId);
  }
  filterFonctionsByEventId(fonctions: IFonctionSig[], eventId: number): IFonctionSig[] {
    return fonctions.filter(fonction => fonction.event?.eventId === eventId);
  }
  filterSitesByEventId(sites: ISiteSig[], eventId: number): ISiteSig[] {
    return sites.filter(site => site.event?.eventId === eventId);
  }
}
