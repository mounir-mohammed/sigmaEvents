import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrintingServerSigDetailComponent } from './printing-server-sig-detail.component';

describe('PrintingServerSig Management Detail Component', () => {
  let comp: PrintingServerSigDetailComponent;
  let fixture: ComponentFixture<PrintingServerSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrintingServerSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ printingServer: { printingServerId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrintingServerSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrintingServerSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load printingServer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.printingServer).toEqual(expect.objectContaining({ printingServerId: 123 }));
    });
  });
});
