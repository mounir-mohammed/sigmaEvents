import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteSig } from '../site-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'sigma-site-sig-detail',
  templateUrl: './site-sig-detail.component.html',
})
export class SiteSigDetailComponent implements OnInit {
  site: ISiteSig | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ site }) => {
      this.site = site;
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
