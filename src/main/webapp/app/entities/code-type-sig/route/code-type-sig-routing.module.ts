import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CodeTypeSigComponent } from '../list/code-type-sig.component';
import { CodeTypeSigDetailComponent } from '../detail/code-type-sig-detail.component';
import { CodeTypeSigUpdateComponent } from '../update/code-type-sig-update.component';
import { CodeTypeSigRoutingResolveService } from './code-type-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const codeTypeRoute: Routes = [
  {
    path: '',
    component: CodeTypeSigComponent,
    data: {
      defaultSort: 'codeTypeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':codeTypeId/view',
    component: CodeTypeSigDetailComponent,
    resolve: {
      codeType: CodeTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CodeTypeSigUpdateComponent,
    resolve: {
      codeType: CodeTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':codeTypeId/edit',
    component: CodeTypeSigUpdateComponent,
    resolve: {
      codeType: CodeTypeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(codeTypeRoute)],
  exports: [RouterModule],
})
export class CodeTypeSigRoutingModule {}
