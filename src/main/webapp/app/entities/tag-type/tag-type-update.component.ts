import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITagType, TagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-tag-type-update',
  templateUrl: './tag-type-update.component.html',
})
export class TagTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    status: [],
  });

  constructor(
    protected tagTypeService: TagTypeService,
    private alertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

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
    this.tagTypeService
      .query({
        ...(tagType.name && { 'name.equals': tagType.name }),
      })
      .subscribe((res: HttpResponse<ITagType[]>) => {
        const valid = this.validateForm(tagType, res.body || []);
        if (valid) {
          if (tagType.id !== undefined) {
            this.subscribeToSaveResponse(this.tagTypeService.update(tagType));
          } else {
            tagType.status = Status.ACTIVE.toUpperCase() as Status;
            this.subscribeToSaveResponse(this.tagTypeService.create(tagType));
          }
        } else {
          this.alertService.addAlert({ toast: false, type: 'danger', msg: 'shoppedApp.validation.forms.nameUnique' }, []);
        }
        this.isSaving = false;
      });
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

  private validateForm(tagType: ITagType, tagTypeList: ITagType[]): boolean {
    const rt = tagTypeList[0];
    if (tagTypeList.length === 0) {
      // no existen tags con el mismo nombre
      return true;
    } else if (tagType.id !== undefined && tagTypeList.length < 2) {
      if (tagType.id !== rt.id) {
        // si no es el mismo tag
        return false; // equal name
      }
      return true; // can update
    }
    return false;
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
}
