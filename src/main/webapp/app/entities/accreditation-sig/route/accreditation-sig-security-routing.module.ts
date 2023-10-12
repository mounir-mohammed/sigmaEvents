import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccreditationSigCheckComponent } from '../check/accreditation-sig-check.component';
import { SecurityAgentEventCanAccesEntities } from 'app/core/auth/security-agent-event-can-acces-entites';
import { AccreditationSigSecurityRoutingResolveService } from './accreditation-sig-security-routing-resolve.service';
import { AccreditationSigNotFoundComponent } from '../check/accreditation-sig-not-found.component';

const accreditationRoute: Routes = [
  {
    path: ':accreditationId/verify',
    component: AccreditationSigCheckComponent,
    resolve: {
      accreditation: AccreditationSigSecurityRoutingResolveService,
    },
    canActivate: [SecurityAgentEventCanAccesEntities],
  },
  {
    path: 'notfound',
    component: AccreditationSigNotFoundComponent,
    resolve: {
      accreditation: AccreditationSigSecurityRoutingResolveService,
    },
    canActivate: [SecurityAgentEventCanAccesEntities],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accreditationRoute)],
  exports: [RouterModule],
})
export class AccreditationSigSecurityRoutingModule {}
