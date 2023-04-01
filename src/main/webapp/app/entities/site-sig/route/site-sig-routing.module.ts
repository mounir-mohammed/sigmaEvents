import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiteSigComponent } from '../list/site-sig.component';
import { SiteSigDetailComponent } from '../detail/site-sig-detail.component';
import { SiteSigUpdateComponent } from '../update/site-sig-update.component';
import { SiteSigRoutingResolveService } from './site-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const siteRoute: Routes = [
  {
    path: '',
    component: SiteSigComponent,
    data: {
      defaultSort: 'siteId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':siteId/view',
    component: SiteSigDetailComponent,
    resolve: {
      site: SiteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteSigUpdateComponent,
    resolve: {
      site: SiteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':siteId/edit',
    component: SiteSigUpdateComponent,
    resolve: {
      site: SiteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siteRoute)],
  exports: [RouterModule],
})
export class SiteSigRoutingModule {}
