import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizSigComponent } from '../list/organiz-sig.component';
import { OrganizSigDetailComponent } from '../detail/organiz-sig-detail.component';
import { OrganizSigUpdateComponent } from '../update/organiz-sig-update.component';
import { OrganizSigRoutingResolveService } from './organiz-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const organizRoute: Routes = [
  {
    path: '',
    component: OrganizSigComponent,
    data: {
      defaultSort: 'organizId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizId/view',
    component: OrganizSigDetailComponent,
    resolve: {
      organiz: OrganizSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizSigUpdateComponent,
    resolve: {
      organiz: OrganizSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizId/edit',
    component: OrganizSigUpdateComponent,
    resolve: {
      organiz: OrganizSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizRoute)],
  exports: [RouterModule],
})
export class OrganizSigRoutingModule {}
