import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatalogue, Catalogue } from 'app/shared/model/catalogue.model';
import { CatalogueService } from './catalogue.service';

@Component({
  selector: 'jhi-catalogue-update',
  templateUrl: './catalogue-update.component.html',
})
export class CatalogueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idCatalogue: [null, [Validators.required]],
    value: [null, [Validators.required]],
  });

  constructor(protected catalogueService: CatalogueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogue }) => {
      this.updateForm(catalogue);
    });
  }

  updateForm(catalogue: ICatalogue): void {
    this.editForm.patchValue({
      id: catalogue.id,
      idCatalogue: catalogue.idCatalogue,
      value: catalogue.value,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catalogue = this.createFromForm();
    if (catalogue.id !== undefined) {
      this.subscribeToSaveResponse(this.catalogueService.update(catalogue));
    } else {
      this.subscribeToSaveResponse(this.catalogueService.create(catalogue));
    }
  }

  private createFromForm(): ICatalogue {
    return {
      ...new Catalogue(),
      id: this.editForm.get(['id'])!.value,
      idCatalogue: this.editForm.get(['idCatalogue'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalogue>>): void {
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
