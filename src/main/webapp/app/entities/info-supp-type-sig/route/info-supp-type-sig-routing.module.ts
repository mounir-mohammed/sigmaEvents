import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InfoSuppTypeSigComponent } from '../list/info-supp-type-sig.component';
import { InfoSuppTypeSigDetailComponent } from '../detail/info-supp-type-sig-detail.component';
import { InfoSuppTypeSigUpdateComponent } from '../update/info-supp-type-sig-update.component';
import { InfoSuppTypeSigRoutingResolveService } from './info-supp-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const infoSuppTypeRoute: Routes = [
  {
    path: '',
    component: InfoSuppTypeSigComponent,
    data: {
      defaultSort: 'infoSuppTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':infoSuppTypeId/view',
    component: InfoSuppTypeSigDetailComponent,
    resolve: {
      infoSuppType: InfoSuppTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InfoSuppTypeSigUpdateComponent,
    resolve: {
      infoSuppType: InfoSuppTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':infoSuppTypeId/edit',
    component: InfoSuppTypeSigUpdateComponent,
    resolve: {
      infoSuppType: InfoSuppTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(infoSuppTypeRoute)],
  exports: [RouterModule],
})
export class InfoSuppTypeSigRoutingModule {}
