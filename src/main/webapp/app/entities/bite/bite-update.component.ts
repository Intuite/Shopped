import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBite, Bite } from 'app/shared/model/bite.model';
import { BiteService } from './bite.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IPost | IUser;

@Component({
  selector: 'jhi-bite-update',
  templateUrl: './bite-update.component.html',
})
export class BiteUpdateComponent implements OnInit {
  isSaving = false;
  posts: IPost[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    created: [null, [Validators.required]],
    status: [],
    postId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected biteService: BiteService,
    protected postService: PostService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bite }) => {
      if (!bite.id) {
        const today = moment().startOf('day');
        bite.created = today;
      }

      this.updateForm(bite);

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(bite: IBite): void {
    this.editForm.patchValue({
      id: bite.id,
      created: bite.created ? bite.created.format(DATE_TIME_FORMAT) : null,
      status: bite.status,
      postId: bite.postId,
      userId: bite.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bite = this.createFromForm();
    if (bite.id !== undefined) {
      this.subscribeToSaveResponse(this.biteService.update(bite));
    } else {
      this.subscribeToSaveResponse(this.biteService.create(bite));
    }
  }

  private createFromForm(): IBite {
    return {
      ...new Bite(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBite>>): void {
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
