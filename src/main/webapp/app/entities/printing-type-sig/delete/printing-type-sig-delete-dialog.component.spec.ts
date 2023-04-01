jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PrintingTypeSigService } from '../service/printing-type-sig.service';

import { PrintingTypeSigDeleteDialogComponent } from './printing-type-sig-delete-dialog.component';

describe('PrintingTypeSig Management Delete Component', () => {
  let comp: PrintingTypeSigDeleteDialogComponent;
  let fixture: ComponentFixture<PrintingTypeSigDeleteDialogComponent>;
  let service: PrintingTypeSigService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PrintingTypeSigDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PrintingTypeSigDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrintingTypeSigDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrintingTypeSigService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
