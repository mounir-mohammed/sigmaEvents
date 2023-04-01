import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventFormSigComponent } from '../list/event-form-sig.component';
import { EventFormSigDetailComponent } from '../detail/event-form-sig-detail.component';
import { EventFormSigUpdateComponent } from '../update/event-form-sig-update.component';
import { EventFormSigRoutingResolveService } from './event-form-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventFormRoute: Routes = [
  {
    path: '',
    component: EventFormSigComponent,
    data: {
      defaultSort: 'formId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':formId/view',
    component: EventFormSigDetailComponent,
    resolve: {
      eventForm: EventFormSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventFormSigUpdateComponent,
    resolve: {
      eventForm: EventFormSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':formId/edit',
    component: EventFormSigUpdateComponent,
    resolve: {
      eventForm: EventFormSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventFormRoute)],
  exports: [RouterModule],
})
export class EventFormSigRoutingModule {}
