import { Injectable } from '@angular/core';
import { CodeType } from 'app/config/codeType';
import { FieldType } from 'app/config/fieldType';
import { SettingType } from 'app/config/settingType';
import { SourceType } from 'app/config/sourceType';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { AreaSigService } from 'app/entities/area-sig/service/area-sig.service';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { SettingSigService } from 'app/entities/setting-sig/service/setting-sig.service';
import { CodeUtil } from 'app/shared/util/code.shared';
import { ExportUtil } from 'app/shared/util/export.shared';
import { Util } from 'app/shared/util/util.shred';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';

@Injectable({
  providedIn: 'root',
})
export class BadgeUtils {
  constructor(
    protected dataUtils: DataUtils,
    protected settingSigService: SettingSigService,
    protected accreditationSigService: AccreditationSigService,
    protected areaSigService: AreaSigService,
    protected printingModelSigService: PrintingModelSigService
  ) {}

  addImages(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addImages');
    return new Promise(async resolve => {
      try {
        if (dataModel.images) {
          for (let image of dataModel.images) {
            if (this.addCondition(image, data)) {
              var img = document.createElement('img');
              img.id = image.name;
              if (image.sourceType == SourceType.ATTRIBUTE) {
                img.src =
                  'data:' +
                  this.dataUtils.searchElementFromJson(image.contentType, data) +
                  ';base64,' +
                  this.dataUtils.searchElementFromJson(image.path, data);
              } else if (image.sourceType == SourceType.SETTING) {
                await this.settingSigService.getSetting(image.settingId).then(setting => {
                  if (setting?.settingType == SettingType.IMAGE) {
                    img.src = 'data:' + setting?.settingValueBlobContentType + ';base64,' + setting?.settingValueBlob;
                  }
                });
              }

              img.style.display = image.display;
              img.style.position = image.position;
              img.style.left = image.x;
              img.style.top = image.y;
              img.style.zIndex = image.z;
              img.style.margin = image.margin;
              img.style.padding = image.padding;
              img.style.border = image.border;
              img.style.verticalAlign = image.verticalAlign;
              img.style.width = image.width;
              img.style.height = image.height;
              img.style.maxWidth = image.maxWidth;
              img.style.maxHeight = image.maxHeight;
              img.hidden = image.hidden;
              if (img.src.length < 30) {
                console.log('Image DATA < 30 ! : ' + image.name);
                img.style.visibility = 'hidden';
              }

              if (image.groupName == null) {
                parent?.appendChild(img);
              } else {
                Array.prototype.forEach.call(groupDivs, groupDiv => {
                  if (groupDiv.id === image.groupName) {
                    groupDiv?.appendChild(img);
                  }
                });
              }
            }
          }
        } else {
          console.log('NO IMAGES FOUND');
          resolve(false);
        }
        console.log('END addImages');
        resolve(true);
      } catch (error: any) {
        console.error(error.message);
        resolve(false);
      }
    });
  }

  addCadres(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addCadres');
    return new Promise(async resolve => {
      try {
        if (dataModel.cadres) {
          for (let cadre of dataModel.cadres) {
            if (this.addCondition(cadre, data)) {
              var cadreDiv = document.createElement('div');
              cadreDiv.id = cadre.name;
              cadreDiv.style.display = cadre.display;
              cadreDiv.style.position = cadre.position;
              cadreDiv.style.left = cadre.x;
              cadreDiv.style.top = cadre.y;
              cadreDiv.style.zIndex = cadre.z;
              cadreDiv.style.margin = cadre.margin;
              cadreDiv.style.padding = cadre.padding;
              cadreDiv.style.backgroundColor = this.dataUtils.searchElementFromJson(cadre.DynamicBackgroundColor, data)
                ? this.dataUtils.searchElementFromJson(cadre.DynamicBackgroundColor, data)
                : cadre.backgroundColor;
              cadreDiv.style.color = this.dataUtils.searchElementFromJson(cadre.DynamicColor, data)
                ? this.dataUtils.searchElementFromJson(cadre.DynamicColor, data)
                : cadre.color;
              cadreDiv.style.width = cadre.width;
              cadreDiv.style.height = cadre.height;
              cadreDiv.style.maxWidth = cadre.maxWidth;
              cadreDiv.style.maxHeight = cadre.maxHeight;
              cadreDiv.style.border = cadre.border;
              cadreDiv.style.verticalAlign = cadre.verticalAlign;
              cadreDiv.hidden = cadre.hidden;

              if (cadre.groupName == null) {
                parent?.appendChild(cadreDiv);
              } else {
                Array.prototype.forEach.call(groupDivs, groupDiv => {
                  if (groupDiv.id === cadre.groupName) {
                    groupDiv?.appendChild(cadreDiv);
                  }
                });
              }
            }
          }
        } else {
          console.log('NO CADRES FOUND');
          resolve(false);
        }
        console.log('END addCadres');
        resolve(true);
      } catch (error: any) {
        console.error(error.message);
        resolve(false);
      }
    });
  }

  createGroups(parent: any, dataModel: any, data: any): Promise<Array<any>[] | null> {
    console.log('START createGroups()');
    return new Promise(async resolve => {
      try {
        var groupDivs: Array<any> = [];
        if (dataModel.groups) {
          for (let group of dataModel.groups) {
            if (this.addCondition(group, data)) {
              var groupDiv = document.createElement('div');
              groupDiv.id = group.name;
              groupDiv.classList.add(group.name);
              groupDiv.style.position = group.position;
              groupDiv.style.left = group.x;
              groupDiv.style.top = group.y;
              groupDiv.style.zIndex = group.z;
              groupDiv.style.margin = group.margin;
              groupDiv.style.padding = group.padding;
              groupDiv.style.backgroundColor = this.dataUtils.searchElementFromJson(group.DynamicBackgroundColor, data)
                ? this.dataUtils.searchElementFromJson(group.DynamicBackgroundColor, data)
                : group.backgroundColor;
              groupDiv.style.color = this.dataUtils.searchElementFromJson(group.DynamicColor, data)
                ? this.dataUtils.searchElementFromJson(group.DynamicColor, data)
                : group.color;
              groupDiv.style.border = group.border;
              groupDiv.style.display = group.display;
              groupDiv.style.verticalAlign = group.verticalAlign;
              groupDiv.style.tableLayout = group.tableLayout;
              groupDiv.style.width = group.width;
              groupDiv.style.height = group.height;
              groupDiv.style.maxWidth = group.maxWidth;
              groupDiv.style.maxHeight = group.maxHeight;
              groupDiv.style.borderSpacing = group.borderSpacing;
              groupDiv.hidden = group.hidden;
              groupDivs.push(groupDiv);
            }
          }
        } else {
          console.log('NO GROUPS FOUND');
        }
        console.log('END createGroups()');
        resolve(groupDivs);
      } catch (error: any) {
        console.error(error.message);
        resolve(null);
      }
    });
  }

  addFields(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addFields()');
    return new Promise(async resolve => {
      try {
        if (dataModel.fields) {
          for (let element of dataModel.fields) {
            if (this.addCondition(element, data)) {
              if (element.type == FieldType.TEXT) {
                var field = document.createElement('div');
                field.id = element.name;
                var text = '';
                if (element.sourceType == SourceType.SETTING) {
                  await this.settingSigService.getSetting(element.settingId).then(setting => {
                    if (setting?.settingType == SettingType.TEXT) {
                      text = setting.settingValueString!;
                    }
                  });
                } else {
                  text = this.dataUtils.searchElementFromJson(element.path, data);
                }
                if (element.toUpperCase) {
                  if (text) {
                    text = text.toString().toUpperCase().trim();
                  }
                }
                field.textContent = text;
                field.style.display = element.display;
                field.style.position = element.position;
                field.style.left = element.x;
                field.style.top = element.y;
                field.style.zIndex = element.z;
                field.style.margin = element.margin;
                field.style.padding = element.padding;
                field.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
                  ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
                  : element.backgroundColor;
                field.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, data)
                  ? this.dataUtils.searchElementFromJson(element.DynamicColor, data)
                  : element.color;
                field.style.textAlign = element.textAlign;
                field.style.fontFamily = element.fontFamily;
                field.style.fontStyle = element.fontStyle;
                if (element.calculatedFontSize) {
                  field.style.fontSize = this.calculateFontSize(
                    element.maxWidth,
                    element.minFontSize,
                    element.maxFontSize,
                    text,
                    element.fontUnite
                  );
                } else {
                  field.style.fontSize = element.fontSize;
                }
                field.style.fontWeight = element.fontWeight;
                field.style.border = element.border;
                field.style.whiteSpace = element.whiteSpace;
                field.style.verticalAlign = element.verticalAlign;
                field.style.width = element.width;
                field.style.height = element.height;
                field.style.maxWidth = element.maxWidth;
                field.style.maxHeight = element.maxHeight;
                field.hidden = element.hidden;
                if (element.groupName == null) {
                  parent?.appendChild(field);
                } else {
                  Array.prototype.forEach.call(groupDivs, groupDiv => {
                    if (groupDiv.id === element.groupName) {
                      groupDiv?.appendChild(field);
                    }
                  });
                }
              } else if (element.type == FieldType.CONCAT) {
                var field = document.createElement('div');
                field.id = element.name;
                var text = '';
                element.childFields.forEach((childField: any) => {
                  if (this.dataUtils.searchElementFromJson(childField.path, data) !== null) {
                    text = text + this.dataUtils.searchElementFromJson(childField.path, data);
                  }
                  if (childField.separator) {
                    text = text + childField.separator;
                  }
                });
                if (element.toUpperCase) {
                  if (text) {
                    text = text.toString().toUpperCase().trim();
                  }
                }
                field.textContent = text;
                field.style.display = element.display;
                field.style.position = element.position;
                field.style.left = element.x;
                field.style.top = element.y;
                field.style.zIndex = element.z;
                field.style.margin = element.margin;
                field.style.padding = element.padding;
                field.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
                  ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
                  : element.backgroundColor;
                field.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, data)
                  ? this.dataUtils.searchElementFromJson(element.DynamicColor, data)
                  : element.color;
                field.style.textAlign = element.textAlign;
                field.style.fontFamily = element.fontFamily;
                field.style.fontStyle = element.fontStyle;
                if (element.calculatedFontSize) {
                  field.style.fontSize = this.calculateFontSize(
                    element.maxWidth,
                    element.minFontSize,
                    element.maxFontSize,
                    text,
                    element.fontUnite
                  );
                } else {
                  field.style.fontSize = element.fontSize;
                }
                field.style.fontWeight = element.fontWeight;
                field.style.border = element.border;
                field.style.whiteSpace = element.whiteSpace;
                field.style.verticalAlign = element.verticalAlign;
                field.style.width = element.width;
                field.style.height = element.height;
                field.style.maxWidth = element.maxWidth;
                field.style.maxHeight = element.maxHeight;
                field.hidden = element.hidden;
                if (element.groupName == null) {
                  parent?.appendChild(field);
                } else {
                  Array.prototype.forEach.call(groupDivs, groupDiv => {
                    if (groupDiv.id === element.groupName) {
                      groupDiv?.appendChild(field);
                    }
                  });
                }
              } else if (element.type == FieldType.LIST) {
                var list = this.dataUtils.searchElementFromJson(element.listPath, data);
                if (element.order) {
                  list.sort((a: any, b: any) => {
                    if (element.orderType == 'desc') {
                      return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() <
                        this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                        ? 1
                        : -1;
                    } else {
                      return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() >
                        this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                        ? 1
                        : -1;
                    }
                  });
                }
                list.forEach((el: any) => {
                  var elJsonJson = JSON.stringify(el);
                  var elData = JSON.parse(elJsonJson);
                  var fieldEl = document.createElement('div');
                  fieldEl.id = element.name;
                  var text = this.dataUtils.searchElementFromJson(element.path, elData);
                  if (element.toUpperCase) {
                    text = text.toString().toUpperCase().trim();
                  }
                  fieldEl.textContent = text;
                  fieldEl.style.display = element.display;
                  fieldEl.style.position = element.position;
                  fieldEl.style.left = element.x;
                  fieldEl.style.top = element.y;
                  fieldEl.style.zIndex = element.z;
                  fieldEl.style.margin = element.margin;
                  fieldEl.style.padding = element.padding;
                  fieldEl.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                    ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                    : element.backgroundColor;
                  fieldEl.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                    ? this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                    : element.color;
                  fieldEl.style.textAlign = element.textAlign;
                  fieldEl.style.fontFamily = element.fontFamily;
                  fieldEl.style.fontStyle = element.fontStyle;
                  if (element.calculatedFontSize) {
                    fieldEl.style.fontSize = this.calculateFontSize(
                      element.maxWidth,
                      element.minFontSize,
                      element.maxFontSize,
                      text,
                      element.fontUnite
                    );
                  } else {
                    fieldEl.style.fontSize = element.fontSize;
                  }
                  fieldEl.style.fontWeight = element.fontWeight;
                  fieldEl.style.border = element.border;
                  fieldEl.style.whiteSpace = element.whiteSpace;
                  fieldEl.style.verticalAlign = element.verticalAlign;
                  fieldEl.style.width = element.width;
                  fieldEl.style.height = element.height;
                  fieldEl.style.maxWidth = element.maxWidth;
                  fieldEl.style.maxHeight = element.maxHeight;
                  fieldEl.hidden = element.hidden;
                  if (element.groupName == null) {
                    parent?.appendChild(field);
                  } else {
                    Array.prototype.forEach.call(groupDivs, groupDiv => {
                      if (groupDiv.id === element.groupName) {
                        groupDiv?.appendChild(fieldEl);
                      }
                    });
                  }
                });
              } else if (element.type == FieldType.TABLE) {
                if (element.fixedPosition) {
                  if (element.listPath.toString().indexOf('areas')) {
                    this.areaSigService.getAllAreas().then(allareas => {
                      var list = this.dataUtils.searchElementFromJson(element.listPath, data);
                      let allareasIds = list.map((a: { areaId: any }) => a.areaId);
                      if (element.order) {
                        list.sort((a: any, b: any) => {
                          if (element.orderType == 'desc') {
                            return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() <
                              this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                              ? 1
                              : -1;
                          } else {
                            return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() >
                              this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                              ? 1
                              : -1;
                          }
                        });
                      }
                      var rows: Array<Node> = [];
                      for (var i = 0; i < element.rowNbr; i++) {
                        var row = document.createElement('div');
                        row.id = 'row-' + element.name + '-' + i;
                        row.style.display = 'table-row';
                        rows.push(row);
                      }

                      let x: number = 0;
                      let y: number = 0;
                      allareas.forEach((el: any) => {
                        var elJsonJson = JSON.stringify(el);
                        var elData = JSON.parse(elJsonJson);
                        var fieldEl = document.createElement('div');
                        fieldEl.id = element.name;
                        fieldEl.style.display = element.display;
                        fieldEl.style.position = element.position;
                        fieldEl.style.left = element.x;
                        fieldEl.style.top = element.y;
                        fieldEl.style.zIndex = element.z;
                        fieldEl.style.margin = element.margin;
                        fieldEl.style.padding = element.padding;
                        fieldEl.style.textAlign = element.textAlign;
                        fieldEl.style.fontFamily = element.fontFamily;
                        fieldEl.style.fontStyle = element.fontStyle;
                        fieldEl.style.fontWeight = element.fontWeight;
                        fieldEl.style.border = element.border;
                        fieldEl.style.whiteSpace = element.whiteSpace;
                        fieldEl.style.verticalAlign = element.verticalAlign;
                        fieldEl.style.width = element.width;
                        fieldEl.style.height = element.height;
                        fieldEl.style.maxWidth = element.maxWidth;
                        fieldEl.style.maxHeight = element.maxHeight;
                        fieldEl.hidden = element.hidden;
                        var text = this.dataUtils.searchElementFromJson(element.path, elData);
                        if (element.toUpperCase) {
                          text = text.toString().toUpperCase().trim();
                        }
                        if (element.calculatedFontSize) {
                          fieldEl.style.fontSize = this.calculateFontSize(
                            element.maxWidth,
                            element.minFontSize,
                            element.maxFontSize,
                            text,
                            element.fontUnite
                          );
                        } else {
                          fieldEl.style.fontSize = element.fontSize;
                        }
                        fieldEl.textContent = text;
                        fieldEl.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                          ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                          : element.backgroundColor;
                        fieldEl.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                          ? this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                          : element.color;
                        if (!allareasIds.includes(this.dataUtils.searchElementFromJson('areaId', elData))) {
                          fieldEl.style.visibility = 'hidden';
                        }

                        rows[x]?.appendChild(fieldEl);
                        y = y + 1;
                        if (y == element.columnNbr) {
                          x = x + 1;
                          y = 0;
                        }
                      });
                      if (element.groupName == null) {
                        rows.forEach(row => {
                          parent?.appendChild(row);
                        });
                      } else {
                        Array.prototype.forEach.call(groupDivs, groupDiv => {
                          if (groupDiv.id === element.groupName) {
                            rows.forEach(row => {
                              groupDiv?.appendChild(row);
                            });
                          }
                        });
                      }
                    });
                  }
                } else {
                  var list = this.dataUtils.searchElementFromJson(element.listPath, data);
                  if (element.order) {
                    list.sort((a: any, b: any) => {
                      if (element.orderType == 'desc') {
                        return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() <
                          this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                          ? 1
                          : -1;
                      } else {
                        return this.dataUtils.searchElementFromJson(element.orderBy, a).toString() >
                          this.dataUtils.searchElementFromJson(element.orderBy, b).toString()
                          ? 1
                          : -1;
                      }
                    });
                  }
                  var rows: Array<Node> = [];
                  for (var i = 0; i < element.rowNbr; i++) {
                    var row = document.createElement('div');
                    row.id = 'row-' + element.name + '-' + i;
                    row.style.display = 'table-row';
                    rows.push(row);
                  }

                  let x: number = 0;
                  let y: number = 0;
                  list.forEach((el: any) => {
                    var elJsonJson = JSON.stringify(el);
                    var elData = JSON.parse(elJsonJson);
                    var fieldEl = document.createElement('div');
                    fieldEl.id = element.name;
                    var text = this.dataUtils.searchElementFromJson(element.path, elData);
                    if (element.toUpperCase) {
                      text = text.toString().toUpperCase().trim();
                    }
                    fieldEl.textContent = text;
                    fieldEl.style.display = element.display;
                    fieldEl.style.position = element.position;
                    fieldEl.style.left = element.x;
                    fieldEl.style.top = element.y;
                    fieldEl.style.zIndex = element.z;
                    fieldEl.style.margin = element.margin;
                    fieldEl.style.padding = element.padding;
                    fieldEl.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                      ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, elData)
                      : element.backgroundColor;
                    fieldEl.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                      ? this.dataUtils.searchElementFromJson(element.DynamicColor, elData)
                      : element.color;
                    fieldEl.style.textAlign = element.textAlign;
                    fieldEl.style.fontFamily = element.fontFamily;
                    fieldEl.style.fontStyle = element.fontStyle;
                    if (element.calculatedFontSize) {
                      fieldEl.style.fontSize = this.calculateFontSize(
                        element.maxWidth,
                        element.minFontSize,
                        element.maxFontSize,
                        text,
                        element.fontUnite
                      );
                    } else {
                      fieldEl.style.fontSize = element.fontSize;
                    }
                    fieldEl.style.fontWeight = element.fontWeight;
                    fieldEl.style.border = element.border;
                    fieldEl.style.whiteSpace = element.whiteSpace;
                    fieldEl.style.verticalAlign = element.verticalAlign;
                    fieldEl.style.width = element.width;
                    fieldEl.style.height = element.height;
                    fieldEl.style.maxWidth = element.maxWidth;
                    fieldEl.style.maxHeight = element.maxHeight;
                    fieldEl.hidden = element.hidden;

                    rows[x]?.appendChild(fieldEl);
                    y = y + 1;
                    if (y == element.columnNbr) {
                      x = x + 1;
                      y = 0;
                    }
                  });
                  if (element.groupName == null) {
                    rows.forEach(row => {
                      parent?.appendChild(row);
                    });
                  } else {
                    Array.prototype.forEach.call(groupDivs, groupDiv => {
                      if (groupDiv.id === element.groupName) {
                        rows.forEach(row => {
                          groupDiv?.appendChild(row);
                        });
                      }
                    });
                  }
                }
              }
            }
          }
        } else {
          console.log('NO FIELDS FOUND');
          resolve(false);
        }

        Array.prototype.forEach.call(groupDivs, groupDiv => {
          parent?.appendChild(groupDiv);
        });
        console.log('END addFields()');
        resolve(true);
      } catch (error: any) {
        console.error(error.message);
        resolve(false);
      }
    });
  }

  generateBadge(accreditation?: IAccreditationSig, modelData?: any, badgeContainerId?: string): Promise<Boolean> {
    console.log('START generateadge()');
    return new Promise(async resolve => {
      if (modelData) {
        try {
          if (accreditation) {
            await this.accreditationSigService.getAccreditation(accreditation?.accreditationId!).then(accreditationFullData => {
              if (accreditationFullData) {
                if (!accreditationFullData.accreditationPrintNumber || accreditationFullData.accreditationPrintNumber == 0) {
                  accreditationFullData.accreditationPrintNumber = 1;
                } else {
                  accreditationFullData.accreditationPrintNumber = accreditationFullData.accreditationPrintNumber + 1;
                }
                var accreditationJson = JSON.stringify(accreditationFullData);
                var data = JSON.parse(accreditationJson);
                var badgeContainer = document.getElementById(badgeContainerId!);
                //generate badge
                var badge = document.createElement('div');
                var badgeId =
                  accreditation?.event?.eventAbreviation + '_' + accreditation?.event?.eventId + '_' + accreditation?.accreditationId;
                badge.id = badgeId;
                badge.style.width = modelData.printingModel.page.width;
                badge.style.height = modelData.printingModel.page.height;
                badge.style.margin = modelData.printingModel.page.margin;
                badge.style.border = modelData.printingModel.page.border;
                badge.style.position = modelData.printingModel.page.position;

                //add groups
                this.createGroups(badge, modelData.printingModel, data).then(groupDivs => {
                  //add fields
                  this.addFields(badge, modelData.printingModel, groupDivs!, data).then(() => {
                    // add images
                    this.addImages(badge, modelData.printingModel, groupDivs!, data).then(() => {
                      //add cadres
                      this.addCadres(badge, modelData.printingModel, groupDivs!, data).then(() => {
                        //add codes
                        this.addCodes(badge, modelData.printingModel, groupDivs!, data).then(() => {
                          this.deplaceGroupToParent(badge, modelData.printingModel).then(() => {
                            badgeContainer?.appendChild(badge);
                            console.log('END generateadge()');
                            return resolve(true);
                          });
                        });
                      });
                    });
                  });
                });
              } else {
                console.error('ERROR get Accreditation FULL DATA');
                return resolve(false);
              }
            });
          } else {
            console.error('ERROR Accreditation ID IS NULL');
            return resolve(false);
          }
        } catch (error: any) {
          console.error(error.message);
          resolve(false);
        }
      } else {
        console.error('generateBadge() modelData IS EMPTY');
        resolve(false);
      }
    });
  }

  print(badgeId: string, modelData: any): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      try {
        console.log('START print()');
        let data = document.getElementById(badgeId);
        if (data && modelData) {
          html2canvas(data, { scale: modelData.printingModel.model.scale }).then(canvas => {
            const contentDataURL = canvas.toDataURL(modelData.printingModel.model.type, modelData.printingModel.model.quality);
            let pdf = null;
            if (modelData.printingModel.model.manualFormat) {
              pdf = new jspdf(modelData.printingModel.model.landScape, modelData.printingModel.model.unite, [
                modelData.printingModel.model.widthFormat,
                modelData.printingModel.model.heightFormat,
              ]);
            } else {
              pdf = new jspdf(
                modelData.printingModel.model.landScape,
                modelData.printingModel.model.unite,
                modelData.printingModel.model.format
              );
            }
            //landscape values p/l
            pdf.addImage(
              contentDataURL,
              modelData.printingModel.model.typeImage,
              modelData.printingModel.model.x,
              modelData.printingModel.model.y,
              modelData.printingModel.model.w,
              modelData.printingModel.model.h
            );
            pdf.autoPrint();
            let fileN = ExportUtil.generateFilenameWithDateTime(badgeId + '.pdf');
            pdf.setProperties({
              title: fileN,
              author: 'www.sigmaEvents.ma',
              creator: 'www.sigmaEvents.ma',
              subject: 'Accreditation',
            });
            if (modelData.printingModel.model.autoPrint == true) {
              window.open(pdf.output('bloburl'));
            } else {
              pdf.save(fileN);
            }
          });
          resolve();
        }
      } catch (error: any) {
        reject();
        console.error(error.message);
      }
      console.log('END print()');
    });
  }

  massPrint(badges: Map<string, any>): Promise<void> {
    return new Promise<void>(async (resolve, reject) => {
      console.log('START massPrint()');
      try {
        if (badges) {
          this.dataUtils.sortBadgeMap(badges).then(sortedBadges => {
            const template = sortedBadges.values().next().value;
            this.generateSelectedBadges(sortedBadges).then(pdf => {
              let fileN = ExportUtil.generateFilenameWithDateTime('ACC_' + badges.size + '.pdf');
              pdf.setProperties({
                title: fileN,
                author: 'www.sigmaEvents.ma',
                creator: 'www.sigmaEvents.ma',
              });
              if (template.printingModel.model.autoPrint == true) {
                window.open(pdf.output('bloburl'));
              } else {
                pdf.save(fileN);
              }
              resolve();
            });
          });
        } else {
          reject();
          console.error('NO ACC TO PRINT');
        }
      } catch (error: any) {
        reject();
        console.error(error.message);
      }
      console.log('END massPrint()');
    });
  }

  generateSelectedBadges(badges: Map<string, any>): Promise<jspdf> {
    return new Promise<jspdf>(async (resolve, reject) => {
      console.log('START generateSelectedBadges()');
      if (badges) {
        const totalBadges = badges.size;
        let currentIndex = 1;
        const template = badges.values().next().value;
        let pdf = new jspdf(
          template.printingModel.model.landScape,
          template.printingModel.model.unite,
          template.printingModel.model.format
        );

        for (const [badgeId, modelData] of badges) {
          const data = document.getElementById(badgeId);
          if (data && modelData) {
            const canvas = await html2canvas(data, { scale: modelData.printingModel.model.scale });
            const contentDataURL = canvas.toDataURL(modelData.printingModel.model.type, modelData.printingModel.model.quality);
            pdf.addImage(
              contentDataURL,
              modelData.printingModel.model.typeImage,
              modelData.printingModel.model.x,
              modelData.printingModel.model.y,
              modelData.printingModel.model.w,
              modelData.printingModel.model.h
            );
            if (currentIndex !== totalBadges) {
              pdf.addPage();
            }

            currentIndex++;
          }
        }
        resolve(pdf);
      } else {
        reject();
        console.error('NO ACC TO PRINT');
      }
      console.log('END generateSelectedBadges()');
    });
  }

  addCondition(element: any, data: any): boolean {
    try {
      console.log('START addCondition()');
      if (!element.condition) {
        return true;
      } else {
        var toAdd = false;
        var element1: any;
        var element2: any;

        if (element.conditionElement1) {
          element1 = this.dataUtils.searchElementFromJson(element.conditionElement1, data);
          if (element.conditionAttribute1) {
            if (element.conditionAttribute1 == 'count') {
              element1 = element1.length;
            }
          }
        } else if (element.conditionElement1Fixed) {
          element1 = element.conditionElement1Fixed;
        }

        if (element.conditionElement2) {
          element2 = this.dataUtils.searchElementFromJson(element.conditionElement2, data);
          if (element.conditionAttribute2) {
            if (element.conditionAttribute2 == 'count') {
              element2 = element2.length;
            }
          }
        } else if (element.conditionElement2Fixed) {
          element2 = element.conditionElement2Fixed;
        }

        if (element.conditionTest == '=') {
          toAdd = element1! == element2!;
        } else if (element.conditionTest == '>') {
          toAdd = element1! > element2!;
        } else if (element.conditionTest == '<') {
          toAdd = element1! < element2!;
        } else if (element.conditionTest == '>=') {
          toAdd = element1! >= element2!;
        } else if (element.conditionTest == '<=') {
          toAdd = element1! <= element2!;
        } else if (element.conditionTest == '!=') {
          toAdd = element1! != element2!;
        } else if (element.conditionTest == 'contain') {
          toAdd = element1!.contain(element2!);
        }
        console.log('END addCondition()');
        return toAdd;
      }
    } catch (error: any) {
      console.error(error.message);
      return true;
    }
  }

  deplaceGroupToParent(parent: any, dataModel: any): Promise<Boolean> {
    console.log('START deplaceGroupToParent()');
    return new Promise(resolve => {
      try {
        if (dataModel.groups) {
          dataModel.groups.forEach((group: any) => {
            if (group.groupName) {
              var childGroupe = parent.getElementsByClassName(group.name)[0];
              var groupParent = parent.getElementsByClassName(group.groupName)[0];
              groupParent?.appendChild(childGroupe!);
            }
          });
          resolve(true);
        } else {
          console.log('NO GROUPS FOUND');
          resolve(false);
        }
        console.log('END deplaceGroupToParent()');
      } catch (error: any) {
        console.error(error.message);
        resolve(false);
      }
    });
  }

  calculateFontSize(
    divWidthString: string,
    desiredFontSizeMinString: string,
    desiredFontSizeMaxString: string,
    text: string,
    unite: string
  ): string {
    try {
      console.log('start calculateFontSize()');

      const textLength = text ? text.length : 0;
      const divWidth = Util.extractNumericValue(divWidthString, unite);
      const desiredFontSizeMax = Util.extractNumericValue(desiredFontSizeMaxString, unite);
      const desiredFontSizeMin = Util.extractNumericValue(desiredFontSizeMinString, unite);
      // Calculate the ratio of the div width to the text length
      const ratio = divWidth / textLength;

      // Calculate the font size based on the ratio within the desired font size range
      const calculatedFontSize = Math.max(
        Math.min(ratio, desiredFontSizeMax), // Upper bound
        desiredFontSizeMin // Lower bound
      );
      console.log('END calculateFontSize()');
      return calculatedFontSize.toString() + unite;
    } catch (error: any) {
      console.error(error.message);
      return '10px';
    }
  }

  addCodes(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addCodes()');
    return new Promise(async resolve => {
      try {
        if (dataModel.codes) {
          for (let code of dataModel.codes) {
            if (this.addCondition(code, data)) {
              var img = document.createElement('img');
              img.id = code.name;

              if (code.sourceType == SourceType.SETTING) {
                await this.settingSigService.getSetting(code.settingId).then(setting => {
                  if (setting?.settingType == CodeType.BAR_CODE) {
                    img.src = CodeUtil.getBarCodeData(
                      setting.settingValueString!,
                      code.codeFormat,
                      code.displayValue,
                      Util.extractNumericValue(code.maxWidth, 'px'),
                      Util.extractNumericValue(code.maxHeight, 'px')
                    );
                  } else {
                    img.src = CodeUtil.getQrCodeData(setting!.settingValueString!);
                  }
                });
              } else {
                if (code.type == FieldType.CONCAT) {
                  var text = '';
                  code.childFields.forEach((childField: any) => {
                    if (this.dataUtils.searchElementFromJson(childField.path, data) !== null) {
                      text = text + this.dataUtils.searchElementFromJson(childField.path, data);
                    }
                    if (childField.separator) {
                      text = text + childField.separator;
                    }
                  });
                  if (code.toUpperCase) {
                    if (text) {
                      text = text.toString().toUpperCase().trim();
                    }
                  }
                  img.src = CodeUtil.getQrCodeData(text);
                } else {
                  if (this.dataUtils.searchElementFromJson(code.codeTypePath, data) == CodeType.BAR_CODE) {
                    if (this.dataUtils.searchElementFromJson(code.codeValuepath, data)) {
                      img.src = CodeUtil.getBarCodeData(
                        this.dataUtils.searchElementFromJson(code.codeValuepath, data),
                        code.codeFormat,
                        code.displayValue,
                        Util.extractNumericValue(code.maxWidth, 'px'),
                        Util.extractNumericValue(code.maxHeight, 'px')
                      );
                    }
                  } else {
                    if (this.dataUtils.searchElementFromJson(code.codeValuepath, data)) {
                      img.src = CodeUtil.getQrCodeData(this.dataUtils.searchElementFromJson(code.codeValuepath, data));
                    }
                  }
                }
              }
              img.hidden = code.hidden;
              img.style.display = code.display;
              img.style.position = code.position;
              img.style.left = code.x;
              img.style.top = code.y;
              img.style.zIndex = code.z;
              img.style.margin = code.margin;
              img.style.padding = code.padding;
              img.style.border = code.border;
              img.style.verticalAlign = code.verticalAlign;
              img.style.width = code.width;
              img.style.height = code.height;
              img.style.maxWidth = code.maxWidth;
              img.style.maxHeight = code.maxHeight;

              if (img.src.length < 30) {
                console.log('Image DATA < 30 ! : ' + code.name);
                img.style.visibility = 'hidden';
              }

              if (code.groupName == null) {
                parent?.appendChild(img);
              } else {
                Array.prototype.forEach.call(groupDivs, groupDiv => {
                  if (groupDiv.id === code.groupName) {
                    groupDiv?.appendChild(img);
                  }
                });
              }
            }
          }
        } else {
          console.log('NO CODE FOUND');
          resolve(false);
        }
        console.log('END addCodes()');
        resolve(true);
      } catch (error: any) {
        console.error(error.message);
        resolve(false);
      }
    });
  }
}
