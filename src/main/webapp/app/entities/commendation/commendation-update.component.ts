import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommendation, Commendation } from 'app/shared/model/commendation.model';
import { CommendationService } from './commendation.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { IAward } from 'app/shared/model/award.model';
import { AwardService } from 'app/entities/award/award.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IPost | IAward | IUser;

@Component({
  selector: 'jhi-commendation-update',
  templateUrl: './commendation-update.component.html',
})
export class CommendationUpdateComponent implements OnInit {
  isSaving = false;
  posts: IPost[] = [];
  awards: IAward[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    postId: [null, Validators.required],
    awardId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected commendationService: CommendationService,
    protected postService: PostService,
    protected awardService: AwardService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commendation }) => {
      if (!commendation.id) {
        const today = moment().startOf('day');
        commendation.date = today;
      }

      this.updateForm(commendation);

      this.postService.query().subscribe((res: HttpResponse<IPost[]>) => (this.posts = res.body || []));

      this.awardService.query().subscribe((res: HttpResponse<IAward[]>) => (this.awards = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(commendation: ICommendation): void {
    this.editForm.patchValue({
      id: commendation.id,
      date: commendation.date ? commendation.date.format(DATE_TIME_FORMAT) : null,
      postId: commendation.postId,
      awardId: commendation.awardId,
      userId: commendation.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commendation = this.createFromForm();
    if (commendation.id !== undefined) {
      this.subscribeToSaveResponse(this.commendationService.update(commendation));
    } else {
      this.subscribeToSaveResponse(this.commendationService.create(commendation));
    }
  }

  private createFromForm(): ICommendation {
    return {
      ...new Commendation(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      postId: this.editForm.get(['postId'])!.value,
      awardId: this.editForm.get(['awardId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommendation>>): void {
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
