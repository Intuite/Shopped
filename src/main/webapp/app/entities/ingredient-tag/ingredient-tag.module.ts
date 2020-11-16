import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { IngredientTagComponent } from './ingredient-tag.component';
import { IngredientTagDetailComponent } from './ingredient-tag-detail.component';
import { IngredientTagUpdateComponent } from './ingredient-tag-update.component';
import { IngredientTagDeleteDialogComponent } from './ingredient-tag-delete-dialog.component';
import { ingredientTagRoute } from './ingredient-tag.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(ingredientTagRoute)],
  declarations: [IngredientTagComponent, IngredientTagDetailComponent, IngredientTagUpdateComponent, IngredientTagDeleteDialogComponent],
  entryComponents: [IngredientTagDetailComponent, IngredientTagDeleteDialogComponent],
})
export class ShoppedIngredientTagModule {}
