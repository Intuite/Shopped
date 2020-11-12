import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Bundle } from 'app/shared/model/bundle.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
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
  cookie?: Cookies;

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
    this.cookie = data[0];
  }
  ngOnInit(): void {
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.cookieService
      .query({
        ...(3 && { 'userId.equals': 3 }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.onSuccess(res.body),
        () => console.warn('se cayo')
      );
  }

  selectBundle(s?: Bundle, stepper?: MatStepper): void {
    if (s) {
      this.bundle = s;
    }
  }

  /* getBundle(id: number): void {
    this.service.find(id).subscribe((res: any) => this.bundle = res.body || new Bundle());

  }*/
}
