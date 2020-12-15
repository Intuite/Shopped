import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';

import { IRecipe, Recipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';

import { IRecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { RecipeHasRecipeTagService } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag.service';
import { RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service.ts';

import { IngredientPickerComponent } from 'app/shared/components/pickers/ingredient-picker/ingredient-picker.component';
import { RecipeTagPickerComponent } from 'app/shared/components/pickers/recipe-tag-picker/recipe-tag-picker.component';

@Component({
  selector: 'jhi-recipe-update',
  templateUrl: './recipe-update.component.html',
  styleUrls: ['../../../content/scss/image_Select.scss'],
})
export class RecipeUpdateComponent implements OnInit {
  @ViewChild('ingredientPk') ingredientPiker!: IngredientPickerComponent;
  @ViewChild('recipeTagPk') recipePiker!: RecipeTagPickerComponent;
  isSaving = false;
  users: IUser[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE'];
  user!: IUser;
  savedIngredients: any[] = [];
  savedRecipeTags: IRecipeTag[] = [];
  recipeCreated!: IRecipe;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    portion: [null, [Validators.required, Validators.min(1)]],
    description: [null, [Validators.required]],
    duration: [null, [Validators.required, Validators.min(1)]],
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
    private accountService: AccountService,
    protected recipeHasIngredientService: RecipeHasIngredientService,
    protected recipeHasRecipeTagService: RecipeHasRecipeTagService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipe }) => {
      if (!recipe.id) {
        recipe.creation = moment().startOf('minute');
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
    this.isSaving = true;
    const recipe = this.createFromForm();
    if (recipe.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeService.update(recipe));
    } else {
      this.savedIngredients = this.ingredientPiker.getIngredients();
      this.savedRecipeTags = this.recipePiker.getRecipeTags();

      this.recipeService.create(recipe).subscribe(
        response => {
          if (response.body !== null) {
            this.selectRecipeCreated(response.body);
            this.addIngredientsToRecipe(response.body);
            this.addRecipeTagsToRecipe(response.body);
          }
        },
        () => console.warn('Error adding ingredients and tags to recipe')
      );
    }
    // this.previousState();
    this.gotoAfterSave();
  }

  selectRecipeCreated(recipe: Recipe): void {
    this.recipeCreated = recipe;
  }

  addIngredientsToRecipe(recipe: Recipe): void {
    let i = 0;
    while (i < this.savedIngredients.length) {
      const recipeHasIngredient = new RecipeHasIngredient(
        undefined,
        this.savedIngredients[i].amount,
        recipe.status,
        this.savedIngredients[i].ingredient.name,
        this.savedIngredients[i].ingredient.id,
        recipe.name,
        recipe.id
      );

      this.recipeHasIngredientService.create(recipeHasIngredient).subscribe(
        () => console.warn('Ingredient added to recipe'),
        () => console.warn('error adding ingredient to recipe')
      );
      i++;
    }
  }

  addRecipeTagsToRecipe(recipe: Recipe): void {
    let i = 0;
    while (i < this.savedRecipeTags.length) {
      const recipeHasRecipeTag = new RecipeHasRecipeTag(
        undefined,
        recipe.status,
        undefined,
        recipe.id,
        this.savedRecipeTags[i].name,
        this.savedRecipeTags[i].id
      );

      this.recipeHasRecipeTagService.create(recipeHasRecipeTag).subscribe(
        () => console.warn('Recipe Tag added to recipe'),
        () => console.warn('error adding Recipe Tag to recipe')
      );
      i++;
    }
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  gotoAfterSave(): void {
    // this.router.navigate(['/recipe', ${id: this.recipeCreated.id}, 'view']);
    // this.router.navigate(['/recipe', 'list']);
    this.previousState();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipe>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    // this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
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
}
