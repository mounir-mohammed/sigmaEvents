import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAreaSig } from '../area-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-area-sig-detail',
  templateUrl: './area-sig-detail.component.html',
})
export class AreaSigDetailComponent implements OnInit {
  area: IAreaSig | null = null;
  authority = Authority;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ area }) => {
      this.area = area;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
