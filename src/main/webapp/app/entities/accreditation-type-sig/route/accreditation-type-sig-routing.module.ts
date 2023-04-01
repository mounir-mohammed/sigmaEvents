import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccreditationTypeSigComponent } from '../list/accreditation-type-sig.component';
import { AccreditationTypeSigDetailComponent } from '../detail/accreditation-type-sig-detail.component';
import { AccreditationTypeSigUpdateComponent } from '../update/accreditation-type-sig-update.component';
import { AccreditationTypeSigRoutingResolveService } from './accreditation-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const accreditationTypeRoute: Routes = [
  {
    path: '',
    component: AccreditationTypeSigComponent,
    data: {
      defaultSort: 'accreditationTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':accreditationTypeId/view',
    component: AccreditationTypeSigDetailComponent,
    resolve: {
      accreditationType: AccreditationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccreditationTypeSigUpdateComponent,
    resolve: {
      accreditationType: AccreditationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':accreditationTypeId/edit',
    component: AccreditationTypeSigUpdateComponent,
    resolve: {
      accreditationType: AccreditationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accreditationTypeRoute)],
  exports: [RouterModule],
})
export class AccreditationTypeSigRoutingModule {}
