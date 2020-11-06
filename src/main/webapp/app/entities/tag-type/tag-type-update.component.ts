import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITagType, TagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';

@Component({
  selector: 'jhi-tag-type-update',
  templateUrl: './tag-type-update.component.html',
})
export class TagTypeUpdateComponent implements OnInit {
  isSaving = false;
  statusOptions = ['ACTIVE','INACTIVE','BLOCKED','PENDING'];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    status: [],
  });

  constructor(protected tagTypeService: TagTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagType }) => {
      this.updateForm(tagType);
    });
  }

  updateForm(tagType: ITagType): void {
    this.editForm.patchValue({
      id: tagType.id,
      name: tagType.name,
      description: tagType.description,
      status: tagType.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tagType = this.createFromForm();
    if (tagType.id !== undefined) {
      this.subscribeToSaveResponse(this.tagTypeService.update(tagType));
    } else {
      this.subscribeToSaveResponse(this.tagTypeService.create(tagType));
    }
  }

  private createFromForm(): ITagType {
    return {
      ...new TagType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITagType>>): void {
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
}
