import { Injectable } from '@angular/core';
import { FieldType } from 'app/config/fieldType';
import { SettingType } from 'app/config/settingType';
import { SourceType } from 'app/config/sourceType';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AreaSigService } from 'app/entities/area-sig/service/area-sig.service';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { SettingSigService } from 'app/entities/setting-sig/service/setting-sig.service';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';

@Injectable({
  providedIn: 'root',
})
export class BadgeUtils {
  constructor(
    protected dataUtils: DataUtils,
    protected settingSigService: SettingSigService,
    protected areaSigService: AreaSigService,
    protected printingModelSigService: PrintingModelSigService
  ) {}

  addImages(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addImages');
    return new Promise(async resolve => {
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
    });
  }

  addCadres(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addCadres');
    return new Promise(async resolve => {
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
    });
  }

  createGroups(parent: any, dataModel: any, data: any): Promise<Array<any>[]> {
    console.log('START createGroups()');
    return new Promise(async resolve => {
      var groupDivs: Array<any> = [];
      if (dataModel.groups) {
        for (let group of dataModel.groups) {
          if (this.addCondition(group, data)) {
            var groupDiv = document.createElement('div');
            groupDiv.id = group.name;
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
            groupDivs.push(groupDiv);
          }
        }
      } else {
        console.log('NO GROUPS FOUND');
      }
      console.log('END createGroups()');
      resolve(groupDivs);
    });
  }

  addFields(parent: any, dataModel: any, groupDivs: Array<any>, data: any): Promise<Boolean> {
    console.log('START addFields()');
    return new Promise(async resolve => {
      if (dataModel.fields) {
        for (let element of dataModel.fields) {
          if (this.addCondition(element, data)) {
            if (element.type == FieldType.TEXT) {
              var field = document.createElement('div');
              field.id = element.name;
              var text = '';
              text = this.dataUtils.searchElementFromJson(element.path, data);
              if (element.toUpperCase) {
                if (text) {
                  text = text.toString().toUpperCase().trim();
                }
              }
              if (element.code) {
                text = '';
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
              field.style.fontSize = element.fontSize;
              field.style.fontWeight = element.fontWeight;
              field.style.border = element.border;
              field.style.whiteSpace = element.whiteSpace;
              field.style.verticalAlign = element.verticalAlign;
              field.style.width = element.width;
              field.style.height = element.height;
              field.style.maxWidth = element.maxWidth;
              field.style.maxHeight = element.maxHeight;
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

              if (element.code) {
                text = '';
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
              field.style.fontSize = element.fontSize;
              field.style.fontWeight = element.fontWeight;
              field.style.border = element.border;
              field.style.whiteSpace = element.whiteSpace;
              field.style.verticalAlign = element.verticalAlign;
              field.style.width = element.width;
              field.style.height = element.height;
              field.style.maxWidth = element.maxWidth;
              field.style.maxHeight = element.maxHeight;
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
                fieldEl.style.fontSize = element.fontSize;
                fieldEl.style.fontWeight = element.fontWeight;
                fieldEl.style.border = element.border;
                fieldEl.style.whiteSpace = element.whiteSpace;
                fieldEl.style.verticalAlign = element.verticalAlign;
                fieldEl.style.width = element.width;
                fieldEl.style.height = element.height;
                fieldEl.style.maxWidth = element.maxWidth;
                fieldEl.style.maxHeight = element.maxHeight;
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
                      fieldEl.style.fontSize = element.fontSize;
                      fieldEl.style.fontWeight = element.fontWeight;
                      fieldEl.style.border = element.border;
                      fieldEl.style.whiteSpace = element.whiteSpace;
                      fieldEl.style.verticalAlign = element.verticalAlign;
                      fieldEl.style.width = element.width;
                      fieldEl.style.height = element.height;
                      fieldEl.style.maxWidth = element.maxWidth;
                      fieldEl.style.maxHeight = element.maxHeight;
                      var text = this.dataUtils.searchElementFromJson(element.path, elData);
                      if (element.toUpperCase) {
                        text = text.toString().toUpperCase().trim();
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
                  fieldEl.style.fontSize = element.fontSize;
                  fieldEl.style.fontWeight = element.fontWeight;
                  fieldEl.style.border = element.border;
                  fieldEl.style.whiteSpace = element.whiteSpace;
                  fieldEl.style.verticalAlign = element.verticalAlign;
                  fieldEl.style.width = element.width;
                  fieldEl.style.height = element.height;
                  fieldEl.style.maxWidth = element.maxWidth;
                  fieldEl.style.maxHeight = element.maxHeight;

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
    });
  }

  generateBadge(accreditation?: IAccreditationSig, modelData?: any, badgeContainerId?: string): Promise<Boolean> {
    console.log('START generateadge()');
    return new Promise(resolve => {
      setTimeout(() => {
        if (accreditation) {
          var accreditationJson = JSON.stringify(accreditation);
          var data = JSON.parse(accreditationJson);
          var badgeContainer = document.getElementById(badgeContainerId!);
          //generate badge
          var badge = document.createElement('div');
          var badgeId = accreditation?.event?.eventAbreviation + '_' + accreditation?.event?.eventId + '_' + accreditation?.accreditationId;
          badge.id = badgeId;
          badge.style.width = modelData.printingModel.page.width;
          badge.style.height = modelData.printingModel.page.height;
          badge.style.margin = modelData.printingModel.page.margin;
          badge.style.border = modelData.printingModel.page.border;
          badge.style.position = modelData.printingModel.page.position;

          //add groups
          this.createGroups(badge, modelData.printingModel, data).then(groupDivs => {
            //add fields
            this.addFields(badge, modelData.printingModel, groupDivs, data).then(() => {
              // add images
              this.addImages(badge, modelData.printingModel, groupDivs, data).then(() => {
                //add cadres
                this.addCadres(badge, modelData.printingModel, groupDivs, data).then(() => {
                  badgeContainer?.appendChild(badge);
                  this.deplaceGroupToParent(modelData.printingModel).then(() => {
                    console.log('END generateadge()');
                    return resolve(true);
                  });
                });
              });
            });
          });
        } else {
          return resolve(false);
        }
      }, 500);
    });
  }

  print(badgeId: string, modelData: any) {
    console.log('START print()');
    let data = document.getElementById(badgeId);
    if (data && modelData) {
      html2canvas(data, { scale: modelData.printingModel.model.scale }).then(canvas => {
        const contentDataURL = canvas.toDataURL(modelData.printingModel.model.type, modelData.printingModel.model.quality);
        let pdf = new jspdf(
          modelData.printingModel.model.landScape,
          modelData.printingModel.model.unite,
          modelData.printingModel.model.format
        );
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
        if (modelData.printingModel.model.autoPrint == true) {
          pdf.output('dataurlnewwindow', { filename: badgeId });
        } else {
          let fileName = badgeId + '_' + new Date().toUTCString();
          pdf.save(fileName);
        }
      });
    }
    console.log('END print()');
  }

  addCondition(element: any, data: any): boolean {
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
  }

  deplaceGroupToParent(dataModel: any): Promise<Boolean> {
    console.log('START deplaceGroupToParent()');
    return new Promise(resolve => {
      if (dataModel.groups) {
        dataModel.groups.forEach((group: any) => {
          if (group.groupName) {
            var childGroupe = document.getElementById(group.name);
            var groupParent = document.getElementById(group.groupName);
            groupParent?.appendChild(childGroupe!);
          }
        });
        resolve(true);
      } else {
        console.log('NO GROUPS FOUND');
        resolve(false);
      }
      console.log('END deplaceGroupToParent()');
    });
  }

  getConfig(modelId: number): Promise<any> {
    console.log('START getConfig()');
    var printingModel: IPrintingModelSig | null = null;
    return new Promise(resolve => {
      this.printingModelSigService.find(modelId).subscribe(resp => {
        printingModel = resp.body;
        if (printingModel?.printingModelStat) {
          var modelData = this.dataUtils.base64ToJson(printingModel?.printingModelData!);
          if (modelData) {
            resolve(modelData);
          }
        } else {
          console.log('getConfig() => PRINTING MODEL STATE NOT ACTIVATED');
          resolve(false);
        }
      });
      console.log('END getConfig()');
    });
  }
}
