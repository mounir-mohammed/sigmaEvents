import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportSigComponent } from './report/report-sig.component';
import { ReportSigRoutingModule } from './route/report-sig-routing.module';
import { NgChartsModule } from 'ng2-charts';

@NgModule({
  imports: [SharedModule, ReportSigRoutingModule, NgChartsModule],
  declarations: [ReportSigComponent],
})
export class ReportSigModule {}
