import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganizSigFormService, OrganizSigFormGroup } from './organiz-sig-form.service';
import { IOrganizSig } from '../organiz-sig.model';
import { OrganizSigService } from '../service/organiz-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-organiz-sig-update',
  templateUrl: './organiz-sig-update.component.html',
})
export class OrganizSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  organiz: IOrganizSig | null = null;

  countriesSharedCollection: ICountrySig[] = [];
  citiesSharedCollection: ICitySig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: OrganizSigFormGroup = this.organizFormService.createOrganizSigFormGroup();
  authority = Authority;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected organizService: OrganizSigService,
    protected organizFormService: OrganizSigFormService,
    protected countryService: CountrySigService,
    protected cityService: CitySigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareCountrySig = (o1: ICountrySig | null, o2: ICountrySig | null): boolean => this.countryService.compareCountrySig(o1, o2);

  compareCitySig = (o1: ICitySig | null, o2: ICitySig | null): boolean => this.cityService.compareCitySig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ organiz }) => {
      this.organiz = organiz;
      if (organiz) {
        this.updateForm(organiz);
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

  save(): void {
    this.isSaving = true;
    const organiz = this.organizFormService.getOrganizSig(this.editForm);
    if (!this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      organiz.event = this.currentAccount?.printingCentre?.event;
    }
    if (organiz.organizId !== null) {
      this.subscribeToSaveResponse(this.organizService.update(organiz));
    } else {
      this.subscribeToSaveResponse(this.organizService.create(organiz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizSig>>): void {
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

  protected updateForm(organiz: IOrganizSig): void {
    this.organiz = organiz;
    this.organizFormService.resetForm(this.editForm, organiz);

    this.countriesSharedCollection = this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(
      this.countriesSharedCollection,
      organiz.country
    );
    this.citiesSharedCollection = this.cityService.addCitySigToCollectionIfMissing<ICitySig>(this.citiesSharedCollection, organiz.city);
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(this.eventsSharedCollection, organiz.event);
  }

  protected loadRelationshipsOptions(): void {
    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountrySig[]) =>
          this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(countries, this.organiz?.country)
        )
      )
      .subscribe((countries: ICountrySig[]) => (this.countriesSharedCollection = countries));

    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? []))
      .pipe(map((cities: ICitySig[]) => this.cityService.addCitySigToCollectionIfMissing<ICitySig>(cities, this.organiz?.city)))
      .subscribe((cities: ICitySig[]) => (this.citiesSharedCollection = cities));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.organiz?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
