import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DayPassInfoSigComponent } from '../list/day-pass-info-sig.component';
import { DayPassInfoSigDetailComponent } from '../detail/day-pass-info-sig-detail.component';
import { DayPassInfoSigUpdateComponent } from '../update/day-pass-info-sig-update.component';
import { DayPassInfoSigRoutingResolveService } from './day-pass-info-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dayPassInfoRoute: Routes = [
  {
    path: '',
    component: DayPassInfoSigComponent,
    data: {
      defaultSort: 'dayPassInfoId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':dayPassInfoId/view',
    component: DayPassInfoSigDetailComponent,
    resolve: {
      dayPassInfo: DayPassInfoSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DayPassInfoSigUpdateComponent,
    resolve: {
      dayPassInfo: DayPassInfoSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':dayPassInfoId/edit',
    component: DayPassInfoSigUpdateComponent,
    resolve: {
      dayPassInfo: DayPassInfoSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dayPassInfoRoute)],
  exports: [RouterModule],
})
export class DayPassInfoSigRoutingModule {}
