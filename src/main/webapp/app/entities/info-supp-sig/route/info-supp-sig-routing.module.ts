import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InfoSuppSigComponent } from '../list/info-supp-sig.component';
import { InfoSuppSigDetailComponent } from '../detail/info-supp-sig-detail.component';
import { InfoSuppSigUpdateComponent } from '../update/info-supp-sig-update.component';
import { InfoSuppSigRoutingResolveService } from './info-supp-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const infoSuppRoute: Routes = [
  {
    path: '',
    component: InfoSuppSigComponent,
    data: {
      defaultSort: 'infoSuppId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':infoSuppId/view',
    component: InfoSuppSigDetailComponent,
    resolve: {
      infoSupp: InfoSuppSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InfoSuppSigUpdateComponent,
    resolve: {
      infoSupp: InfoSuppSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':infoSuppId/edit',
    component: InfoSuppSigUpdateComponent,
    resolve: {
      infoSupp: InfoSuppSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(infoSuppRoute)],
  exports: [RouterModule],
})
export class InfoSuppSigRoutingModule {}
