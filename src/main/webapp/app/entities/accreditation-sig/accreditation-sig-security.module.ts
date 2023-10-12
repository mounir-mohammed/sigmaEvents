import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NgxPhotoEditorModule } from 'ngx-photo-editor';
import { AccreditationSigCheckComponent } from './check/accreditation-sig-check.component';
import { AccreditationSigSecurityRoutingModule } from './route/accreditation-sig-security-routing.module';
import { AccreditationSigNotFoundComponent } from './check/accreditation-sig-not-found.component';

@NgModule({
  imports: [SharedModule, AccreditationSigSecurityRoutingModule, NgxPhotoEditorModule],
  declarations: [AccreditationSigCheckComponent, AccreditationSigNotFoundComponent],
})
export class AccreditationSigSecurityModule {}
