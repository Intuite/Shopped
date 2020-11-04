import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecipeShared, RecipeShared } from 'app/shared/model/recipe-shared.model';
import { RecipeSharedService } from './recipe-shared.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IRecipe | IUser;

@Component({
  selector: 'jhi-recipe-shared-update',
  templateUrl: './recipe-shared-update.component.html',
})
export class RecipeSharedUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    recipeId: [null, Validators.required],
    userId: [null, Validators.required],
  });

  constructor(
    protected recipeSharedService: RecipeSharedService,
    protected recipeService: RecipeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeShared }) => {
      this.updateForm(recipeShared);

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(recipeShared: IRecipeShared): void {
    this.editForm.patchValue({
      id: recipeShared.id,
      status: recipeShared.status,
      recipeId: recipeShared.recipeId,
      userId: recipeShared.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recipeShared = this.createFromForm();
    if (recipeShared.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeSharedService.update(recipeShared));
    } else {
      this.subscribeToSaveResponse(this.recipeSharedService.create(recipeShared));
    }
  }

  private createFromForm(): IRecipeShared {
    return {
      ...new RecipeShared(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeShared>>): void {
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
