import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorySigComponent } from '../list/category-sig.component';
import { CategorySigDetailComponent } from '../detail/category-sig-detail.component';
import { CategorySigUpdateComponent } from '../update/category-sig-update.component';
import { CategorySigRoutingResolveService } from './category-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoryRoute: Routes = [
  {
    path: '',
    component: CategorySigComponent,
    data: {
      defaultSort: 'categoryId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':categoryId/view',
    component: CategorySigDetailComponent,
    resolve: {
      category: CategorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorySigUpdateComponent,
    resolve: {
      category: CategorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':categoryId/edit',
    component: CategorySigUpdateComponent,
    resolve: {
      category: CategorySigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoryRoute)],
  exports: [RouterModule],
})
export class CategorySigRoutingModule {}
