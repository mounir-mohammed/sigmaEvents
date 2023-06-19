import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OperationHistorySigComponent } from '../list/operation-history-sig.component';
import { OperationHistorySigDetailComponent } from '../detail/operation-history-sig-detail.component';
import { OperationHistorySigUpdateComponent } from '../update/operation-history-sig-update.component';
import { OperationHistorySigRoutingResolveService } from './operation-history-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const operationHistoryRoute: Routes = [
  {
    path: '',
    component: OperationHistorySigComponent,
    data: {
      defaultSort: 'operationHistoryId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':operationHistoryId/view',
    component: OperationHistorySigDetailComponent,
    resolve: {
      operationHistory: OperationHistorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  // {
  //   path: 'new',
  //   component: OperationHistorySigUpdateComponent,
  //   resolve: {
  //     operationHistory: OperationHistorySigRoutingResolveService,
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: ':operationHistoryId/edit',
  //   component: OperationHistorySigUpdateComponent,
  //   resolve: {
  //     operationHistory: OperationHistorySigRoutingResolveService,
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];

@NgModule({
  imports: [RouterModule.forChild(operationHistoryRoute)],
  exports: [RouterModule],
})
export class OperationHistorySigRoutingModule {}
