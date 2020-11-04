import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFollower, Follower } from 'app/shared/model/follower.model';
import { FollowerService } from './follower.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-follower-update',
  templateUrl: './follower-update.component.html',
})
export class FollowerUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    created: [null, [Validators.required]],
    status: [],
    userFollowedId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected followerService: FollowerService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ follower }) => {
      if (!follower.id) {
        const today = moment().startOf('day');
        follower.created = today;
      }

      this.updateForm(follower);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(follower: IFollower): void {
    this.editForm.patchValue({
      id: follower.id,
      created: follower.created ? follower.created.format(DATE_TIME_FORMAT) : null,
      status: follower.status,
      userFollowedId: follower.userFollowedId,
      userId: follower.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const follower = this.createFromForm();
    if (follower.id !== undefined) {
      this.subscribeToSaveResponse(this.followerService.update(follower));
    } else {
      this.subscribeToSaveResponse(this.followerService.create(follower));
    }
  }

  private createFromForm(): IFollower {
    return {
      ...new Follower(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      userFollowedId: this.editForm.get(['userFollowedId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFollower>>): void {
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
