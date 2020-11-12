import { Component, AfterViewInit, ElementRef, ViewChild, Inject, PLATFORM_ID } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { PasswordResetInitService } from './password-reset-init.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html',
})
export class PasswordResetInitComponent implements AfterViewInit {
  @ViewChild('email', { static: false })
  email?: ElementRef;

  success = false;
  resetRequestForm = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
  });

  constructor(
    private passwordResetInitService: PasswordResetInitService,
    private fb: FormBuilder,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngAfterViewInit(): void {
    this.setFocusMail();
  }

  setFocusMail(): void {
    if (isPlatformBrowser(this.platformId) && this.email) {
      this.email.nativeElement.focus();
    }
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.get(['email'])!.value).subscribe(() => (this.success = true));
  }
}
