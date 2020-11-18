import { Component, Inject, OnInit } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'jhi-ingredient-detail',
  templateUrl: './ingredient-detail.component.html',
})
export class IngredientDetailComponent implements OnInit {
  constructor(protected dataUtils: JhiDataUtils, @Inject(MAT_DIALOG_DATA) public element: any) {}

  ngOnInit(): void {}

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
