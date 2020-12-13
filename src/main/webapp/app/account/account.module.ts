import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';

import { SessionsComponent } from './sessions/sessions.component';
import { PasswordStrengthBarComponent } from './password/password-strength-bar.component';
import { RegisterComponent } from './register/register.component';
import { ActivateComponent } from './activate/activate.component';
import { PasswordComponent } from './password/password.component';
import { PasswordResetInitComponent } from './password-reset/init/password-reset-init.component';
import { PasswordResetFinishComponent } from './password-reset/finish/password-reset-finish.component';
import { SettingsComponent } from './settings/settings.component';
import { accountState } from './account.route';
import { ProfileComponent } from './profile/profile.component';
import { UserInfoComponent } from './profile/user-info/user-info.component';
import { DashboardComponent } from './profile/dashboard/dashboard.component';
import { RecipesComponent } from './profile/dashboard/recipes/recipes.component';
import { CollectionsComponent } from './profile/dashboard/collections/collections.component';
import { ReportsComponent } from './profile/dashboard/reports/reports.component';
import { PaymentsComponent } from './profile/dashboard/payments/payments.component';
import { ShoppedTransactionModule } from 'app/entities/transaction/transaction.module';
import { ShoppedRecipeModule } from 'app/entities/recipe/recipe.module';
import { AnaliticaComponent } from './profile/dashboard/analitica/analitica.component';
// Cart
import { CartComponent } from 'app/account/profile/dashboard/cart/cart.component';
import { ListComponent as CartIngredientListComponent } from 'app/account/profile/dashboard/cart/list/list.component';
import { AddIngredientsComponent as CartAddIngredientsComponent } from 'app/account/profile/dashboard/cart/dialog/add-ingredients/add-ingredients.component';
import { InfoIngredientComponent as CartIngredientInfoComponent } from 'app/account/profile/dashboard/cart/dialog/info-ingredient/info-ingredient.component';
import { RemoveIngredientComponent as CartIngredientRemoveComponent } from 'app/account/profile/dashboard/cart/dialog/remove-ingredient/remove-ingredient.component';
import { ItemComponent as CartItemComponent } from 'app/account/profile/dashboard/cart/list/item/item.component';
import { AnaliticaPComponent } from './profile/dashboard/analitica-p/analitica-p.component';
import { RecipeListComponent } from './profile/dashboard/cart/recipe-list/recipe-list.component';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(accountState), ShoppedTransactionModule, ShoppedRecipeModule],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SessionsComponent,
    SettingsComponent,
    ProfileComponent,
    UserInfoComponent,
    DashboardComponent,
    RecipesComponent,
    CollectionsComponent,
    ReportsComponent,
    PaymentsComponent,

    CartComponent,
    CartIngredientListComponent,
    CartAddIngredientsComponent,
    CartIngredientInfoComponent,
    CartIngredientRemoveComponent,
    CartItemComponent,
    AnaliticaComponent,
    AnaliticaPComponent,
    RecipeListComponent,
  ],
})
export class AccountModule {}
