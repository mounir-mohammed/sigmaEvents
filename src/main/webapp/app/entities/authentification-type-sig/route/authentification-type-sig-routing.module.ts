import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuthentificationTypeSigComponent } from '../list/authentification-type-sig.component';
import { AuthentificationTypeSigDetailComponent } from '../detail/authentification-type-sig-detail.component';
import { AuthentificationTypeSigUpdateComponent } from '../update/authentification-type-sig-update.component';
import { AuthentificationTypeSigRoutingResolveService } from './authentification-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const authentificationTypeRoute: Routes = [
  {
    path: '',
    component: AuthentificationTypeSigComponent,
    data: {
      defaultSort: 'authentificationTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':authentificationTypeId/view',
    component: AuthentificationTypeSigDetailComponent,
    resolve: {
      authentificationType: AuthentificationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuthentificationTypeSigUpdateComponent,
    resolve: {
      authentificationType: AuthentificationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':authentificationTypeId/edit',
    component: AuthentificationTypeSigUpdateComponent,
    resolve: {
      authentificationType: AuthentificationTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(authentificationTypeRoute)],
  exports: [RouterModule],
})
export class AuthentificationTypeSigRoutingModule {}
