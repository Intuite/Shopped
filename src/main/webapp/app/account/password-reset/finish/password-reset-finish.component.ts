import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { PasswordResetFinishService } from './password-reset-finish.service';
import { PasswordStrengthBarComponent } from 'app/account/password/password-strength-bar.component';

@Component({
  selector: 'jhi-password-reset-finish',
  templateUrl: './password-reset-finish.component.html',
})
export class PasswordResetFinishComponent implements OnInit, AfterViewInit {
  @ViewChild('newPassword', { static: false })
  newPassword?: ElementRef;

  initialized = false;
  doNotMatch = false;
  error = false;
  success = false;
  key = '';
  requesting = false;
  unacceptablePassword = false;
  @ViewChild('strengthBarComponent', { static: false }) stBar!: PasswordStrengthBarComponent;

  passwordForm = this.fb.group({
    newPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
  });

  constructor(
    private passwordResetFinishService: PasswordResetFinishService,
    private loginModalService: LoginModalService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['key']) {
        this.key = params['key'];
      }
      this.initialized = true;
    });
  }

  ngAfterViewInit(): void {
    if (this.newPassword) {
      this.newPassword.nativeElement.focus();
    }
  }

  finishReset(): void {
    this.doNotMatch = false;
    this.error = false;

    const newPassword = this.passwordForm.get(['newPassword'])!.value;
    const confirmPassword = this.passwordForm.get(['confirmPassword'])!.value;

    if (newPassword !== confirmPassword) {
      this.doNotMatch = true;
    }

    this.unacceptablePassword = !this.isPasswordAcceptable();

    if (!this.doNotMatch && !this.unacceptablePassword) {
      this.requesting = true;
      this.passwordResetFinishService.save(this.key, newPassword).subscribe(
        () => (this.success = true),
        () => {
          this.error = true;
          this.requesting = false;
        },
        () => (this.requesting = false)
      );
    }
  }

  login(): void {
    this.loginModalService.open();
  }

  isPasswordAcceptable(): boolean {
    return this.stBar.curIdx === 5;
  }
}
