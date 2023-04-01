import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LanguageSigFormService, LanguageSigFormGroup } from './language-sig-form.service';
import { ILanguageSig } from '../language-sig.model';
import { LanguageSigService } from '../service/language-sig.service';

@Component({
  selector: 'sigma-language-sig-update',
  templateUrl: './language-sig-update.component.html',
})
export class LanguageSigUpdateComponent implements OnInit {
  isSaving = false;
  language: ILanguageSig | null = null;

  editForm: LanguageSigFormGroup = this.languageFormService.createLanguageSigFormGroup();

  constructor(
    protected languageService: LanguageSigService,
    protected languageFormService: LanguageSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => {
      this.language = language;
      if (language) {
        this.updateForm(language);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const language = this.languageFormService.getLanguageSig(this.editForm);
    if (language.languageId !== null) {
      this.subscribeToSaveResponse(this.languageService.update(language));
    } else {
      this.subscribeToSaveResponse(this.languageService.create(language));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILanguageSig>>): void {
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

  protected updateForm(language: ILanguageSig): void {
    this.language = language;
    this.languageFormService.resetForm(this.editForm, language);
  }
}
