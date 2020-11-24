import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { LoginModalComponent } from 'app/shared/login/login.component';

@Injectable({ providedIn: 'root' })
export class LoginModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {}

  open(finalizeFunc?: Function): void {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef: NgbModalRef = this.modalService.open(LoginModalComponent, { centered: true });
    modalRef.result.finally(() => {
      this.isOpen = false;
      if (finalizeFunc) finalizeFunc();
    });
  }
}
