import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CodeSigComponent } from '../list/code-sig.component';
import { CodeSigDetailComponent } from '../detail/code-sig-detail.component';
import { CodeSigUpdateComponent } from '../update/code-sig-update.component';
import { CodeSigRoutingResolveService } from './code-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const codeRoute: Routes = [
  {
    path: '',
    component: CodeSigComponent,
    data: {
      defaultSort: 'codeId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':codeId/view',
    component: CodeSigDetailComponent,
    resolve: {
      code: CodeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CodeSigUpdateComponent,
    resolve: {
      code: CodeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':codeId/edit',
    component: CodeSigUpdateComponent,
    resolve: {
      code: CodeSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(codeRoute)],
  exports: [RouterModule],
})
export class CodeSigRoutingModule {}
