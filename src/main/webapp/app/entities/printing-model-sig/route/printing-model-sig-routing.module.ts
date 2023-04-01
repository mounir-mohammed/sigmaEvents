import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrintingModelSigComponent } from '../list/printing-model-sig.component';
import { PrintingModelSigDetailComponent } from '../detail/printing-model-sig-detail.component';
import { PrintingModelSigUpdateComponent } from '../update/printing-model-sig-update.component';
import { PrintingModelSigRoutingResolveService } from './printing-model-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const printingModelRoute: Routes = [
  {
    path: '',
    component: PrintingModelSigComponent,
    data: {
      defaultSort: 'printingModelId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingModelId/view',
    component: PrintingModelSigDetailComponent,
    resolve: {
      printingModel: PrintingModelSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrintingModelSigUpdateComponent,
    resolve: {
      printingModel: PrintingModelSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingModelId/edit',
    component: PrintingModelSigUpdateComponent,
    resolve: {
      printingModel: PrintingModelSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(printingModelRoute)],
  exports: [RouterModule],
})
export class PrintingModelSigRoutingModule {}
