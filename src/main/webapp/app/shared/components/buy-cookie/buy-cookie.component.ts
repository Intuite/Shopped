import { Component, Inject, OnInit } from '@angular/core';
import { Bundle } from 'app/shared/model/bundle.model';
import { Subscription } from 'rxjs';
import { BundleService } from 'app/entities/bundle/bundle.service';
import { MatDialogContent, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-buy-cookie',
  templateUrl: './buy-cookie.component.html',
  styleUrls: ['./buy-cookie.component.scss'],
})
export class BuyCookieComponent implements OnInit {
  bundles?: Bundle[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(protected service: BundleService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.service.query().subscribe(
      (res: HttpResponse<Bundle>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  protected onSuccess(data: Bundle[] | null): void {
    this.bundles = data || [];
  }

  protected onError(): void {
    console.warn('There was an error');
  }

  open(): any {
    this.dialog.open(BundlePickerDialog, {
      data: this.bundles,
    });
  }
}
@Component({
  selector: 'bundle-picker-dialog',
  templateUrl: './bundle-picker-dialog.html',
})
export class BundlePickerDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Bundle[]) {}
}
