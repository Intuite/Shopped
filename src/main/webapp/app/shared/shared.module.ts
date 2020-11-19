import { NgModule } from '@angular/core';
import { ShoppedSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

import { JhMaterialModule } from 'app/shared/jh-material.module';
import { ColorDirective } from './material-color/color.directive';
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
import { DatepickerComponent } from './components/datepicker/datepicker.component';
import { CheckboxComponent } from './components/checkbox/checkbox.component';
import { PostTableComponent } from './tables/post-table/post-table.component';
import { IngredientPickerComponent } from './components/pickers/ingredient-picker/ingredient-picker.component';
import { SelectionComponent as IngredientPKSelectionComponent } from './components/pickers/ingredient-picker/selection/selection.component';
import { DialogComponent as IngredientPKDialogComponent } from './components/pickers/ingredient-picker/dialog/dialog.component';

import { BuyCookieComponent } from './components/buy-cookie/buy-cookie.component';
import { BundlePickerDialogComponent } from './components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';
import { CheckOutComponent } from './components/check-out/check-out.component';
import { FilterPipe } from './components/pickers/pipes/filter.pipe';
import { IngredientTagTableComponent } from './components/tables/ingredient-tag-table/ingredient-tag-table.component';
import { GiveAwardComponent } from './components/give-award/give-award.component';
import { AwardPickerDialogComponent } from './components/giveAward/award-picker-dialog/award-picker-dialog.component';
import { BasePickerComponent } from './components/pickers/base-picker/base-picker.component';
import { DialogComponent } from './components/pickers/base-picker/dialog/dialog.component';
import { PanelComponent } from './components/pickers/base-picker/panel/panel.component';
import { ChipsComponent } from './components/pickers/base-picker/chips/chips.component';
import { ListComponent } from './components/pickers/base-picker/list/list.component';
import { TagFilterPipe } from './components/pickers/pipes/tag-filter.pipe';
import { RecipeTagPickerComponent } from 'app/shared/components/pickers/recipe-tag-picker/recipe-tag-picker.component';

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
    RecipeTagPickerComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    IngredientPickerComponent,
    IngredientPKSelectionComponent,
    IngredientPKDialogComponent,
    BuyCookieComponent,
    BundlePickerDialogComponent,
    CheckOutComponent,
    FilterPipe,
    IngredientTagTableComponent,
    GiveAwardComponent,
    AwardPickerDialogComponent,
    BasePickerComponent,
    DialogComponent,
    PanelComponent,
    TagFilterPipe,
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
    RecipeTagPickerComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    IngredientPickerComponent,
    IngredientPKSelectionComponent,
    IngredientPKDialogComponent,
    BuyCookieComponent,
    IngredientTagTableComponent,
    AwardPickerDialogComponent,
    GiveAwardComponent,
  ],
})
export class ShoppedSharedModule {}
