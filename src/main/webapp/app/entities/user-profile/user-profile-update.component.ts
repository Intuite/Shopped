import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import * as moment from 'moment';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class UserProfileUpdateComponent implements OnInit {
  isSavingUP = false;
  isSavingU = false;
  successUP = false;
  successU = false;
  currentUserProfile!: IUserProfile;
  currentAccount!: Account;
  editableStatus = false;
  minDate = new Date(1915, 0, 1);
  maxDate = new Date(2010, 0, 1);
  startDate = new Date(1990, 0, 1);
  // users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    description: ['', [Validators.maxLength(254)]],
    birthDate: [],
    image: [],
    imageContentType: [],
    status: [],
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    // userId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userProfileService: UserProfileService,
    protected accountService: AccountService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.currentUserProfile = userProfile;
      this.updateForm(this.currentUserProfile);

      // this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userProfile: IUserProfile): void {
    this.editForm.patchValue({
      id: userProfile.id,
      description: userProfile.description,
      image: userProfile.image,
      imageContentType: userProfile.imageContentType,
      status: userProfile.status,
      userId: userProfile.userId,
    });
    if (!this.editableStatus)
      this.editForm.patchValue({
        status: this.getStatusCapitalized(userProfile.status),
      });
    if (userProfile.birthDate)
      this.editForm.patchValue({
        birthDate: moment(userProfile.birthDate).toISOString() || '',
      });
    if (this.currentUserProfile !== undefined) this.updateUserFields();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  byteSizeNumber(base64String: string): number {
    const st = this.dataUtils.byteSize(base64String).replace(/[^0-9]/g, '');
    return parseInt(st, 10);
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
    this.isSavingUP = true;
    this.isSavingU = true;
    const userProfile = this.createProfileFromForm();
    const user = this.createUserFromForm();
    if (userProfile.id !== undefined && user != null) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
      this.subscribeToRespond(this.accountService.save(user));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createProfileFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      birthDate: moment(this.editForm.get(['birthDate'])!.value),
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      status: this.currentUserProfile.status,
      userId: this.currentUserProfile.userId,
    };
  }

  private createUserFromForm(): Account {
    this.currentAccount.firstName = this.editForm.get('firstName')!.value;
    this.currentAccount.lastName = this.editForm.get('lastName')!.value;
    this.currentAccount.email = this.editForm.get('email')!.value;
    return this.currentAccount;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSavingUP = false;
    this.successUP = true;
    setTimeout(() => {
      this.successUP = false;
    }, 3000);
  }

  protected onSaveError(): void {
    this.isSavingUP = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  getStatusCapitalized(status?: Status): string {
    if (!status) return 'Not defined';
    const stStatus = status.toString();
    return stStatus[0].toUpperCase() + stStatus.substr(1).toLowerCase();
  }

  private updateUserFields(): void {
    // const userLogin = userProfile.userLogin;
    // if (userLogin) {
    this.accountService.identity().subscribe(resUser => {
      if (resUser) {
        this.editForm.patchValue({
          firstName: resUser.firstName,
          lastName: resUser.lastName,
          email: resUser.email,
        });
        this.currentAccount = resUser;
      }
    });
    // }
  }

  private subscribeToRespond(iUserObservable: Observable<IUser>): void {
    iUserObservable.subscribe(
      () => {
        this.isSavingU = false;
        this.successU = true;
        this.accountService.authenticate(this.currentAccount);
        setTimeout(() => {
          this.successU = false;
        }, 3000);
      },
      () => {
        this.isSavingU = false;
        this.successU = false;
      }
    );
  }
}
