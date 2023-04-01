import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event-sig',
        data: { pageTitle: 'sigmaEventsApp.event.home.title' },
        loadChildren: () => import('./event-sig/event-sig.module').then(m => m.EventSigModule),
      },
      {
        path: 'printing-type-sig',
        data: { pageTitle: 'sigmaEventsApp.printingType.home.title' },
        loadChildren: () => import('./printing-type-sig/printing-type-sig.module').then(m => m.PrintingTypeSigModule),
      },
      {
        path: 'log-history-sig',
        data: { pageTitle: 'sigmaEventsApp.logHistory.home.title' },
        loadChildren: () => import('./log-history-sig/log-history-sig.module').then(m => m.LogHistorySigModule),
      },
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
        path: 'printing-model-sig',
        data: { pageTitle: 'sigmaEventsApp.printingModel.home.title' },
        loadChildren: () => import('./printing-model-sig/printing-model-sig.module').then(m => m.PrintingModelSigModule),
      },
      {
        path: 'code-type-sig',
        data: { pageTitle: 'sigmaEventsApp.codeType.home.title' },
        loadChildren: () => import('./code-type-sig/code-type-sig.module').then(m => m.CodeTypeSigModule),
      },
      {
        path: 'code-sig',
        data: { pageTitle: 'sigmaEventsApp.code.home.title' },
        loadChildren: () => import('./code-sig/code-sig.module').then(m => m.CodeSigModule),
      },
      {
        path: 'accreditation-type-sig',
        data: { pageTitle: 'sigmaEventsApp.accreditationType.home.title' },
        loadChildren: () => import('./accreditation-type-sig/accreditation-type-sig.module').then(m => m.AccreditationTypeSigModule),
      },
      {
        path: 'authentification-type-sig',
        data: { pageTitle: 'sigmaEventsApp.authentificationType.home.title' },
        loadChildren: () =>
          import('./authentification-type-sig/authentification-type-sig.module').then(m => m.AuthentificationTypeSigModule),
      },
      {
        path: 'status-sig',
        data: { pageTitle: 'sigmaEventsApp.status.home.title' },
        loadChildren: () => import('./status-sig/status-sig.module').then(m => m.StatusSigModule),
      },
      {
        path: 'attachement-type-sig',
        data: { pageTitle: 'sigmaEventsApp.attachementType.home.title' },
        loadChildren: () => import('./attachement-type-sig/attachement-type-sig.module').then(m => m.AttachementTypeSigModule),
      },
      {
        path: 'info-supp-type-sig',
        data: { pageTitle: 'sigmaEventsApp.infoSuppType.home.title' },
        loadChildren: () => import('./info-supp-type-sig/info-supp-type-sig.module').then(m => m.InfoSuppTypeSigModule),
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
        path: 'civility-sig',
        data: { pageTitle: 'sigmaEventsApp.civility.home.title' },
        loadChildren: () => import('./civility-sig/civility-sig.module').then(m => m.CivilitySigModule),
      },
      {
        path: 'photo-archive-sig',
        data: { pageTitle: 'sigmaEventsApp.photoArchive.home.title' },
        loadChildren: () => import('./photo-archive-sig/photo-archive-sig.module').then(m => m.PhotoArchiveSigModule),
      },
      {
        path: 'nationality-sig',
        data: { pageTitle: 'sigmaEventsApp.nationality.home.title' },
        loadChildren: () => import('./nationality-sig/nationality-sig.module').then(m => m.NationalitySigModule),
      },
      {
        path: 'country-sig',
        data: { pageTitle: 'sigmaEventsApp.country.home.title' },
        loadChildren: () => import('./country-sig/country-sig.module').then(m => m.CountrySigModule),
      },
      {
        path: 'city-sig',
        data: { pageTitle: 'sigmaEventsApp.city.home.title' },
        loadChildren: () => import('./city-sig/city-sig.module').then(m => m.CitySigModule),
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
        path: 'operation-type-sig',
        data: { pageTitle: 'sigmaEventsApp.operationType.home.title' },
        loadChildren: () => import('./operation-type-sig/operation-type-sig.module').then(m => m.OperationTypeSigModule),
      },
      {
        path: 'sexe-sig',
        data: { pageTitle: 'sigmaEventsApp.sexe.home.title' },
        loadChildren: () => import('./sexe-sig/sexe-sig.module').then(m => m.SexeSigModule),
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
        path: 'printing-centre-sig',
        data: { pageTitle: 'sigmaEventsApp.printingCentre.home.title' },
        loadChildren: () => import('./printing-centre-sig/printing-centre-sig.module').then(m => m.PrintingCentreSigModule),
      },
      {
        path: 'language-sig',
        data: { pageTitle: 'sigmaEventsApp.language.home.title' },
        loadChildren: () => import('./language-sig/language-sig.module').then(m => m.LanguageSigModule),
      },
      {
        path: 'setting-sig',
        data: { pageTitle: 'sigmaEventsApp.setting.home.title' },
        loadChildren: () => import('./setting-sig/setting-sig.module').then(m => m.SettingSigModule),
      },
      {
        path: 'cloning-sig',
        data: { pageTitle: 'sigmaEventsApp.cloning.home.title' },
        loadChildren: () => import('./cloning-sig/cloning-sig.module').then(m => m.CloningSigModule),
      },
      {
        path: 'printing-server-sig',
        data: { pageTitle: 'sigmaEventsApp.printingServer.home.title' },
        loadChildren: () => import('./printing-server-sig/printing-server-sig.module').then(m => m.PrintingServerSigModule),
      },
      {
        path: 'check-accreditation-history-sig',
        data: { pageTitle: 'sigmaEventsApp.checkAccreditationHistory.home.title' },
        loadChildren: () =>
          import('./check-accreditation-history-sig/check-accreditation-history-sig.module').then(
            m => m.CheckAccreditationHistorySigModule
          ),
      },
      {
        path: 'check-accreditation-report-sig',
        data: { pageTitle: 'sigmaEventsApp.checkAccreditationReport.home.title' },
        loadChildren: () =>
          import('./check-accreditation-report-sig/check-accreditation-report-sig.module').then(m => m.CheckAccreditationReportSigModule),
      },
      {
        path: 'event-form-sig',
        data: { pageTitle: 'sigmaEventsApp.eventForm.home.title' },
        loadChildren: () => import('./event-form-sig/event-form-sig.module').then(m => m.EventFormSigModule),
      },
      {
        path: 'event-field-sig',
        data: { pageTitle: 'sigmaEventsApp.eventField.home.title' },
        loadChildren: () => import('./event-field-sig/event-field-sig.module').then(m => m.EventFieldSigModule),
      },
      {
        path: 'event-control-sig',
        data: { pageTitle: 'sigmaEventsApp.eventControl.home.title' },
        loadChildren: () => import('./event-control-sig/event-control-sig.module').then(m => m.EventControlSigModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
