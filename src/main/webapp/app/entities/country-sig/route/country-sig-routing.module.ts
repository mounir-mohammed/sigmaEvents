import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CountrySigComponent } from '../list/country-sig.component';
import { CountrySigDetailComponent } from '../detail/country-sig-detail.component';
import { CountrySigUpdateComponent } from '../update/country-sig-update.component';
import { CountrySigRoutingResolveService } from './country-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const countryRoute: Routes = [
  {
    path: '',
    component: CountrySigComponent,
    data: {
      defaultSort: 'countryId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':countryId/view',
    component: CountrySigDetailComponent,
    resolve: {
      country: CountrySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CountrySigUpdateComponent,
    resolve: {
      country: CountrySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':countryId/edit',
    component: CountrySigUpdateComponent,
    resolve: {
      country: CountrySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(countryRoute)],
  exports: [RouterModule],
})
export class CountrySigRoutingModule {}
