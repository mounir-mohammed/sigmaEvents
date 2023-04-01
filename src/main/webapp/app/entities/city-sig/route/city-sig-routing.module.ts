import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CitySigComponent } from '../list/city-sig.component';
import { CitySigDetailComponent } from '../detail/city-sig-detail.component';
import { CitySigUpdateComponent } from '../update/city-sig-update.component';
import { CitySigRoutingResolveService } from './city-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cityRoute: Routes = [
  {
    path: '',
    component: CitySigComponent,
    data: {
      defaultSort: 'cityId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cityId/view',
    component: CitySigDetailComponent,
    resolve: {
      city: CitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CitySigUpdateComponent,
    resolve: {
      city: CitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cityId/edit',
    component: CitySigUpdateComponent,
    resolve: {
      city: CitySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cityRoute)],
  exports: [RouterModule],
})
export class CitySigRoutingModule {}
