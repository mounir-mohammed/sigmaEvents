import { Injectable } from '@angular/core';
import { FieldType } from 'app/config/fieldType';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';

@Injectable({
  providedIn: 'root',
})
export class BadgeUtils {
  constructor(protected dataUtils: DataUtils) {}
  addImages(parent: any, dataModel: any, data: any): void {
    dataModel.images.forEach((image: any) => {
      var img = document.createElement('img');
      img.id = image.name;
      img.src =
        'data:' +
        this.dataUtils.searchElementFromJson(image.type, data) +
        ';base64,' +
        this.dataUtils.searchElementFromJson(image.path, data);
      img.style.display = image.display;
      img.style.position = image.position;
      img.style.left = image.x;
      img.style.top = image.y;
      img.style.zIndex = image.z;
      img.style.border = image.border;
      img.style.verticalAlign = image.verticalAlign;
      img.style.width = image.width;
      img.style.height = image.height;
      img.style.maxWidth = image.maxWidth;
      img.style.maxHeight = image.maxHeight;
      parent?.appendChild(img);
    });
  }

  addCadres(parent: any, dataModel: any, data: any): void {
    dataModel.cadres.forEach((cadre: any) => {
      var cadreDiv = document.createElement('div');
      cadreDiv.id = cadre.name;
      cadreDiv.style.display = cadre.display;
      cadreDiv.style.position = cadre.position;
      cadreDiv.style.left = cadre.x;
      cadreDiv.style.top = cadre.y;
      cadreDiv.style.zIndex = cadre.z;
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
      parent?.appendChild(cadreDiv);
    });
  }

  createGroups(parent: any, dataModel: any, data: any): Array<any>[] {
    var groupDivs: Array<any> = [];
    dataModel.groups.forEach((group: any) => {
      var groupDiv = document.createElement('div');
      groupDiv.id = group.name;
      groupDiv.style.position = group.position;
      groupDiv.style.left = group.x;
      groupDiv.style.top = group.y;
      groupDiv.style.zIndex = group.z;
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
    });

    return groupDivs;
  }

  addFields(parent: any, dataModel: any, groupDivs: Array<any>, data: any): void {
    dataModel.fields.forEach((element: any) => {
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
        list.forEach((el: any) => {
          var elJsonJson = JSON.stringify(el);
          var elData = JSON.parse(elJsonJson);
          var fieldEl = document.createElement('div');
          fieldEl.id = element.name;
          var text = this.dataUtils.searchElementFromJson(element.path, elData);
          fieldEl.textContent = text;
          fieldEl.style.display = element.display;
          fieldEl.style.position = element.position;
          fieldEl.style.left = element.x;
          fieldEl.style.top = element.y;
          fieldEl.style.zIndex = element.z;
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
        var list = this.dataUtils.searchElementFromJson(element.listPath, data);
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
          fieldEl.textContent = text;
          fieldEl.style.display = element.display;
          fieldEl.style.position = element.position;
          fieldEl.style.left = element.x;
          fieldEl.style.top = element.y;
          fieldEl.style.zIndex = element.z;
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
    });

    Array.prototype.forEach.call(groupDivs, groupDiv => {
      parent?.appendChild(groupDiv);
    });
  }

  generateadge(accreditation?: IAccreditationSig, modelData?: any, badgeContainerId?: string): Promise<Boolean> {
    return new Promise(resolve => {
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
        var groupDivs: Array<any> = this.createGroups(badge, modelData.printingModel, data);

        //add fields
        this.addFields(badge, modelData.printingModel, groupDivs, data);

        // add images
        this.addImages(badge, modelData.printingModel, data);

        //add cadres
        this.addCadres(badge, modelData.printingModel, data);

        badgeContainer?.appendChild(badge);
        return resolve(true);
      } else {
        return resolve(false);
      }
    });
  }

  print(badgeId: string, modelData: any) {
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
  }
}
