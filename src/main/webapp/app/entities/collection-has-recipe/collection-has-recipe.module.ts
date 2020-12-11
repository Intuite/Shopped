import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CollectionHasRecipeComponent } from './collection-has-recipe.component';
import { CollectionHasRecipeDetailComponent } from './collection-has-recipe-detail.component';
import { CollectionHasRecipeUpdateComponent } from './collection-has-recipe-update.component';
import { CollectionHasRecipeDeleteDialogComponent } from './collection-has-recipe-delete-dialog.component';
import { collectionHasRecipeRoute } from './collection-has-recipe.route';
import { ShoppedRecipeModule } from 'app/entities/recipe/recipe.module';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(collectionHasRecipeRoute), ShoppedRecipeModule],
  declarations: [
    CollectionHasRecipeComponent,
    CollectionHasRecipeDetailComponent,
    CollectionHasRecipeUpdateComponent,
    CollectionHasRecipeDeleteDialogComponent,
  ],
  entryComponents: [CollectionHasRecipeDeleteDialogComponent],
})
export class ShoppedCollectionHasRecipeModule {}
