import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckAccreditationReportSigComponent } from '../list/check-accreditation-report-sig.component';
import { CheckAccreditationReportSigDetailComponent } from '../detail/check-accreditation-report-sig-detail.component';
import { CheckAccreditationReportSigUpdateComponent } from '../update/check-accreditation-report-sig-update.component';
import { CheckAccreditationReportSigRoutingResolveService } from './check-accreditation-report-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const checkAccreditationReportRoute: Routes = [
  {
    path: '',
    component: CheckAccreditationReportSigComponent,
    data: {
      defaultSort: 'checkAccreditationReportId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':checkAccreditationReportId/view',
    component: CheckAccreditationReportSigDetailComponent,
    resolve: {
      checkAccreditationReport: CheckAccreditationReportSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckAccreditationReportSigUpdateComponent,
    resolve: {
      checkAccreditationReport: CheckAccreditationReportSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':checkAccreditationReportId/edit',
    component: CheckAccreditationReportSigUpdateComponent,
    resolve: {
      checkAccreditationReport: CheckAccreditationReportSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkAccreditationReportRoute)],
  exports: [RouterModule],
})
export class CheckAccreditationReportSigRoutingModule {}
