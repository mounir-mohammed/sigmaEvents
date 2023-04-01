import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AttachementSigComponent } from '../list/attachement-sig.component';
import { AttachementSigDetailComponent } from '../detail/attachement-sig-detail.component';
import { AttachementSigUpdateComponent } from '../update/attachement-sig-update.component';
import { AttachementSigRoutingResolveService } from './attachement-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const attachementRoute: Routes = [
  {
    path: '',
    component: AttachementSigComponent,
    data: {
      defaultSort: 'attachementId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':attachementId/view',
    component: AttachementSigDetailComponent,
    resolve: {
      attachement: AttachementSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttachementSigUpdateComponent,
    resolve: {
      attachement: AttachementSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':attachementId/edit',
    component: AttachementSigUpdateComponent,
    resolve: {
      attachement: AttachementSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(attachementRoute)],
  exports: [RouterModule],
})
export class AttachementSigRoutingModule {}
