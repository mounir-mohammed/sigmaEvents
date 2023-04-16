import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SiteSigFormService, SiteSigFormGroup } from './site-sig-form.service';
import { ISiteSig } from '../site-sig.model';
import { SiteSigService } from '../service/site-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-site-sig-update',
  templateUrl: './site-sig-update.component.html',
})
export class SiteSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  site: ISiteSig | null = null;

  citiesSharedCollection: ICitySig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: SiteSigFormGroup = this.siteFormService.createSiteSigFormGroup();
  public color = '#cccccc';
  authority = Authority;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected siteService: SiteSigService,
    protected siteFormService: SiteSigFormService,
    protected cityService: CitySigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareCitySig = (o1: ICitySig | null, o2: ICitySig | null): boolean => this.cityService.compareCitySig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ site }) => {
      this.site = site;
      if (site) {
        this.color = this.site?.siteColor!;
        this.updateForm(site);
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
    const site = this.siteFormService.getSiteSig(this.editForm);
    if (!this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      site.event = this.currentAccount?.printingCentre?.event;
    }
    if (site.siteId !== null) {
      this.subscribeToSaveResponse(this.siteService.update(site));
    } else {
      this.subscribeToSaveResponse(this.siteService.create(site));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteSig>>): void {
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

  protected updateForm(site: ISiteSig): void {
    this.site = site;
    this.siteFormService.resetForm(this.editForm, site);

    this.citiesSharedCollection = this.cityService.addCitySigToCollectionIfMissing<ICitySig>(this.citiesSharedCollection, site.city);
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(this.eventsSharedCollection, site.event);
  }

  protected loadRelationshipsOptions(): void {
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? []))
      .pipe(map((cities: ICitySig[]) => this.cityService.addCitySigToCollectionIfMissing<ICitySig>(cities, this.site?.city)))
      .subscribe((cities: ICitySig[]) => (this.citiesSharedCollection = cities));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.site?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }

  public onChangeColor(color: string): void {
    this.color = color;
    this.editForm.patchValue({ siteColor: color });
  }
}
