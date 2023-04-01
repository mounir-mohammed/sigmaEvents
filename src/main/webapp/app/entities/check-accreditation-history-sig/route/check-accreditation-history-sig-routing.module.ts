import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckAccreditationHistorySigComponent } from '../list/check-accreditation-history-sig.component';
import { CheckAccreditationHistorySigDetailComponent } from '../detail/check-accreditation-history-sig-detail.component';
import { CheckAccreditationHistorySigUpdateComponent } from '../update/check-accreditation-history-sig-update.component';
import { CheckAccreditationHistorySigRoutingResolveService } from './check-accreditation-history-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const checkAccreditationHistoryRoute: Routes = [
  {
    path: '',
    component: CheckAccreditationHistorySigComponent,
    data: {
      defaultSort: 'checkAccreditationHistoryId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':checkAccreditationHistoryId/view',
    component: CheckAccreditationHistorySigDetailComponent,
    resolve: {
      checkAccreditationHistory: CheckAccreditationHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckAccreditationHistorySigUpdateComponent,
    resolve: {
      checkAccreditationHistory: CheckAccreditationHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':checkAccreditationHistoryId/edit',
    component: CheckAccreditationHistorySigUpdateComponent,
    resolve: {
      checkAccreditationHistory: CheckAccreditationHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkAccreditationHistoryRoute)],
  exports: [RouterModule],
})
export class CheckAccreditationHistorySigRoutingModule {}
