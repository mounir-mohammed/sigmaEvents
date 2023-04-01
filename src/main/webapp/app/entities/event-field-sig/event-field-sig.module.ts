import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventFieldSigComponent } from './list/event-field-sig.component';
import { EventFieldSigDetailComponent } from './detail/event-field-sig-detail.component';
import { EventFieldSigUpdateComponent } from './update/event-field-sig-update.component';
import { EventFieldSigDeleteDialogComponent } from './delete/event-field-sig-delete-dialog.component';
import { EventFieldSigRoutingModule } from './route/event-field-sig-routing.module';

@NgModule({
  imports: [SharedModule, EventFieldSigRoutingModule],
  declarations: [EventFieldSigComponent, EventFieldSigDetailComponent, EventFieldSigUpdateComponent, EventFieldSigDeleteDialogComponent],
})
export class EventFieldSigModule {}
