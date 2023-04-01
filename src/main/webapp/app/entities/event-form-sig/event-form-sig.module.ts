import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventFormSigComponent } from './list/event-form-sig.component';
import { EventFormSigDetailComponent } from './detail/event-form-sig-detail.component';
import { EventFormSigUpdateComponent } from './update/event-form-sig-update.component';
import { EventFormSigDeleteDialogComponent } from './delete/event-form-sig-delete-dialog.component';
import { EventFormSigRoutingModule } from './route/event-form-sig-routing.module';

@NgModule({
  imports: [SharedModule, EventFormSigRoutingModule],
  declarations: [EventFormSigComponent, EventFormSigDetailComponent, EventFormSigUpdateComponent, EventFormSigDeleteDialogComponent],
})
export class EventFormSigModule {}
