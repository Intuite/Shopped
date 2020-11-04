import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReportPost, ReportPost } from 'app/shared/model/report-post.model';
import { ReportPostService } from './report-post.service';
import { IReportType } from 'app/shared/model/report-type.model';
import { ReportTypeService } from 'app/entities/report-type/report-type.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IReportType | IPost | IUser;

@Component({
  selector: 'jhi-report-post-update',
  templateUrl: './report-post-update.component.html',
})
export class ReportPostUpdateComponent implements OnInit {
  isSaving = false;
  reporttypes: IReportType[] = [];
  posts: IPost[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    created: [null, [Validators.required]],
    status: [],
    typeId: [null, Validators.required],
    postId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected reportPostService: ReportPostService,
    protected reportTypeService: ReportTypeService,
    protected postService: PostService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportPost }) => {
      if (!reportPost.id) {
        const today = moment().startOf('day');
        reportPost.created = today;
      }

      this.updateForm(reportPost);

      this.reportTypeService.query().subscribe((res: HttpResponse<IReportType[]>) => (this.reporttypes = res.body || []));

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(reportPost: IReportPost): void {
    this.editForm.patchValue({
      id: reportPost.id,
      created: reportPost.created ? reportPost.created.format(DATE_TIME_FORMAT) : null,
      status: reportPost.status,
      typeId: reportPost.typeId,
      postId: reportPost.postId,
      userId: reportPost.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportPost = this.createFromForm();
    if (reportPost.id !== undefined) {
      this.subscribeToSaveResponse(this.reportPostService.update(reportPost));
    } else {
      this.subscribeToSaveResponse(this.reportPostService.create(reportPost));
    }
  }

  private createFromForm(): IReportPost {
    return {
      ...new ReportPost(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportPost>>): void {
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
