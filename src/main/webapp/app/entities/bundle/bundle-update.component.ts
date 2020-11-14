import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBundle, Bundle } from 'app/shared/model/bundle.model';
import { BundleService } from './bundle.service';

@Component({
  selector: 'jhi-bundle-update',
  templateUrl: './bundle-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class BundleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    cost: [null, [Validators.required, Validators.min(0)]],
    cookieAmount: [null, [Validators.required, Validators.min(0)]],
  });

  constructor(protected bundleService: BundleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bundle }) => {
      this.updateForm(bundle);
    });
  }

  updateForm(bundle: IBundle): void {
    this.editForm.patchValue({
      id: bundle.id,
      name: bundle.name,
      cost: bundle.cost,
      cookieAmount: bundle.cookieAmount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bundle = this.createFromForm();
    if (bundle.id !== undefined) {
      this.subscribeToSaveResponse(this.bundleService.update(bundle));
    } else {
      this.subscribeToSaveResponse(this.bundleService.create(bundle));
    }
  }

  private createFromForm(): IBundle {
    return {
      ...new Bundle(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      cost: this.editForm.get(['cost'])!.value,
      cookieAmount: this.editForm.get(['cookieAmount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBundle>>): void {
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
