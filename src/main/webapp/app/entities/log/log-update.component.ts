import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILog, Log } from 'app/shared/model/log.model';
import { LogService } from './log.service';
import { ILogType } from 'app/shared/model/log-type.model';
import { LogTypeService } from 'app/entities/log-type/log-type.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ILogType | IUser;

@Component({
  selector: 'jhi-log-update',
  templateUrl: './log-update.component.html',
})
export class LogUpdateComponent implements OnInit {
  isSaving = false;
  logtypes: ILogType[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    created: [],
    typeId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected logService: LogService,
    protected logTypeService: LogTypeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ log }) => {
      if (!log.id) {
        const today = moment().startOf('day');
        log.created = today;
      }

      this.updateForm(log);

      this.logTypeService.query().subscribe((res: HttpResponse<ILogType[]>) => (this.logtypes = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(log: ILog): void {
    this.editForm.patchValue({
      id: log.id,
      description: log.description,
      created: log.created ? log.created.format(DATE_TIME_FORMAT) : null,
      typeId: log.typeId,
      userId: log.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const log = this.createFromForm();
    if (log.id !== undefined) {
      this.subscribeToSaveResponse(this.logService.update(log));
    } else {
      this.subscribeToSaveResponse(this.logService.create(log));
    }
  }

  private createFromForm(): ILog {
    return {
      ...new Log(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      typeId: this.editForm.get(['typeId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILog>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
