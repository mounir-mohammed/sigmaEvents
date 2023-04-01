import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CitySigFormService } from './city-sig-form.service';
import { CitySigService } from '../service/city-sig.service';
import { ICitySig } from '../city-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';

import { CitySigUpdateComponent } from './city-sig-update.component';

describe('CitySig Management Update Component', () => {
  let comp: CitySigUpdateComponent;
  let fixture: ComponentFixture<CitySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cityFormService: CitySigFormService;
  let cityService: CitySigService;
  let countryService: CountrySigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CitySigUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CitySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CitySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cityFormService = TestBed.inject(CitySigFormService);
    cityService = TestBed.inject(CitySigService);
    countryService = TestBed.inject(CountrySigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CountrySig query and add missing value', () => {
      const city: ICitySig = { cityId: 456 };
      const country: ICountrySig = { countryId: 28461 };
      city.country = country;

      const countryCollection: ICountrySig[] = [{ countryId: 88015 }];
      jest.spyOn(countryService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const additionalCountrySigs = [country];
      const expectedCollection: ICountrySig[] = [...additionalCountrySigs, ...countryCollection];
      jest.spyOn(countryService, 'addCountrySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ city });
      comp.ngOnInit();

      expect(countryService.query).toHaveBeenCalled();
      expect(countryService.addCountrySigToCollectionIfMissing).toHaveBeenCalledWith(
        countryCollection,
        ...additionalCountrySigs.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const city: ICitySig = { cityId: 456 };
      const country: ICountrySig = { countryId: 61128 };
      city.country = country;

      activatedRoute.data = of({ city });
      comp.ngOnInit();

      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.city).toEqual(city);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitySig>>();
      const city = { cityId: 123 };
      jest.spyOn(cityFormService, 'getCitySig').mockReturnValue(city);
      jest.spyOn(cityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: city }));
      saveSubject.complete();

      // THEN
      expect(cityFormService.getCitySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cityService.update).toHaveBeenCalledWith(expect.objectContaining(city));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitySig>>();
      const city = { cityId: 123 };
      jest.spyOn(cityFormService, 'getCitySig').mockReturnValue({ cityId: null });
      jest.spyOn(cityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: city }));
      saveSubject.complete();

      // THEN
      expect(cityFormService.getCitySig).toHaveBeenCalled();
      expect(cityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitySig>>();
      const city = { cityId: 123 };
      jest.spyOn(cityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCountrySig', () => {
      it('Should forward to countryService', () => {
        const entity = { countryId: 123 };
        const entity2 = { countryId: 456 };
        jest.spyOn(countryService, 'compareCountrySig');
        comp.compareCountrySig(entity, entity2);
        expect(countryService.compareCountrySig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
