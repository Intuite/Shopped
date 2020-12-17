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

import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Log } from 'app/shared/model/log.model';
import { Notification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification/notification.service';
import { LogService } from 'app/entities/log/log.service';

type SelectableEntity = IPost | IUser;

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html',
})
export class CommentUpdateComponent implements OnInit {
  post?: IPost;
  account?: Account | undefined;
  isSaving = false;
  posts: IPost[] = [];
  users: IUser[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE'];

  editForm = this.fb.group({
    id: [],
    content: [null, [Validators.required, Validators.maxLength(200)]],
    created: [],
    status: [],
    postId: [],
    userId: [],
  });

  constructor(
    protected commentService: CommentService,
    protected postService: PostService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    private logService: LogService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.updateForm(comment);
    });
  }

  updateForm(comment: IComment): void {
    this.editForm.patchValue({
      id: comment.id,
      content: comment.content,
      created: comment.created ? comment.created.format(DATE_TIME_FORMAT) : null,
      status: comment.status,
      postId: comment.postId,
      userId: comment.userId,
    });
  }

  previousState(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    return {
      ...new Comment(),
      id: undefined,
      content: this.editForm.get(['content'])!.value,
      created: moment().startOf('minute'),
      status: this.post?.status,
      postId: this.post?.id,
      userId: this.account?.id,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.saveHistoryComment();
    this.addNotificationComment();
    this.activeModal.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  saveHistoryComment(): void {
    const description = JSON.stringify({
      postRecipeName: this.post?.recipeName,
      userComment: this.account?.login,
    });
    this.logService
      .create(new Log(undefined, description, moment().startOf('minute'), 'Post comment', 5, this.account?.login, this.account?.id))
      .subscribe(
        () => console.warn('Comment log succesful'),
        () => console.warn('Comment log failed')
      );
  }

  addNotificationComment(): void {
    const content = JSON.stringify({
      userComment: this.account?.login,
    });
    this.notificationService
      .create(
        new Notification(
          undefined,
          content,
          moment().startOf('minute'),
          this.post?.status,
          'Comment',
          2,
          this.account?.login,
          this.account?.id
        )
      )
      .subscribe(
        () => console.warn('Notification comment succesful'),
        () => console.warn('Notification comment failed')
      );
  }
}
