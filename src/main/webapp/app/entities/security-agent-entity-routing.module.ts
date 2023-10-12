import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'accreditation',
        data: { pageTitle: 'sigmaEventsApp.accreditation.home.title' },
        loadChildren: () => import('./accreditation-sig/accreditation-sig-security.module').then(m => m.AccreditationSigSecurityModule),
      },
    ]),
  ],
})
export class SecurityAgentEntityRoutingModule {}
