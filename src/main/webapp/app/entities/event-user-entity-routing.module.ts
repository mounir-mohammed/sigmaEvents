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
      {
        path: 'report-sig',
        data: { pageTitle: 'global.menu.entities.reportSig' },
        loadChildren: () => import('./report-sig/report-sig.module').then(m => m.ReportSigModule),
      },
    ]),
  ],
})
export class EventUserEntityRoutingModule {}
