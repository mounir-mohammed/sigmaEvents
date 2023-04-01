import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusSigComponent } from '../list/status-sig.component';
import { StatusSigDetailComponent } from '../detail/status-sig-detail.component';
import { StatusSigUpdateComponent } from '../update/status-sig-update.component';
import { StatusSigRoutingResolveService } from './status-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const statusRoute: Routes = [
  {
    path: '',
    component: StatusSigComponent,
    data: {
      defaultSort: 'statusId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':statusId/view',
    component: StatusSigDetailComponent,
    resolve: {
      status: StatusSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusSigUpdateComponent,
    resolve: {
      status: StatusSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':statusId/edit',
    component: StatusSigUpdateComponent,
    resolve: {
      status: StatusSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusRoute)],
  exports: [RouterModule],
})
export class StatusSigRoutingModule {}
