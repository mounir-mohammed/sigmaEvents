import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttachementTypeSig } from '../attachement-type-sig.model';

@Component({
  selector: 'sigma-attachement-type-sig-detail',
  templateUrl: './attachement-type-sig-detail.component.html',
})
export class AttachementTypeSigDetailComponent implements OnInit {
  attachementType: IAttachementTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachementType }) => {
      this.attachementType = attachementType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
