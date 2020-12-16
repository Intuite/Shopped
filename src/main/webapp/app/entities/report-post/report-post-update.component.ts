import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
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
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Log } from 'app/shared/model/log.model';
import { LogService } from 'app/entities/log/log.service';

type SelectableEntity = IReportType | IPost | IUser;

@Component({
  selector: 'jhi-report-post-update',
  templateUrl: './report-post-update.component.html',
})
export class ReportPostUpdateComponent implements OnInit {
  post?: IPost;
  account?: Account | undefined;
  reporttypes: IReportType[] = [];
  isSaving = false;
  posts: IPost[] = [];
  users: IUser[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE'];

  editForm = this.fb.group({
    id: [],
    created: [],
    status: [],
    typeId: [null, Validators.required],
    postId: [],
    userId: [],
  });

  constructor(
    protected reportPostService: ReportPostService,
    protected reportTypeService: ReportTypeService,
    protected postService: PostService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportPost }) => {
      if (!reportPost.id) {
        const today = moment().startOf('minute');
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
    this.activeModal.dismiss();
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
      id: undefined,
      created: moment().startOf('minute'),
      status: this.post?.status,
      typeId: this.editForm.get(['typeId'])!.value,
      postId: this.post?.id,
      userId: this.account?.id,
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
    this.saveHistoryReportPost();
    this.activeModal.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  saveHistoryReportPost(): void {
    const description = JSON.stringify({
      postRecipeName: this.post?.recipeName,
      userReporting: this.account?.login,
    });
    this.logService
      .create(new Log(undefined, description, moment().startOf('minute'), 'Report post', 8, this.account?.login, this.account?.id))
      .subscribe(
        () => console.warn('Report post log succesful'),
        () => console.warn('Report post log failed')
      );
  }
}
