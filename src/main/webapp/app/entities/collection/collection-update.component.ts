import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';

import { Collection, ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from './collection.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/user/account.model';
import { Status } from 'app/shared/model/enumerations/status.model';

@Component({
  selector: 'jhi-collection-update',
  templateUrl: './collection-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class CollectionUpdateComponent implements OnInit {
  isSaving = false;
  currentAccount?: Account;
  collection?: ICollection;
  errorData = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    image: [],
    imageContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected collectionService: CollectionService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.currentAccount) this.updateForm(this.collection);
    else {
      this.errorData = true;
    }
  }

  updateForm(collection?: ICollection): void {
    if (this.collection) {
      this.editForm.patchValue({
        id: collection?.id,
        name: collection?.name,
        description: collection?.description,
        image: collection?.image,
        imageContentType: collection?.imageContentType,
      });
    }
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

  // previousState(): void {
  //   window.history.back();
  // }

  close(): void {
    this.activeModal.close();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const collection = this.createFromForm();
    if (collection.id !== undefined && collection.id !== null) {
      this.subscribeToSaveResponse(this.collectionService.update(collection));
    } else {
      this.subscribeToSaveResponse(this.collectionService.create(collection));
    }
  }

  private createFromForm(): ICollection {
    return {
      ...new Collection(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      created: moment(),
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      status: 'ACTIVE' as Status,
      userId: this.currentAccount?.id,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollection>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.eventManager.broadcast('collectionListModification');
    this.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  byteSizeNumber(base64String: string): number {
    const st = this.dataUtils.byteSize(base64String).replace(/[^0-9]/g, '');
    return parseInt(st, 10);
  }
}
