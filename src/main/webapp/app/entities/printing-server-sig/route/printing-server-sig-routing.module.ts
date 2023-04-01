import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrintingServerSigComponent } from '../list/printing-server-sig.component';
import { PrintingServerSigDetailComponent } from '../detail/printing-server-sig-detail.component';
import { PrintingServerSigUpdateComponent } from '../update/printing-server-sig-update.component';
import { PrintingServerSigRoutingResolveService } from './printing-server-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const printingServerRoute: Routes = [
  {
    path: '',
    component: PrintingServerSigComponent,
    data: {
      defaultSort: 'printingServerId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingServerId/view',
    component: PrintingServerSigDetailComponent,
    resolve: {
      printingServer: PrintingServerSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrintingServerSigUpdateComponent,
    resolve: {
      printingServer: PrintingServerSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingServerId/edit',
    component: PrintingServerSigUpdateComponent,
    resolve: {
      printingServer: PrintingServerSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(printingServerRoute)],
  exports: [RouterModule],
})
export class PrintingServerSigRoutingModule {}
