import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IRecipe, Recipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-recipe-update',
  templateUrl: './recipe-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class RecipeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE'];
  user!: IUser;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    portion: [null, [Validators.min(1)]],
    description: [null, [Validators.required, Validators.min(5), Validators.max(1000)]],
    duration: [null, [Validators.min(1)]],
    creation: [],
    image: [],
    imageContentType: [],
    status: [],
    userId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected recipeService: RecipeService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipe }) => {
      if (!recipe.id) {
        const today = moment().startOf('minute');
        recipe.creation = today;
      }

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account) {
          this.userService.find(account.login).subscribe(user => (this.user = user));
        }
      });

      this.updateForm(recipe);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(recipe: IRecipe): void {
    this.editForm.patchValue({
      id: recipe.id,
      name: recipe.name,
      portion: recipe.portion,
      description: recipe.description,
      duration: recipe.duration,
      creation: recipe.creation ? recipe.creation.format(DATE_TIME_FORMAT) : null,
      image: recipe.image,
      imageContentType: recipe.imageContentType,
      status: recipe.status,
      userId: recipe.userId,
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
    this.isSaving = true;
    const recipe = this.createFromForm();
    if (recipe.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeService.update(recipe));
    } else {
      this.subscribeToSaveResponse(this.recipeService.create(recipe));
    }
  }

  private createFromForm(): IRecipe {
    return {
      ...new Recipe(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      portion: this.editForm.get(['portion'])!.value,
      description: this.editForm.get(['description'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      creation: this.editForm.get(['creation'])!.value ? moment(this.editForm.get(['creation'])!.value, DATE_TIME_FORMAT) : undefined,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      status: this.editForm.get(['status'])!.value,
      userId: this.user.id,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipe>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
