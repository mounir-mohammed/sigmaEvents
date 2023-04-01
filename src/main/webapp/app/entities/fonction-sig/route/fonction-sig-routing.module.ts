import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FonctionSigComponent } from '../list/fonction-sig.component';
import { FonctionSigDetailComponent } from '../detail/fonction-sig-detail.component';
import { FonctionSigUpdateComponent } from '../update/fonction-sig-update.component';
import { FonctionSigRoutingResolveService } from './fonction-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fonctionRoute: Routes = [
  {
    path: '',
    component: FonctionSigComponent,
    data: {
      defaultSort: 'fonctionId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':fonctionId/view',
    component: FonctionSigDetailComponent,
    resolve: {
      fonction: FonctionSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FonctionSigUpdateComponent,
    resolve: {
      fonction: FonctionSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':fonctionId/edit',
    component: FonctionSigUpdateComponent,
    resolve: {
      fonction: FonctionSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fonctionRoute)],
  exports: [RouterModule],
})
export class FonctionSigRoutingModule {}
