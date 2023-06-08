import { Injectable } from '@angular/core';
import { AreaSigService } from 'app/entities/area-sig/service/area-sig.service';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { SettingSigService } from 'app/entities/setting-sig/service/setting-sig.service';

@Injectable({
  providedIn: 'root',
})
export class CacheService {
  constructor(
    protected settingService: SettingSigService,
    protected printingModelService: PrintingModelSigService,
    protected areaService: AreaSigService
  ) {}

  reset(): boolean {
    this.settingService.resetSettingCache();
    this.printingModelService.resetPrintingModelCache();
    this.areaService.resetAreasCache();
    return true;
  }
}
