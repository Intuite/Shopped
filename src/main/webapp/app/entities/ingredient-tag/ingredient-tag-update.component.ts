import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIngredientTag, IngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IngredientTagService } from './ingredient-tag.service';
import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from 'app/entities/tag-type/tag-type.service';

@Component({
  selector: 'jhi-ingredient-tag-update',
  templateUrl: './ingredient-tag-update.component.html',
})
export class IngredientTagUpdateComponent implements OnInit {
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
    protected ingredientTagService: IngredientTagService,
    protected tagTypeService: TagTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientTag }) => {
      this.updateForm(ingredientTag);

      this.tagTypeService.query().subscribe((res: HttpResponse<ITagType[]>) => (this.tagtypes = res.body || []));
    });
  }

  updateForm(ingredientTag: IIngredientTag): void {
    this.editForm.patchValue({
      id: ingredientTag.id,
      name: ingredientTag.name,
      description: ingredientTag.description,
      status: ingredientTag.status,
      typeId: ingredientTag.typeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredientTag = this.createFromForm();
    if (ingredientTag.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientTagService.update(ingredientTag));
    } else {
      this.subscribeToSaveResponse(this.ingredientTagService.create(ingredientTag));
    }
  }

  private createFromForm(): IIngredientTag {
    return {
      ...new IngredientTag(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      status: this.editForm.get(['status'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientTag>>): void {
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
