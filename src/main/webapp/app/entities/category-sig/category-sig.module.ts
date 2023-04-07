import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorySigComponent } from './list/category-sig.component';
import { CategorySigDetailComponent } from './detail/category-sig-detail.component';
import { CategorySigUpdateComponent } from './update/category-sig-update.component';
import { CategorySigDeleteDialogComponent } from './delete/category-sig-delete-dialog.component';
import { CategorySigRoutingModule } from './route/category-sig-routing.module';
import { ColorPickerModule } from 'ngx-color-picker';

@NgModule({
  imports: [SharedModule, CategorySigRoutingModule, ColorPickerModule],
  declarations: [CategorySigComponent, CategorySigDetailComponent, CategorySigUpdateComponent, CategorySigDeleteDialogComponent],
})
export class CategorySigModule {}
