import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizSig } from '../organiz-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-organiz-sig-detail',
  templateUrl: './organiz-sig-detail.component.html',
})
export class OrganizSigDetailComponent implements OnInit {
  organiz: IOrganizSig | null = null;
  authority = Authority;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organiz }) => {
      this.organiz = organiz;
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
