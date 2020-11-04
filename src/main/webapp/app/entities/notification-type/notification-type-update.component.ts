import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotificationType, NotificationType } from 'app/shared/model/notification-type.model';
import { NotificationTypeService } from './notification-type.service';

@Component({
  selector: 'jhi-notification-type-update',
  templateUrl: './notification-type-update.component.html',
})
export class NotificationTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    status: [],
  });

  constructor(
    protected notificationTypeService: NotificationTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationType }) => {
      this.updateForm(notificationType);
    });
  }

  updateForm(notificationType: INotificationType): void {
    this.editForm.patchValue({
      id: notificationType.id,
      name: notificationType.name,
      status: notificationType.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificationType = this.createFromForm();
    if (notificationType.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationTypeService.update(notificationType));
    } else {
      this.subscribeToSaveResponse(this.notificationTypeService.create(notificationType));
    }
  }

  private createFromForm(): INotificationType {
    return {
      ...new NotificationType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificationType>>): void {
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
}
