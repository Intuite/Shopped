import { Component, OnInit } from '@angular/core';
import { Bundle, IBundle } from 'app/shared/model/bundle.model';
import { BundleService } from 'app/entities/bundle/bundle.service';
import { MatDialog } from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';
import { BundlePickerDialogComponent } from 'app/shared/components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';

@Component({
  selector: 'jhi-buy-cookie',
  templateUrl: './buy-cookie.component.html',
  styleUrls: ['./buy-cookie.component.scss'],
})
export class BuyCookieComponent implements OnInit {
  bundles?: Bundle[];

  constructor(protected service: BundleService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.service.query().subscribe(
      (res: HttpResponse<IBundle[]>) => this.onSuccess(res.body),
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
    this.dialog.open(BundlePickerDialogComponent, {
      data: this.bundles,
    });
  }
}
