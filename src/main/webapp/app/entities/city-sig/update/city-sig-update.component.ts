import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CitySigFormService, CitySigFormGroup } from './city-sig-form.service';
import { ICitySig } from '../city-sig.model';
import { CitySigService } from '../service/city-sig.service';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';

@Component({
  selector: 'sigma-city-sig-update',
  templateUrl: './city-sig-update.component.html',
})
export class CitySigUpdateComponent implements OnInit {
  isSaving = false;
  city: ICitySig | null = null;

  countriesSharedCollection: ICountrySig[] = [];

  editForm: CitySigFormGroup = this.cityFormService.createCitySigFormGroup();

  constructor(
    protected cityService: CitySigService,
    protected cityFormService: CitySigFormService,
    protected countryService: CountrySigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCountrySig = (o1: ICountrySig | null, o2: ICountrySig | null): boolean => this.countryService.compareCountrySig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ city }) => {
      this.city = city;
      if (city) {
        this.updateForm(city);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const city = this.cityFormService.getCitySig(this.editForm);
    if (city.cityId !== null) {
      this.subscribeToSaveResponse(this.cityService.update(city));
    } else {
      this.subscribeToSaveResponse(this.cityService.create(city));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICitySig>>): void {
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

  protected updateForm(city: ICitySig): void {
    this.city = city;
    this.cityFormService.resetForm(this.editForm, city);

    this.countriesSharedCollection = this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(
      this.countriesSharedCollection,
      city.country
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountrySig[]) =>
          this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(countries, this.city?.country)
        )
      )
      .subscribe((countries: ICountrySig[]) => (this.countriesSharedCollection = countries));
  }
}
