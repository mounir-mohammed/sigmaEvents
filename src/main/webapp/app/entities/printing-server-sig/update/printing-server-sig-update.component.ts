import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrintingServerSigFormService, PrintingServerSigFormGroup } from './printing-server-sig-form.service';
import { IPrintingServerSig } from '../printing-server-sig.model';
import { PrintingServerSigService } from '../service/printing-server-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-printing-server-sig-update',
  templateUrl: './printing-server-sig-update.component.html',
})
export class PrintingServerSigUpdateComponent implements OnInit {
  isSaving = false;
  printingServer: IPrintingServerSig | null = null;

  eventsSharedCollection: IEventSig[] = [];

  editForm: PrintingServerSigFormGroup = this.printingServerFormService.createPrintingServerSigFormGroup();

  constructor(
    protected printingServerService: PrintingServerSigService,
    protected printingServerFormService: PrintingServerSigFormService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingServer }) => {
      this.printingServer = printingServer;
      if (printingServer) {
        this.updateForm(printingServer);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const printingServer = this.printingServerFormService.getPrintingServerSig(this.editForm);
    if (printingServer.printingServerId !== null) {
      this.subscribeToSaveResponse(this.printingServerService.update(printingServer));
    } else {
      this.subscribeToSaveResponse(this.printingServerService.create(printingServer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrintingServerSig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(printingServer: IPrintingServerSig): void {
    this.printingServer = printingServer;
    this.printingServerFormService.resetForm(this.editForm, printingServer);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      printingServer.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.printingServer?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
