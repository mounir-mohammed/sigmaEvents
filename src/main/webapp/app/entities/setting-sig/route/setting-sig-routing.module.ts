import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingSigComponent } from '../list/setting-sig.component';
import { SettingSigDetailComponent } from '../detail/setting-sig-detail.component';
import { SettingSigUpdateComponent } from '../update/setting-sig-update.component';
import { SettingSigRoutingResolveService } from './setting-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const settingRoute: Routes = [
  {
    path: '',
    component: SettingSigComponent,
    data: {
      defaultSort: 'settingId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':settingId/view',
    component: SettingSigDetailComponent,
    resolve: {
      setting: SettingSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SettingSigUpdateComponent,
    resolve: {
      setting: SettingSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':settingId/edit',
    component: SettingSigUpdateComponent,
    resolve: {
      setting: SettingSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingRoute)],
  exports: [RouterModule],
})
export class SettingSigRoutingModule {}
