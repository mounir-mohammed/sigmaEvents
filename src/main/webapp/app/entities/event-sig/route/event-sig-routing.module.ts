import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventSigComponent } from '../list/event-sig.component';
import { EventSigDetailComponent } from '../detail/event-sig-detail.component';
import { EventSigUpdateComponent } from '../update/event-sig-update.component';
import { EventSigRoutingResolveService } from './event-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventRoute: Routes = [
  {
    path: '',
    component: EventSigComponent,
    data: {
      defaultSort: 'eventId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':eventId/view',
    component: EventSigDetailComponent,
    resolve: {
      event: EventSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventSigUpdateComponent,
    resolve: {
      event: EventSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':eventId/edit',
    component: EventSigUpdateComponent,
    resolve: {
      event: EventSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventRoute)],
  exports: [RouterModule],
})
export class EventSigRoutingModule {}
