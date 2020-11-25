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
import { CartComponent } from './profile/dashboard/cart/cart.component';
import { ReportsComponent } from './profile/dashboard/reports/reports.component';
import { PaymentsComponent } from './profile/dashboard/payments/payments.component';
import { ShoppedTransactionModule } from 'app/entities/transaction/transaction.module';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(accountState), ShoppedTransactionModule],
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
    CartComponent,
    ReportsComponent,
    PaymentsComponent,
  ],
})
export class AccountModule {}
