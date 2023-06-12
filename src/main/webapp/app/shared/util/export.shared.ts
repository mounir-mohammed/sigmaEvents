import { TranslateService } from '@ngx-translate/core';
import * as XLSX from 'xlsx';

export class ExportUtil {
  private static translateService: TranslateService;

  static initialize(translateService: TranslateService): void {
    ExportUtil.translateService = translateService;
  }

  static exportTableToExcel(tableId: string, sheetNameKey: string, fileNameKey: string): void {
    console.log(tableId);
    console.log(sheetNameKey);
    console.log(fileNameKey);
    let fileName = this.translateService.instant(sheetNameKey);
    let sheetName = this.translateService.instant(fileNameKey);

    fileName = fileName + '.xlsx';
    let element = document.getElementById(tableId);
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    XLSX.writeFile(wb, this.generateFilenameWithDateTime(fileName));
  }

  static generateFilenameWithDateTime(filename: string): string {
    const currentDate = new Date();
    const formattedDate = currentDate.toISOString().slice(0, 10); // Format the date as YYYY-MM-DD
    const formattedTime = currentDate.toLocaleTimeString().replace(/:/g, '-'); // Format the time as HH-MM-SS

    const extensionIndex = filename.lastIndexOf('.');
    const filenameWithoutExtension = filename.substring(0, extensionIndex);
    const fileExtension = filename.substring(extensionIndex);

    const filenameWithDateTime = `${filenameWithoutExtension}_${formattedDate}_${formattedTime}${fileExtension}`;

    return filenameWithDateTime;
  }

  static downloadAccreditationModelFile(columnHeaders: any, sheetNameKey: string, entityNameKey: string, fileNameKey: string): void {
    let fileName = this.translateService.instant(sheetNameKey);
    let entityName = this.translateService.instant(entityNameKey);
    let sheetName = this.translateService.instant(fileNameKey);

    fileName = entityName + '_' + fileName + '.xlsx';
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet([{}], { header: columnHeaders });
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    XLSX.writeFile(wb, this.generateFilenameWithDateTime(fileName));
  }
}
