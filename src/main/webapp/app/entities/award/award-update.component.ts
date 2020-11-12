import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent, JhiAlertService } from 'ng-jhipster';

import { IAward, Award } from 'app/shared/model/award.model';
import { AwardService } from './award.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-award-update',
  templateUrl: './award-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class AwardUpdateComponent implements OnInit {
  isSaving = false;
  statusOptions = ['ACTIVE', 'INACTIVE'];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    cost: [null, [Validators.required, Validators.min(0)]],
    image: [],
    imageContentType: [],
    status: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected awardService: AwardService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private alertService: JhiAlertService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ award }) => {
      this.updateForm(award);
    });
  }

  updateForm(award: IAward): void {
    this.editForm.patchValue({
      id: award.id,
      name: award.name,
      description: award.description,
      cost: award.cost,
      image: award.image,
      imageContentType: award.imageContentType,
      status: award.status,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('shoppedApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    const award = this.createFromForm();
    if (award.image === null) {
      console.warn('message empty');
    } else {
      console.warn(award.imageContentType);
      // TODO not working!!! default image
      this.editForm.patchValue({
        image:
          'src="data:image/png;base64,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"',
        imageContentType: 'image/png',
      });
      this.setFileData(new Event('change'), 'image', true);
    }
    this.awardService
      .query({
        ...(award.name && { 'name.equals': award.name }),
      })
      .subscribe((res: HttpResponse<IAward[]>) => this.saveValidated((res.body || []).length, award, (res.body || [])[0]));
  }

  private saveValidated(val: number, award: any, first: Award): void {
    if (award.id !== undefined && val <= 1) {
      if (first.id !== award.id) {
        this.alertService.addAlert({ toast: false, type: 'warning', msg: 'shoppedApp.validation.forms.nameUnique' }, []);
      } else {
        this.subscribeToSaveResponse(this.awardService.update(award));
        this.isSaving = true;
      }
    } else if (val === 0) {
      this.subscribeToSaveResponse(this.awardService.create(award));
      this.isSaving = true;
    } else {
      this.alertService.addAlert({ toast: false, type: 'warning', msg: 'Name already in use' }, []);
    }
  }

  private createFromForm(): IAward {
    return {
      ...new Award(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      cost: this.editForm.get(['cost'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAward>>): void {
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
