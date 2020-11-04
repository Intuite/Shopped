import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIngredientHasIngredientTag, IngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';
import { IngredientHasIngredientTagService } from './ingredient-has-ingredient-tag.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IngredientTagService } from 'app/entities/ingredient-tag/ingredient-tag.service';

type SelectableEntity = IIngredient | IIngredientTag;

@Component({
  selector: 'jhi-ingredient-has-ingredient-tag-update',
  templateUrl: './ingredient-has-ingredient-tag-update.component.html',
})
export class IngredientHasIngredientTagUpdateComponent implements OnInit {
  isSaving = false;
  ingredients: IIngredient[] = [];
  ingredienttags: IIngredientTag[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    ingredientId: [null, Validators.required],
    ingredientTagId: [null, Validators.required],
  });

  constructor(
    protected ingredientHasIngredientTagService: IngredientHasIngredientTagService,
    protected ingredientService: IngredientService,
    protected ingredientTagService: IngredientTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientHasIngredientTag }) => {
      this.updateForm(ingredientHasIngredientTag);

      this.ingredientService.query().subscribe((res: HttpResponse<IIngredient[]>) => (this.ingredients = res.body || []));

      this.ingredientTagService.query().subscribe((res: HttpResponse<IIngredientTag[]>) => (this.ingredienttags = res.body || []));
    });
  }

  updateForm(ingredientHasIngredientTag: IIngredientHasIngredientTag): void {
    this.editForm.patchValue({
      id: ingredientHasIngredientTag.id,
      status: ingredientHasIngredientTag.status,
      ingredientId: ingredientHasIngredientTag.ingredientId,
      ingredientTagId: ingredientHasIngredientTag.ingredientTagId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredientHasIngredientTag = this.createFromForm();
    if (ingredientHasIngredientTag.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientHasIngredientTagService.update(ingredientHasIngredientTag));
    } else {
      this.subscribeToSaveResponse(this.ingredientHasIngredientTagService.create(ingredientHasIngredientTag));
    }
  }

  private createFromForm(): IIngredientHasIngredientTag {
    return {
      ...new IngredientHasIngredientTag(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      ingredientId: this.editForm.get(['ingredientId'])!.value,
      ingredientTagId: this.editForm.get(['ingredientTagId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientHasIngredientTag>>): void {
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
