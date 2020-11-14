import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { RecipeTagComponent } from './recipe-tag.component';
import { RecipeTagDetailComponent } from './recipe-tag-detail.component';
import { RecipeTagUpdateComponent } from './recipe-tag-update.component';
import { RecipeTagDeleteDialogComponent } from './recipe-tag-delete-dialog.component';
import { recipeTagRoute } from './recipe-tag.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeTagRoute)],
  declarations: [RecipeTagComponent, RecipeTagDetailComponent, RecipeTagUpdateComponent, RecipeTagDeleteDialogComponent],
  entryComponents: [RecipeTagUpdateComponent, RecipeTagDeleteDialogComponent],
})
export class ShoppedRecipeTagModule {}
