import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CodeSigFormService, CodeSigFormGroup } from './code-sig-form.service';
import { ICodeSig } from '../code-sig.model';
import { CodeSigService } from '../service/code-sig.service';
import { ICodeTypeSig } from 'app/entities/code-type-sig/code-type-sig.model';
import { CodeTypeSigService } from 'app/entities/code-type-sig/service/code-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-code-sig-update',
  templateUrl: './code-sig-update.component.html',
})
export class CodeSigUpdateComponent implements OnInit {
  isSaving = false;
  code: ICodeSig | null = null;

  codeTypesSharedCollection: ICodeTypeSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: CodeSigFormGroup = this.codeFormService.createCodeSigFormGroup();

  constructor(
    protected codeService: CodeSigService,
    protected codeFormService: CodeSigFormService,
    protected codeTypeService: CodeTypeSigService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCodeTypeSig = (o1: ICodeTypeSig | null, o2: ICodeTypeSig | null): boolean => this.codeTypeService.compareCodeTypeSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code }) => {
      this.code = code;
      if (code) {
        this.updateForm(code);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const code = this.codeFormService.getCodeSig(this.editForm);
    if (code.codeId !== null) {
      this.subscribeToSaveResponse(this.codeService.update(code));
    } else {
      this.subscribeToSaveResponse(this.codeService.create(code));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeSig>>): void {
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

  protected updateForm(code: ICodeSig): void {
    this.code = code;
    this.codeFormService.resetForm(this.editForm, code);

    this.codeTypesSharedCollection = this.codeTypeService.addCodeTypeSigToCollectionIfMissing<ICodeTypeSig>(
      this.codeTypesSharedCollection,
      code.codeType
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(this.eventsSharedCollection, code.event);
  }

  protected loadRelationshipsOptions(): void {
    this.codeTypeService
      .query()
      .pipe(map((res: HttpResponse<ICodeTypeSig[]>) => res.body ?? []))
      .pipe(
        map((codeTypes: ICodeTypeSig[]) =>
          this.codeTypeService.addCodeTypeSigToCollectionIfMissing<ICodeTypeSig>(codeTypes, this.code?.codeType)
        )
      )
      .subscribe((codeTypes: ICodeTypeSig[]) => (this.codeTypesSharedCollection = codeTypes));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.code?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
