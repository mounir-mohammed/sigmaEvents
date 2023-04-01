import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuthentificationTypeSigDetailComponent } from './authentification-type-sig-detail.component';

describe('AuthentificationTypeSig Management Detail Component', () => {
  let comp: AuthentificationTypeSigDetailComponent;
  let fixture: ComponentFixture<AuthentificationTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthentificationTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ authentificationType: { authentificationTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AuthentificationTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AuthentificationTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load authentificationType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.authentificationType).toEqual(expect.objectContaining({ authentificationTypeId: 123 }));
    });
  });
});
