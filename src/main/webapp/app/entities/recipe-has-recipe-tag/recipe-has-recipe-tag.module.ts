import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { RecipeHasRecipeTagComponent } from './recipe-has-recipe-tag.component';
import { RecipeHasRecipeTagDetailComponent } from './recipe-has-recipe-tag-detail.component';
import { RecipeHasRecipeTagUpdateComponent } from './recipe-has-recipe-tag-update.component';
import { RecipeHasRecipeTagDeleteDialogComponent } from './recipe-has-recipe-tag-delete-dialog.component';
import { recipeHasRecipeTagRoute } from './recipe-has-recipe-tag.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeHasRecipeTagRoute)],
  declarations: [
    RecipeHasRecipeTagComponent,
    RecipeHasRecipeTagDetailComponent,
    RecipeHasRecipeTagUpdateComponent,
    RecipeHasRecipeTagDeleteDialogComponent,
  ],
  entryComponents: [RecipeHasRecipeTagDeleteDialogComponent],
})
export class ShoppedRecipeHasRecipeTagModule {}
