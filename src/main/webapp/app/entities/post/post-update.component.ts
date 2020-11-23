import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPost, Post } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';

type SelectableEntity = IRecipe | IUser;

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.component.html',
})
export class PostUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE'];
  user!: IUser;

  // continue with the user

  editForm = this.fb.group({
    id: [],
    caption: [],
    date: [null, [Validators.required]],
    status: [],
    recipeId: [null, Validators.required],
    userId: [],
  });

  constructor(
    protected postService: PostService,
    protected recipeService: RecipeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => {
      if (!post.id) {
        const today = moment().startOf('minute');
        post.date = today;
      }

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account) {
          this.userService.find(account.login).subscribe(user => (this.user = user));
        }
      });

      this.updateForm(post);

      this.recipeService
        .query({ 'postId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IRecipe[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRecipe[]) => {
          if (!post.recipeId) {
            this.recipes = resBody;
          } else {
            this.recipeService
              .find(post.recipeId)
              .pipe(
                map((subRes: HttpResponse<IRecipe>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRecipe[]) => (this.recipes = concatRes));
          }
          this.cleanRecipes();
        });
    });
  }

  cleanRecipes(): void {
    let i = 0;
    while (i < this.recipes.length) {
      if (this.recipes[i].userId !== this.user.id || this.recipes[i].status === this.statusOptions[1]) {
        this.recipes.splice(i, 1);
      } else {
        i++;
      }
    }
  }

  updateForm(post: IPost): void {
    this.editForm.patchValue({
      id: post.id,
      caption: post.caption,
      date: post.date ? post.date.format(DATE_TIME_FORMAT) : null,
      status: post.status,
      recipeId: post.recipeId,
      userId: post.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const post = this.createFromForm();
    if (post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(post));
    }
  }

  private createFromForm(): IPost {
    return {
      ...new Post(),
      id: this.editForm.get(['id'])!.value,
      caption: this.editForm.get(['caption'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
      userId: this.user.id,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>): void {
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
