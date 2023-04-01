import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NationalitySigComponent } from '../list/nationality-sig.component';
import { NationalitySigDetailComponent } from '../detail/nationality-sig-detail.component';
import { NationalitySigUpdateComponent } from '../update/nationality-sig-update.component';
import { NationalitySigRoutingResolveService } from './nationality-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nationalityRoute: Routes = [
  {
    path: '',
    component: NationalitySigComponent,
    data: {
      defaultSort: 'nationalityId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nationalityId/view',
    component: NationalitySigDetailComponent,
    resolve: {
      nationality: NationalitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NationalitySigUpdateComponent,
    resolve: {
      nationality: NationalitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nationalityId/edit',
    component: NationalitySigUpdateComponent,
    resolve: {
      nationality: NationalitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nationalityRoute)],
  exports: [RouterModule],
})
export class NationalitySigRoutingModule {}
