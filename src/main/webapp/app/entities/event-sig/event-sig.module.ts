import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventSigComponent } from './list/event-sig.component';
import { EventSigDetailComponent } from './detail/event-sig-detail.component';
import { EventSigUpdateComponent } from './update/event-sig-update.component';
import { EventSigDeleteDialogComponent } from './delete/event-sig-delete-dialog.component';
import { EventSigRoutingModule } from './route/event-sig-routing.module';

@NgModule({
  imports: [SharedModule, EventSigRoutingModule],
  declarations: [EventSigComponent, EventSigDetailComponent, EventSigUpdateComponent, EventSigDeleteDialogComponent],
})
export class EventSigModule {}
