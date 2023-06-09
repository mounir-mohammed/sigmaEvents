import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccreditationSigComponent } from '../list/accreditation-sig.component';
import { AccreditationSigDetailComponent } from '../detail/accreditation-sig-detail.component';
import { AccreditationSigUpdateComponent } from '../update/accreditation-sig-update.component';
import { AccreditationSigRoutingResolveService } from './accreditation-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { AccreditationSigMassUpdateComponent } from '../mass-update/accreditation-sig-mass-update.component';

const accreditationRoute: Routes = [
  {
    path: '',
    component: AccreditationSigComponent,
    data: {
      defaultSort: 'accreditationId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':accreditationId/view',
    component: AccreditationSigDetailComponent,
    resolve: {
      accreditation: AccreditationSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccreditationSigUpdateComponent,
    resolve: {
      accreditation: AccreditationSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':accreditationId/edit',
    component: AccreditationSigUpdateComponent,
    resolve: {
      accreditation: AccreditationSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'massUpdate',
    component: AccreditationSigMassUpdateComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accreditationRoute)],
  exports: [RouterModule],
})
export class AccreditationSigRoutingModule {}
