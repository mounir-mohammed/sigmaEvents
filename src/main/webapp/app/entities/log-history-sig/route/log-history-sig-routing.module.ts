import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LogHistorySigComponent } from '../list/log-history-sig.component';
import { LogHistorySigDetailComponent } from '../detail/log-history-sig-detail.component';
import { LogHistorySigUpdateComponent } from '../update/log-history-sig-update.component';
import { LogHistorySigRoutingResolveService } from './log-history-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const logHistoryRoute: Routes = [
  {
    path: '',
    component: LogHistorySigComponent,
    data: {
      defaultSort: 'logHistory,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':logHistory/view',
    component: LogHistorySigDetailComponent,
    resolve: {
      logHistory: LogHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogHistorySigUpdateComponent,
    resolve: {
      logHistory: LogHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':logHistory/edit',
    component: LogHistorySigUpdateComponent,
    resolve: {
      logHistory: LogHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(logHistoryRoute)],
  exports: [RouterModule],
})
export class LogHistorySigRoutingModule {}
