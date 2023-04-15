import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FonctionSigFormService, FonctionSigFormGroup } from './fonction-sig-form.service';
import { IFonctionSig } from '../fonction-sig.model';
import { FonctionSigService } from '../service/fonction-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAreaSig } from 'app/entities/area-sig/area-sig.model';
import { AreaSigService } from 'app/entities/area-sig/service/area-sig.service';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'sigma-fonction-sig-update',
  templateUrl: './fonction-sig-update.component.html',
})
export class FonctionSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  fonction: IFonctionSig | null = null;

  areasSharedCollection: IAreaSig[] = [];
  categoriesSharedCollection: ICategorySig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: FonctionSigFormGroup = this.fonctionFormService.createFonctionSigFormGroup();
  public color = '#cccccc';

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fonctionService: FonctionSigService,
    protected fonctionFormService: FonctionSigFormService,
    protected areaService: AreaSigService,
    protected categoryService: CategorySigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareAreaSig = (o1: IAreaSig | null, o2: IAreaSig | null): boolean => this.areaService.compareAreaSig(o1, o2);

  compareCategorySig = (o1: ICategorySig | null, o2: ICategorySig | null): boolean => this.categoryService.compareCategorySig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ fonction }) => {
      this.fonction = fonction;
      if (fonction) {
        this.color = this.fonction?.fonctionColor!;
        this.updateForm(fonction);
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
    const fonction = this.fonctionFormService.getFonctionSig(this.editForm);
    if (!this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      fonction.event = this.currentAccount?.printingCentre?.event;
    }
    if (fonction.fonctionId !== null) {
      this.subscribeToSaveResponse(this.fonctionService.update(fonction));
    } else {
      this.subscribeToSaveResponse(this.fonctionService.create(fonction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFonctionSig>>): void {
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

  protected updateForm(fonction: IFonctionSig): void {
    this.fonction = fonction;
    this.fonctionFormService.resetForm(this.editForm, fonction);

    this.areasSharedCollection = this.areaService.addAreaSigToCollectionIfMissing<IAreaSig>(
      this.areasSharedCollection,
      ...(fonction.areas ?? [])
    );
    this.categoriesSharedCollection = this.categoryService.addCategorySigToCollectionIfMissing<ICategorySig>(
      this.categoriesSharedCollection,
      fonction.category
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      fonction.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.areaService
      .query()
      .pipe(map((res: HttpResponse<IAreaSig[]>) => res.body ?? []))
      .pipe(map((areas: IAreaSig[]) => this.areaService.addAreaSigToCollectionIfMissing<IAreaSig>(areas, ...(this.fonction?.areas ?? []))))
      .subscribe((areas: IAreaSig[]) => (this.areasSharedCollection = areas));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategorySig[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorySig[]) =>
          this.categoryService.addCategorySigToCollectionIfMissing<ICategorySig>(categories, this.fonction?.category)
        )
      )
      .subscribe((categories: ICategorySig[]) => (this.categoriesSharedCollection = categories));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.fonction?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }

  public onChangeColor(color: string): void {
    this.color = color;
    this.editForm.patchValue({ fonctionColor: color });
  }
}
