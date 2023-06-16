import { TranslateService } from '@ngx-translate/core';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
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

  static downloadAccreditationModelFile(
    columnHeaders: any,
    sheetNameKey: string,
    entityNameKey: string,
    fileNameKey: string,
    OccupationNameKey: string,
    fonctionsSharedCollection: IFonctionSig[],
    genderNameKey: string,
    sexesSharedCollection: ISexeSig[],
    typeNameKey: string,
    typeSharedCollection: IAccreditationTypeSig[],
    organizNameKey: string,
    organizSharedCollection: IOrganizSig[],
    nationalityNameKey: string,
    nationalitySharedCollection: INationalitySig[],
    siteNameKey: string,
    siteSharedCollection: ISiteSig[]
  ): void {
    let fileName = this.translateService.instant(sheetNameKey);
    let entityName = this.translateService.instant(entityNameKey);
    let sheetName = this.translateService.instant(fileNameKey);
    let occupationSheetName = this.translateService.instant(OccupationNameKey);
    let genderSheetName = this.translateService.instant(genderNameKey);
    let typeSheetName = this.translateService.instant(typeNameKey);
    let organizSheetName = this.translateService.instant(organizNameKey);
    let nationalitySheetName = this.translateService.instant(nationalityNameKey);
    let siteSheetName = this.translateService.instant(siteNameKey);

    fileName = entityName + '_' + fileName + '.xlsx';
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet([{}], { header: columnHeaders });

    const wsOccupation: XLSX.WorkSheet = XLSX.utils.json_to_sheet(
      fonctionsSharedCollection.map(fonction => ({ VALUE: fonction.fonctionName }))
    );

    const wsGender: XLSX.WorkSheet = XLSX.utils.json_to_sheet(sexesSharedCollection.map(gender => ({ VALUE: gender.sexeValue })));

    const wsType: XLSX.WorkSheet = XLSX.utils.json_to_sheet(typeSharedCollection.map(type => ({ VALUE: type.accreditationTypeValue })));

    const wsOrganiz: XLSX.WorkSheet = XLSX.utils.json_to_sheet(organizSharedCollection.map(organiz => ({ VALUE: organiz.organizName })));

    const wsNationality: XLSX.WorkSheet = XLSX.utils.json_to_sheet(
      nationalitySharedCollection.map(nationality => ({ VALUE: nationality.nationalityValue }))
    );

    const wsSite: XLSX.WorkSheet = XLSX.utils.json_to_sheet(siteSharedCollection.map(site => ({ VALUE: site.siteName })));

    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    XLSX.utils.book_append_sheet(wb, wsGender, genderSheetName);
    XLSX.utils.book_append_sheet(wb, wsOccupation, occupationSheetName);
    XLSX.utils.book_append_sheet(wb, wsType, typeSheetName);
    XLSX.utils.book_append_sheet(wb, wsSite, siteSheetName);
    XLSX.utils.book_append_sheet(wb, wsOrganiz, organizSheetName);
    XLSX.utils.book_append_sheet(wb, wsNationality, nationalitySheetName);
    XLSX.writeFile(wb, this.generateFilenameWithDateTime(fileName));
  }
}
