import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttachementSig } from '../attachement-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-attachement-sig-detail',
  templateUrl: './attachement-sig-detail.component.html',
})
export class AttachementSigDetailComponent implements OnInit {
  attachement: IAttachementSig | null = null;
  authority = Authority;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachement }) => {
      this.attachement = attachement;
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
