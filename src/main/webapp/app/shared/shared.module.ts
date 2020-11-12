import { NgModule } from '@angular/core';
import { ShoppedSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

import { JhMaterialModule } from 'app/shared/jh-material.module';
import { ColorDirective } from './material-color/color.directive';
import { ChipsComponent } from './components/chips/chips.component';
import { ListComponent } from './components/list/list.component';
import { TabsComponent } from './components/tabs/tabs.component';
import { UserTableComponent } from './tables/material-table/user-table.component';
import { WrapperMaterialTableComponent } from './tables/wrapper-material-table/wrapper-material-table.component';
import { ExtendedModule, FlexModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';
import { DynamicTablePrototypeComponent } from './tables/dynamic-table-prototype/dynamic-table-prototype.component';
import { UnitTableComponent } from './tables/unit-table/unit-table.component';
import { TagTypeTableComponent } from './tables/tag-type-table/tag-type-table.component';
import { IngredientTableComponent } from './tables/ingredient-table/ingredient-table.component';
import { CatalogueTableComponent } from './tables/catalogue-table/catalogue-table.component';
import { AwardTableComponent } from './tables/award-table/award-table.component';

import { RecipeTagTableComponent } from './tables/recipe-tag-table/recipe-tag-table.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { DatepickerComponent } from './components/datepicker/datepicker.component';
import { CheckboxComponent } from './components/checkbox/checkbox.component';
import { PostTableComponent } from './tables/post-table/post-table.component';
import { BuyCookieComponent } from './components/buy-cookie/buy-cookie.component';
import { BundlePickerDialogComponent } from './components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';
@NgModule({
  imports: [JhMaterialModule, ShoppedSharedLibsModule, FlexModule, RouterModule, ExtendedModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ChipsComponent,
    ColorDirective,
    ListComponent,
    TabsComponent,
    UserTableComponent,
    WrapperMaterialTableComponent,
    DynamicTablePrototypeComponent,
    UnitTableComponent,
    TagTypeTableComponent,
    CatalogueTableComponent,
    AwardTableComponent,
    IngredientTableComponent,
    RecipeTagTableComponent,
    DialogComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    BuyCookieComponent,
    BundlePickerDialogComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    JhMaterialModule,
    ShoppedSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ListComponent,
    TabsComponent,
    ChipsComponent,
    ColorDirective,
    UserTableComponent,
    DynamicTablePrototypeComponent,
    UnitTableComponent,
    TagTypeTableComponent,
    CatalogueTableComponent,
    AwardTableComponent,
    IngredientTableComponent,
    RecipeTagTableComponent,
    DialogComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    BuyCookieComponent,
  ],
})
export class ShoppedSharedModule {}
