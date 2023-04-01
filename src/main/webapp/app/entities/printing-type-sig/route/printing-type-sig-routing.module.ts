import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrintingTypeSigComponent } from '../list/printing-type-sig.component';
import { PrintingTypeSigDetailComponent } from '../detail/printing-type-sig-detail.component';
import { PrintingTypeSigUpdateComponent } from '../update/printing-type-sig-update.component';
import { PrintingTypeSigRoutingResolveService } from './printing-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const printingTypeRoute: Routes = [
  {
    path: '',
    component: PrintingTypeSigComponent,
    data: {
      defaultSort: 'printingTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingTypeId/view',
    component: PrintingTypeSigDetailComponent,
    resolve: {
      printingType: PrintingTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrintingTypeSigUpdateComponent,
    resolve: {
      printingType: PrintingTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':printingTypeId/edit',
    component: PrintingTypeSigUpdateComponent,
    resolve: {
      printingType: PrintingTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(printingTypeRoute)],
  exports: [RouterModule],
})
export class PrintingTypeSigRoutingModule {}
