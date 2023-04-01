import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CivilitySigComponent } from '../list/civility-sig.component';
import { CivilitySigDetailComponent } from '../detail/civility-sig-detail.component';
import { CivilitySigUpdateComponent } from '../update/civility-sig-update.component';
import { CivilitySigRoutingResolveService } from './civility-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const civilityRoute: Routes = [
  {
    path: '',
    component: CivilitySigComponent,
    data: {
      defaultSort: 'civilityId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':civilityId/view',
    component: CivilitySigDetailComponent,
    resolve: {
      civility: CivilitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CivilitySigUpdateComponent,
    resolve: {
      civility: CivilitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':civilityId/edit',
    component: CivilitySigUpdateComponent,
    resolve: {
      civility: CivilitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(civilityRoute)],
  exports: [RouterModule],
})
export class CivilitySigRoutingModule {}
