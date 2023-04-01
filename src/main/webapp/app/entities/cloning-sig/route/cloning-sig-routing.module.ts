import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CloningSigComponent } from '../list/cloning-sig.component';
import { CloningSigDetailComponent } from '../detail/cloning-sig-detail.component';
import { CloningSigUpdateComponent } from '../update/cloning-sig-update.component';
import { CloningSigRoutingResolveService } from './cloning-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cloningRoute: Routes = [
  {
    path: '',
    component: CloningSigComponent,
    data: {
      defaultSort: 'cloningId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cloningId/view',
    component: CloningSigDetailComponent,
    resolve: {
      cloning: CloningSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CloningSigUpdateComponent,
    resolve: {
      cloning: CloningSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cloningId/edit',
    component: CloningSigUpdateComponent,
    resolve: {
      cloning: CloningSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cloningRoute)],
  exports: [RouterModule],
})
export class CloningSigRoutingModule {}
