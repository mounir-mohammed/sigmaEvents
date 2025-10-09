import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'area-sig',
        data: { pageTitle: 'sigmaEventsApp.area.home.title' },
        loadChildren: () => import('./area-sig/area-sig.module').then(m => m.AreaSigModule),
      },
      {
        path: 'fonction-sig',
        data: { pageTitle: 'sigmaEventsApp.fonction.home.title' },
        loadChildren: () => import('./fonction-sig/fonction-sig.module').then(m => m.FonctionSigModule),
      },
      {
        path: 'category-sig',
        data: { pageTitle: 'sigmaEventsApp.category.home.title' },
        loadChildren: () => import('./category-sig/category-sig.module').then(m => m.CategorySigModule),
      },
      {
        path: 'code-sig',
        data: { pageTitle: 'sigmaEventsApp.code.home.title' },
        loadChildren: () => import('./code-sig/code-sig.module').then(m => m.CodeSigModule),
      },
      {
        path: 'info-supp-sig',
        data: { pageTitle: 'sigmaEventsApp.infoSupp.home.title' },
        loadChildren: () => import('./info-supp-sig/info-supp-sig.module').then(m => m.InfoSuppSigModule),
      },
      {
        path: 'attachement-sig',
        data: { pageTitle: 'sigmaEventsApp.attachement.home.title' },
        loadChildren: () => import('./attachement-sig/attachement-sig.module').then(m => m.AttachementSigModule),
      },
      {
        path: 'organiz-sig',
        data: { pageTitle: 'sigmaEventsApp.organiz.home.title' },
        loadChildren: () => import('./organiz-sig/organiz-sig.module').then(m => m.OrganizSigModule),
      },
      {
        path: 'photo-archive-sig',
        data: { pageTitle: 'sigmaEventsApp.photoArchive.home.title' },
        loadChildren: () => import('./photo-archive-sig/photo-archive-sig.module').then(m => m.PhotoArchiveSigModule),
      },
      {
        path: 'site-sig',
        data: { pageTitle: 'sigmaEventsApp.site.home.title' },
        loadChildren: () => import('./site-sig/site-sig.module').then(m => m.SiteSigModule),
      },
      {
        path: 'accreditation-sig',
        data: { pageTitle: 'sigmaEventsApp.accreditation.home.title' },
        loadChildren: () => import('./accreditation-sig/accreditation-sig.module').then(m => m.AccreditationSigModule),
      },
      {
        path: 'day-pass-info-sig',
        data: { pageTitle: 'sigmaEventsApp.dayPassInfo.home.title' },
        loadChildren: () => import('./day-pass-info-sig/day-pass-info-sig.module').then(m => m.DayPassInfoSigModule),
      },
      {
        path: 'note-sig',
        data: { pageTitle: 'sigmaEventsApp.note.home.title' },
        loadChildren: () => import('./note-sig/note-sig.module').then(m => m.NoteSigModule),
      },
      {
        path: 'operation-history-sig',
        data: { pageTitle: 'sigmaEventsApp.operationHistory.home.title' },
        loadChildren: () => import('./operation-history-sig/operation-history-sig.module').then(m => m.OperationHistorySigModule),
      },
      {
        path: 'report-sig',
        data: { pageTitle: 'global.menu.entities.reportSig' },
        loadChildren: () => import('./report-sig/report-sig.module').then(m => m.ReportSigModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EventAdminEntityRoutingModule {}
