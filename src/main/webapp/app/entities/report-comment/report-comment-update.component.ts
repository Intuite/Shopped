import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReportComment, ReportComment } from 'app/shared/model/report-comment.model';
import { ReportCommentService } from './report-comment.service';
import { IReportType } from 'app/shared/model/report-type.model';
import { ReportTypeService } from 'app/entities/report-type/report-type.service';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment/comment.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IReportType | IComment | IUser;

@Component({
  selector: 'jhi-report-comment-update',
  templateUrl: './report-comment-update.component.html',
})
export class ReportCommentUpdateComponent implements OnInit {
  isSaving = false;
  reporttypes: IReportType[] = [];
  comments: IComment[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    created: [null, [Validators.required]],
    status: [],
    typeId: [null, Validators.required],
    commentId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected reportCommentService: ReportCommentService,
    protected reportTypeService: ReportTypeService,
    protected commentService: CommentService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportComment }) => {
      if (!reportComment.id) {
        const today = moment().startOf('day');
        reportComment.created = today;
      }

      this.updateForm(reportComment);

      this.reportTypeService.query().subscribe((res: HttpResponse<IReportType[]>) => (this.reporttypes = res.body || []));

      this.commentService.query().subscribe((res: HttpResponse<IComment[]>) => (this.comments = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(reportComment: IReportComment): void {
    this.editForm.patchValue({
      id: reportComment.id,
      created: reportComment.created ? reportComment.created.format(DATE_TIME_FORMAT) : null,
      status: reportComment.status,
      typeId: reportComment.typeId,
      commentId: reportComment.commentId,
      userId: reportComment.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportComment = this.createFromForm();
    if (reportComment.id !== undefined) {
      this.subscribeToSaveResponse(this.reportCommentService.update(reportComment));
    } else {
      this.subscribeToSaveResponse(this.reportCommentService.create(reportComment));
    }
  }

  private createFromForm(): IReportComment {
    return {
      ...new ReportComment(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      commentId: this.editForm.get(['commentId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportComment>>): void {
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
