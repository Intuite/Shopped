import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Bundle } from 'app/shared/model/bundle.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Cookies } from 'app/shared/model/cookies.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';

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
  user?: User;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Bundle[],
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private cookieService: CookiesService,
    private userService: UserService
  ) {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.userService.find(this.account!.login).subscribe(res => (this.user = res || undefined));
    console.warn(this.user?.id);
    this.cookieService
      .query({
        ...(this.user?.id && { 'user_id.equals': this.user?.id }),
      })
      .subscribe(res => (this.cookie = res.body![0] || undefined));

    console.warn(this.account?.login);
    console.warn(this.cookie?.amount);
  }

  selectBundle(s?: Bundle, stepper?: MatStepper): void {
    if (s) {
      this.bundle = s;
    }
  }

  /*getBundle(id: number): void {
    this.service.find(id).subscribe((res: any) => this.bundle = res.body || new Bundle());

  }*/
}
