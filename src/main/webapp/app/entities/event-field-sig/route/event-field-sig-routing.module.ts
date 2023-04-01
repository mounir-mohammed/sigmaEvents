import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventFieldSigComponent } from '../list/event-field-sig.component';
import { EventFieldSigDetailComponent } from '../detail/event-field-sig-detail.component';
import { EventFieldSigUpdateComponent } from '../update/event-field-sig-update.component';
import { EventFieldSigRoutingResolveService } from './event-field-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventFieldRoute: Routes = [
  {
    path: '',
    component: EventFieldSigComponent,
    data: {
      defaultSort: 'fieldId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':fieldId/view',
    component: EventFieldSigDetailComponent,
    resolve: {
      eventField: EventFieldSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventFieldSigUpdateComponent,
    resolve: {
      eventField: EventFieldSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':fieldId/edit',
    component: EventFieldSigUpdateComponent,
    resolve: {
      eventField: EventFieldSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventFieldRoute)],
  exports: [RouterModule],
})
export class EventFieldSigRoutingModule {}
