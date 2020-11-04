import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CartHasRecipeComponent } from './cart-has-recipe.component';
import { CartHasRecipeDetailComponent } from './cart-has-recipe-detail.component';
import { CartHasRecipeUpdateComponent } from './cart-has-recipe-update.component';
import { CartHasRecipeDeleteDialogComponent } from './cart-has-recipe-delete-dialog.component';
import { cartHasRecipeRoute } from './cart-has-recipe.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(cartHasRecipeRoute)],
  declarations: [CartHasRecipeComponent, CartHasRecipeDetailComponent, CartHasRecipeUpdateComponent, CartHasRecipeDeleteDialogComponent],
  entryComponents: [CartHasRecipeDeleteDialogComponent],
})
export class ShoppedCartHasRecipeModule {}
