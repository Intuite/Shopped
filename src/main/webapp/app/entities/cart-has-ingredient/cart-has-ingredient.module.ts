import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CartHasIngredientComponent } from './cart-has-ingredient.component';
import { CartHasIngredientDetailComponent } from './cart-has-ingredient-detail.component';
import { CartHasIngredientUpdateComponent } from './cart-has-ingredient-update.component';
import { CartHasIngredientDeleteDialogComponent } from './cart-has-ingredient-delete-dialog.component';
import { cartHasIngredientRoute } from './cart-has-ingredient.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(cartHasIngredientRoute)],
  declarations: [
    CartHasIngredientComponent,
    CartHasIngredientDetailComponent,
    CartHasIngredientUpdateComponent,
    CartHasIngredientDeleteDialogComponent,
  ],
  entryComponents: [CartHasIngredientDeleteDialogComponent],
})
export class ShoppedCartHasIngredientModule {}
