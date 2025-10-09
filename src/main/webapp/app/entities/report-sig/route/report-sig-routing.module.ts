import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportSigComponent } from '../report/report-sig.component';
import { ReportSigRoutingResolveService } from './report-sig-routing-resolve.service';

const settingRoute: Routes = [
  {
    path: '',
    component: ReportSigComponent,
    resolve: {
      setting: ReportSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingRoute)],
  exports: [RouterModule],
})
export class ReportSigRoutingModule {}
