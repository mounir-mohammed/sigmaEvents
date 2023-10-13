import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccreditationSig } from '../accreditation-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-accreditation-sig-check',
  templateUrl: './accreditation-sig-check.component.html',
})
export class AccreditationSigCheckComponent implements OnInit {
  accreditation: IAccreditationSig | null = null;
  authority = Authority;
  verifing: boolean = true;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute, protected accountService: AccountService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditation }) => {
      this.accreditation = accreditation;
      this.verifing = false;
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
