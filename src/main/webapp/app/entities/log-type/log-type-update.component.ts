import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILogType, LogType } from 'app/shared/model/log-type.model';
import { LogTypeService } from './log-type.service';

@Component({
  selector: 'jhi-log-type-update',
  templateUrl: './log-type-update.component.html',
})
export class LogTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    template: [],
    status: [],
  });

  constructor(protected logTypeService: LogTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logType }) => {
      this.updateForm(logType);
    });
  }

  updateForm(logType: ILogType): void {
    this.editForm.patchValue({
      id: logType.id,
      name: logType.name,
      template: logType.template,
      status: logType.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logType = this.createFromForm();
    if (logType.id !== undefined) {
      this.subscribeToSaveResponse(this.logTypeService.update(logType));
    } else {
      this.subscribeToSaveResponse(this.logTypeService.create(logType));
    }
  }

  private createFromForm(): ILogType {
    return {
      ...new LogType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      template: this.editForm.get(['template'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogType>>): void {
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
