import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecipeTag, RecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagService } from './recipe-tag.service';
import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from 'app/entities/tag-type/tag-type.service';

@Component({
  selector: 'jhi-recipe-tag-update',
  templateUrl: './recipe-tag-update.component.html',
})
export class RecipeTagUpdateComponent implements OnInit {
  isSaving = false;
  tagtypes: ITagType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    status: [],
    typeId: [null, Validators.required],
  });

  constructor(
    protected recipeTagService: RecipeTagService,
    protected tagTypeService: TagTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeTag }) => {
      this.updateForm(recipeTag);

      this.tagTypeService.query().subscribe((res: HttpResponse<ITagType[]>) => (this.tagtypes = res.body || []));
    });
  }

  updateForm(recipeTag: IRecipeTag): void {
    this.editForm.patchValue({
      id: recipeTag.id,
      name: recipeTag.name,
      description: recipeTag.description,
      status: recipeTag.status,
      typeId: recipeTag.typeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recipeTag = this.createFromForm();
    if (recipeTag.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeTagService.update(recipeTag));
    } else {
      this.subscribeToSaveResponse(this.recipeTagService.create(recipeTag));
    }
  }

  private createFromForm(): IRecipeTag {
    return {
      ...new RecipeTag(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      status: this.editForm.get(['status'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeTag>>): void {
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

  trackById(index: number, item: ITagType): any {
    return item.id;
  }
}
