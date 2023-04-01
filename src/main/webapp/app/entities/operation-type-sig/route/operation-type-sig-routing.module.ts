import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OperationTypeSigComponent } from '../list/operation-type-sig.component';
import { OperationTypeSigDetailComponent } from '../detail/operation-type-sig-detail.component';
import { OperationTypeSigUpdateComponent } from '../update/operation-type-sig-update.component';
import { OperationTypeSigRoutingResolveService } from './operation-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const operationTypeRoute: Routes = [
  {
    path: '',
    component: OperationTypeSigComponent,
    data: {
      defaultSort: 'operationTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':operationTypeId/view',
    component: OperationTypeSigDetailComponent,
    resolve: {
      operationType: OperationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OperationTypeSigUpdateComponent,
    resolve: {
      operationType: OperationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':operationTypeId/edit',
    component: OperationTypeSigUpdateComponent,
    resolve: {
      operationType: OperationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(operationTypeRoute)],
  exports: [RouterModule],
})
export class OperationTypeSigRoutingModule {}
