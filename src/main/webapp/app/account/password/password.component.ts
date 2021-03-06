import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { PasswordService } from './password.service';
import { PasswordStrengthBarComponent } from 'app/account/password/password-strength-bar.component';

@Component({
  selector: 'jhi-password',
  templateUrl: './password.component.html',
})
export class PasswordComponent implements OnInit {
  doNotMatch = false;
  unacceptablePassword = false;
  error = false;
  success = false;
  requesting = false;
  account$?: Observable<Account | null>;
  passwordForm = this.fb.group({
    currentPassword: ['', [Validators.required]],
    newPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
  });
  @ViewChild('strengthBarComponent', { static: false }) stBar!: PasswordStrengthBarComponent;

  constructor(private passwordService: PasswordService, private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
  }

  changePassword(): void {
    this.error = false;
    this.success = false;
    this.doNotMatch = false;

    const newPassword = this.passwordForm.get(['newPassword'])!.value;
    if (newPassword !== this.passwordForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    }

    this.unacceptablePassword = !this.isPasswordAcceptable();

    if (!this.doNotMatch && !this.unacceptablePassword) {
      this.requesting = true;
      this.passwordService.save(newPassword, this.passwordForm.get(['currentPassword'])!.value).subscribe(
        () => {
          this.success = true;
        },
        () => {
          this.error = true;
          this.requesting = false;
        },
        () => {
          this.requesting = false;
        }
      );
    }
  }

  isPasswordAcceptable(): boolean {
    return this.stBar.curIdx === 5;
  }

  previousState(): void {
    window.history.back();
  }
}
