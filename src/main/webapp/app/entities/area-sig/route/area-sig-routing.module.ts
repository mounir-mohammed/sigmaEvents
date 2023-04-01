import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AreaSigComponent } from '../list/area-sig.component';
import { AreaSigDetailComponent } from '../detail/area-sig-detail.component';
import { AreaSigUpdateComponent } from '../update/area-sig-update.component';
import { AreaSigRoutingResolveService } from './area-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const areaRoute: Routes = [
  {
    path: '',
    component: AreaSigComponent,
    data: {
      defaultSort: 'areaId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':areaId/view',
    component: AreaSigDetailComponent,
    resolve: {
      area: AreaSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaSigUpdateComponent,
    resolve: {
      area: AreaSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':areaId/edit',
    component: AreaSigUpdateComponent,
    resolve: {
      area: AreaSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(areaRoute)],
  exports: [RouterModule],
})
export class AreaSigRoutingModule {}
