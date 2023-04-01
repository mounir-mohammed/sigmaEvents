import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SexeSigComponent } from '../list/sexe-sig.component';
import { SexeSigDetailComponent } from '../detail/sexe-sig-detail.component';
import { SexeSigUpdateComponent } from '../update/sexe-sig-update.component';
import { SexeSigRoutingResolveService } from './sexe-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const sexeRoute: Routes = [
  {
    path: '',
    component: SexeSigComponent,
    data: {
      defaultSort: 'sexeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':sexeId/view',
    component: SexeSigDetailComponent,
    resolve: {
      sexe: SexeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SexeSigUpdateComponent,
    resolve: {
      sexe: SexeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':sexeId/edit',
    component: SexeSigUpdateComponent,
    resolve: {
      sexe: SexeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sexeRoute)],
  exports: [RouterModule],
})
export class SexeSigRoutingModule {}
