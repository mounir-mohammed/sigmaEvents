import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataUtils } from 'app/core/util/data-util.service';

import { PrintingCentreSigDetailComponent } from './printing-centre-sig-detail.component';

describe('PrintingCentreSig Management Detail Component', () => {
  let comp: PrintingCentreSigDetailComponent;
  let fixture: ComponentFixture<PrintingCentreSigDetailComponent>;
  let dataUtils: DataUtils;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrintingCentreSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ printingCentre: { printingCentreId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrintingCentreSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrintingCentreSigDetailComponent);
    comp = fixture.componentInstance;
    dataUtils = TestBed.inject(DataUtils);
    jest.spyOn(window, 'open').mockImplementation(() => null);
  });

  describe('OnInit', () => {
    it('Should load printingCentre on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.printingCentre).toEqual(expect.objectContaining({ printingCentreId: 123 }));
    });
  });

  describe('byteSize', () => {
    it('Should call byteSize from DataUtils', () => {
      // GIVEN
      jest.spyOn(dataUtils, 'byteSize');
      const fakeBase64 = 'fake base64';

      // WHEN
      comp.byteSize(fakeBase64);

      // THEN
      expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
    });
  });

  describe('openFile', () => {
    it('Should call openFile from DataUtils', () => {
      const newWindow = { ...window };
      newWindow.document.write = jest.fn();
      window.open = jest.fn(() => newWindow);
      window.onload = jest.fn(() => newWindow);
      window.URL.createObjectURL = jest.fn();
      // GIVEN
      jest.spyOn(dataUtils, 'openFile');
      const fakeContentType = 'fake content type';
      const fakeBase64 = 'fake base64';

      // WHEN
      comp.openFile(fakeBase64, fakeContentType);

      // THEN
      expect(dataUtils.openFile).toBeCalledWith(fakeBase64, fakeContentType);
    });
  });
});
