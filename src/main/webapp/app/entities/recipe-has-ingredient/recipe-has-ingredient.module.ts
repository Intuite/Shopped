import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { RecipeHasIngredientComponent } from './recipe-has-ingredient.component';
import { RecipeHasIngredientDetailComponent } from './recipe-has-ingredient-detail.component';
import { RecipeHasIngredientUpdateComponent } from './recipe-has-ingredient-update.component';
import { RecipeHasIngredientDeleteDialogComponent } from './recipe-has-ingredient-delete-dialog.component';
import { recipeHasIngredientRoute } from './recipe-has-ingredient.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeHasIngredientRoute)],
  declarations: [
    RecipeHasIngredientComponent,
    RecipeHasIngredientDetailComponent,
    RecipeHasIngredientUpdateComponent,
    RecipeHasIngredientDeleteDialogComponent,
  ],
  entryComponents: [RecipeHasIngredientDeleteDialogComponent],
})
export class ShoppedRecipeHasIngredientModule {}
