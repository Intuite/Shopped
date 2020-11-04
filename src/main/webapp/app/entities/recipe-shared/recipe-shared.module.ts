import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { RecipeSharedComponent } from './recipe-shared.component';
import { RecipeSharedDetailComponent } from './recipe-shared-detail.component';
import { RecipeSharedUpdateComponent } from './recipe-shared-update.component';
import { RecipeSharedDeleteDialogComponent } from './recipe-shared-delete-dialog.component';
import { recipeSharedRoute } from './recipe-shared.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(recipeSharedRoute)],
  declarations: [RecipeSharedComponent, RecipeSharedDetailComponent, RecipeSharedUpdateComponent, RecipeSharedDeleteDialogComponent],
  entryComponents: [RecipeSharedDeleteDialogComponent],
})
export class ShoppedRecipeSharedModule {}
