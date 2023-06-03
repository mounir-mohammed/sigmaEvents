import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'accreditation-sig',
        data: { pageTitle: 'sigmaEventsApp.accreditation.home.title' },
        loadChildren: () => import('./accreditation-sig/accreditation-sig.module').then(m => m.AccreditationSigModule),
      },
    ]),
  ],
})
export class EventUserEntityRoutingModule {}
