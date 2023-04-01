import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LanguageSigDetailComponent } from './language-sig-detail.component';

describe('LanguageSig Management Detail Component', () => {
  let comp: LanguageSigDetailComponent;
  let fixture: ComponentFixture<LanguageSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LanguageSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ language: { languageId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LanguageSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LanguageSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load language on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.language).toEqual(expect.objectContaining({ languageId: 123 }));
    });
  });
});
