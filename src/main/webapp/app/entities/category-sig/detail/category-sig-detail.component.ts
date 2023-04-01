import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorySig } from '../category-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'sigma-category-sig-detail',
  templateUrl: './category-sig-detail.component.html',
})
export class CategorySigDetailComponent implements OnInit {
  category: ICategorySig | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.category = category;
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
