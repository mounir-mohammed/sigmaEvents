jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';

import { InfoSuppTypeSigDeleteDialogComponent } from './info-supp-type-sig-delete-dialog.component';

describe('InfoSuppTypeSig Management Delete Component', () => {
  let comp: InfoSuppTypeSigDeleteDialogComponent;
  let fixture: ComponentFixture<InfoSuppTypeSigDeleteDialogComponent>;
  let service: InfoSuppTypeSigService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [InfoSuppTypeSigDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(InfoSuppTypeSigDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InfoSuppTypeSigDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InfoSuppTypeSigService);
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
