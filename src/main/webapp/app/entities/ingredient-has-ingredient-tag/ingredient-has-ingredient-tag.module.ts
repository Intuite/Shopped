import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { IngredientHasIngredientTagComponent } from './ingredient-has-ingredient-tag.component';
import { IngredientHasIngredientTagDetailComponent } from './ingredient-has-ingredient-tag-detail.component';
import { IngredientHasIngredientTagUpdateComponent } from './ingredient-has-ingredient-tag-update.component';
import { IngredientHasIngredientTagDeleteDialogComponent } from './ingredient-has-ingredient-tag-delete-dialog.component';
import { ingredientHasIngredientTagRoute } from './ingredient-has-ingredient-tag.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(ingredientHasIngredientTagRoute)],
  declarations: [
    IngredientHasIngredientTagComponent,
    IngredientHasIngredientTagDetailComponent,
    IngredientHasIngredientTagUpdateComponent,
    IngredientHasIngredientTagDeleteDialogComponent,
  ],
  entryComponents: [IngredientHasIngredientTagDeleteDialogComponent],
})
export class ShoppedIngredientHasIngredientTagModule {}
