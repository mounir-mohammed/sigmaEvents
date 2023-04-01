import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrintingCentreSigComponent } from '../list/printing-centre-sig.component';
import { PrintingCentreSigDetailComponent } from '../detail/printing-centre-sig-detail.component';
import { PrintingCentreSigUpdateComponent } from '../update/printing-centre-sig-update.component';
import { PrintingCentreSigRoutingResolveService } from './printing-centre-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const printingCentreRoute: Routes = [
  {
    path: '',
    component: PrintingCentreSigComponent,
    data: {
      defaultSort: 'printingCentreId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingCentreId/view',
    component: PrintingCentreSigDetailComponent,
    resolve: {
      printingCentre: PrintingCentreSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrintingCentreSigUpdateComponent,
    resolve: {
      printingCentre: PrintingCentreSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingCentreId/edit',
    component: PrintingCentreSigUpdateComponent,
    resolve: {
      printingCentre: PrintingCentreSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(printingCentreRoute)],
  exports: [RouterModule],
})
export class PrintingCentreSigRoutingModule {}
