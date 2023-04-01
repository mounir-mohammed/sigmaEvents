import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AttachementTypeSigDetailComponent } from './attachement-type-sig-detail.component';

describe('AttachementTypeSig Management Detail Component', () => {
  let comp: AttachementTypeSigDetailComponent;
  let fixture: ComponentFixture<AttachementTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AttachementTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ attachementType: { attachementTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AttachementTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AttachementTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load attachementType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.attachementType).toEqual(expect.objectContaining({ attachementTypeId: 123 }));
    });
  });
});
