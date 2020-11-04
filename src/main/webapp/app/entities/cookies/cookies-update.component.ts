import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICookies, Cookies } from 'app/shared/model/cookies.model';
import { CookiesService } from './cookies.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cookies-update',
  templateUrl: './cookies-update.component.html',
})
export class CookiesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.min(0)]],
    walletKey: [],
    userId: [null, Validators.required],
  });

  constructor(
    protected cookiesService: CookiesService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cookies }) => {
      this.updateForm(cookies);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(cookies: ICookies): void {
    this.editForm.patchValue({
      id: cookies.id,
      amount: cookies.amount,
      walletKey: cookies.walletKey,
      userId: cookies.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cookies = this.createFromForm();
    if (cookies.id !== undefined) {
      this.subscribeToSaveResponse(this.cookiesService.update(cookies));
    } else {
      this.subscribeToSaveResponse(this.cookiesService.create(cookies));
    }
  }

  private createFromForm(): ICookies {
    return {
      ...new Cookies(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      walletKey: this.editForm.get(['walletKey'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICookies>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
