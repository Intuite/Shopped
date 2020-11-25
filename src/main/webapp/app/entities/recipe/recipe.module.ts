import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { RecipeComponent } from './recipe.component';
import { RecipeDetailComponent } from './recipe-detail.component';
import { RecipeUpdateComponent } from './recipe-update.component';
import { RecipeDeleteDialogComponent } from './recipe-delete-dialog.component';
import { RecipeListComponent } from './recipe-list.component';
import { recipeRoute } from './recipe.route';
import { RecipeFilterPipe } from './recipe-filter.pipe';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeRoute)],
  declarations: [
    RecipeComponent,
    RecipeDetailComponent,
    RecipeUpdateComponent,
    RecipeDeleteDialogComponent,
    RecipeListComponent,
    RecipeFilterPipe,
  ],
  entryComponents: [RecipeDeleteDialogComponent],
  exports: [RecipeComponent, RecipeListComponent],
})
export class ShoppedRecipeModule {}
