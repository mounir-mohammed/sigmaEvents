jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { LogHistorySigService } from '../service/log-history-sig.service';

import { LogHistorySigDeleteDialogComponent } from './log-history-sig-delete-dialog.component';

describe('LogHistorySig Management Delete Component', () => {
  let comp: LogHistorySigDeleteDialogComponent;
  let fixture: ComponentFixture<LogHistorySigDeleteDialogComponent>;
  let service: LogHistorySigService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LogHistorySigDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(LogHistorySigDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LogHistorySigDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LogHistorySigService);
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
