import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'yesNo',
})
export class YesNoPipe implements PipeTransform {
  constructor(private translate: TranslateService) {}
  transform(value: any): string {
    if (value) {
      return this.translate.instant('yes');
    } else return this.translate.instant('no');
  }
}
