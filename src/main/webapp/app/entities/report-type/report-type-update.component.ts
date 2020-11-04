import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReportType, ReportType } from 'app/shared/model/report-type.model';
import { ReportTypeService } from './report-type.service';

@Component({
  selector: 'jhi-report-type-update',
  templateUrl: './report-type-update.component.html',
})
export class ReportTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    text: [null, [Validators.required]],
    status: [],
  });

  constructor(protected reportTypeService: ReportTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportType }) => {
      this.updateForm(reportType);
    });
  }

  updateForm(reportType: IReportType): void {
    this.editForm.patchValue({
      id: reportType.id,
      name: reportType.name,
      text: reportType.text,
      status: reportType.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportType = this.createFromForm();
    if (reportType.id !== undefined) {
      this.subscribeToSaveResponse(this.reportTypeService.update(reportType));
    } else {
      this.subscribeToSaveResponse(this.reportTypeService.create(reportType));
    }
  }

  private createFromForm(): IReportType {
    return {
      ...new ReportType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      text: this.editForm.get(['text'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportType>>): void {
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
