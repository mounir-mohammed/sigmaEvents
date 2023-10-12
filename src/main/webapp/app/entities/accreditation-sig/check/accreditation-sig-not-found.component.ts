import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccreditationSig } from '../accreditation-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-accreditation-sig-not-found',
  templateUrl: './accreditation-sig-not-found.component.html',
})
export class AccreditationSigNotFoundComponent implements OnInit {
  accreditation: IAccreditationSig | null = null;
  authority = Authority;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute, protected accountService: AccountService) {}

  ngOnInit(): void {}

  previousState(): void {
    window.history.back();
  }
}
