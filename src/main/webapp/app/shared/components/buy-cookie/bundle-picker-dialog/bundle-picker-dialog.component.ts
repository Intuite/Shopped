import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Bundle } from 'app/shared/model/bundle.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-bundle-picker-dialog',
  templateUrl: './bundle-picker-dialog.component.html',
  styleUrls: ['./bundle-picker-dialog.component.scss'],
})
export class BundlePickerDialogComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  bundleId = 0;
  bundle = new Bundle();
  account?: Account;
  cookie = new Cookies();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Bundle[],
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private cookieService: CookiesService
  ) {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required],
    });
  }

  onSuccess(data: any): void {
    if (data !== null && data !== undefined) {
      this.cookie = data[0];
    }
  }
  ngOnInit(): void {
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.cookieService
      .query({
        ...(this.account?.id && { 'userId.equals': this.account?.id }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.onSuccess(res.body || undefined),
        () => console.warn('No wallet found for user: ' + this.account?.login)
      );
  }

  selectBundle(s?: Bundle): void {
    if (s) {
      this.bundle = s;
      this.bundleId = this.bundle.id || 0;
    }
  }

  payed(): void {
    console.warn('pay');
  }
}
