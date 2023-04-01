import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LanguageSigComponent } from '../list/language-sig.component';
import { LanguageSigDetailComponent } from '../detail/language-sig-detail.component';
import { LanguageSigUpdateComponent } from '../update/language-sig-update.component';
import { LanguageSigRoutingResolveService } from './language-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const languageRoute: Routes = [
  {
    path: '',
    component: LanguageSigComponent,
    data: {
      defaultSort: 'languageId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':languageId/view',
    component: LanguageSigDetailComponent,
    resolve: {
      language: LanguageSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LanguageSigUpdateComponent,
    resolve: {
      language: LanguageSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':languageId/edit',
    component: LanguageSigUpdateComponent,
    resolve: {
      language: LanguageSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(languageRoute)],
  exports: [RouterModule],
})
export class LanguageSigRoutingModule {}
