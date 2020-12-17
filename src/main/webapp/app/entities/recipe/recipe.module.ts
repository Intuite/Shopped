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
import { RecipeReversePipe } from './recipe-reverse.pipe';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeRoute)],
  declarations: [
    RecipeComponent,
    RecipeDetailComponent,
    RecipeUpdateComponent,
    RecipeDeleteDialogComponent,
    RecipeListComponent,
    RecipeFilterPipe,
    RecipeReversePipe,
  ],
  entryComponents: [RecipeDeleteDialogComponent],
  exports: [RecipeComponent, RecipeListComponent, RecipeFilterPipe],
})
export class ShoppedRecipeModule {}
