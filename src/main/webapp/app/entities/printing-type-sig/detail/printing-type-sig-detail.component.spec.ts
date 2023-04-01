import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrintingTypeSigDetailComponent } from './printing-type-sig-detail.component';

describe('PrintingTypeSig Management Detail Component', () => {
  let comp: PrintingTypeSigDetailComponent;
  let fixture: ComponentFixture<PrintingTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrintingTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ printingType: { printingTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrintingTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrintingTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load printingType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.printingType).toEqual(expect.objectContaining({ printingTypeId: 123 }));
    });
  });
});
