import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventControlSigComponent } from '../list/event-control-sig.component';
import { EventControlSigDetailComponent } from '../detail/event-control-sig-detail.component';
import { EventControlSigUpdateComponent } from '../update/event-control-sig-update.component';
import { EventControlSigRoutingResolveService } from './event-control-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventControlRoute: Routes = [
  {
    path: '',
    component: EventControlSigComponent,
    data: {
      defaultSort: 'controlId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':controlId/view',
    component: EventControlSigDetailComponent,
    resolve: {
      eventControl: EventControlSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventControlSigUpdateComponent,
    resolve: {
      eventControl: EventControlSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':controlId/edit',
    component: EventControlSigUpdateComponent,
    resolve: {
      eventControl: EventControlSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventControlRoute)],
  exports: [RouterModule],
})
export class EventControlSigRoutingModule {}
