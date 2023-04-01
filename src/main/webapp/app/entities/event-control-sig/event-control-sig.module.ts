import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventControlSigComponent } from './list/event-control-sig.component';
import { EventControlSigDetailComponent } from './detail/event-control-sig-detail.component';
import { EventControlSigUpdateComponent } from './update/event-control-sig-update.component';
import { EventControlSigDeleteDialogComponent } from './delete/event-control-sig-delete-dialog.component';
import { EventControlSigRoutingModule } from './route/event-control-sig-routing.module';

@NgModule({
  imports: [SharedModule, EventControlSigRoutingModule],
  declarations: [
    EventControlSigComponent,
    EventControlSigDetailComponent,
    EventControlSigUpdateComponent,
    EventControlSigDeleteDialogComponent,
  ],
})
export class EventControlSigModule {}
