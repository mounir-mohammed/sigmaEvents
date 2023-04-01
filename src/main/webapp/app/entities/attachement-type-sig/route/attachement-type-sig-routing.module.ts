import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AttachementTypeSigComponent } from '../list/attachement-type-sig.component';
import { AttachementTypeSigDetailComponent } from '../detail/attachement-type-sig-detail.component';
import { AttachementTypeSigUpdateComponent } from '../update/attachement-type-sig-update.component';
import { AttachementTypeSigRoutingResolveService } from './attachement-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const attachementTypeRoute: Routes = [
  {
    path: '',
    component: AttachementTypeSigComponent,
    data: {
      defaultSort: 'attachementTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':attachementTypeId/view',
    component: AttachementTypeSigDetailComponent,
    resolve: {
      attachementType: AttachementTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttachementTypeSigUpdateComponent,
    resolve: {
      attachementType: AttachementTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':attachementTypeId/edit',
    component: AttachementTypeSigUpdateComponent,
    resolve: {
      attachementType: AttachementTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(attachementTypeRoute)],
  exports: [RouterModule],
})
export class AttachementTypeSigRoutingModule {}
