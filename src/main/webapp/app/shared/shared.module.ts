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

import { GiveAwardComponent } from './components/give-award/give-award.component';
import { AwardPickerDialogComponent } from './components/award-picker-dialog/award-picker-dialog.component';
import { BuyCookieComponent } from './components/buy-cookie/buy-cookie.component';
import { BundlePickerDialogComponent } from './components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';
import { CheckOutComponent } from './components/check-out/check-out.component';
import { IngredientTagTableComponent } from './components/tables/ingredient-tag-table/ingredient-tag-table.component';
import { RecipeTableComponent } from './tables/recipe-table/recipe-table.component';
import { RecipeTagPickerComponent } from 'app/shared/components/pickers/recipe-tag-picker/recipe-tag-picker.component';

// Ingredient Picker
import { IngredientPickerComponent } from './components/pickers/ingredient-picker/ingredient-picker.component';
import { DialogComponent as IngredientPickerDialogComponent } from './components/pickers/ingredient-picker/dialog/dialog.component';
import { PanelComponent as IngredientPickerPanelComponent } from './components/pickers/ingredient-picker/panel/panel.component';
import { ListComponent as IngredientPickerListComponent } from './components/pickers/ingredient-picker/list/list.component';
import { SelectionComponent as IngredientPickerSelectionComponent } from './components/pickers/ingredient-picker/selection/selection.component';
import { SelectionListComponent as IngredientPickerSelectionListComponent } from './components/pickers/ingredient-picker/selection-list/selection-list.component';

// Base Picker
import { BasePickerComponent } from './components/pickers/base-picker/base-picker.component';
import { DialogComponent as BasePickerDialogComponent } from './components/pickers/base-picker/dialog/dialog.component';
import { PanelComponent as BasePickerPanelComponent } from './components/pickers/base-picker/panel/panel.component';
import { ChipsComponent as BasePickerChipsComponent } from './components/pickers/base-picker/chips/chips.component';
import { ListComponent as BasePickerListComponent } from './components/pickers/base-picker/list/list.component';

// Pipes
import { FilterPipe } from './components/pickers/pipes/filter.pipe';
import { TagFilterPipe } from './components/pickers/pipes/tag-filter.pipe';
import { ViewTransactionsComponent } from './components/view-transactions/view-transactions.component';
import { AwardViewerComponent } from './components/award-viewer/award-viewer.component';
import { RecipeListComponent } from './components/recipe-list/recipe-list.component';
import { ChipListComponent } from './components/chip-list/chip-list.component';
import { NgxPayPalModule } from 'ngx-paypal';
import { WithdrawCookiesComponent } from './components/withdraw-cookies/withdraw-cookies.component';
import { WithdrawCookiesModalComponent } from './components/withdraw-cookies-modal/withdraw-cookies-modal.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import { LineChartComponent } from './components/line-chart/line-chart.component';
import { ChartsModule } from 'ng2-charts';
import { UserPickerComponent } from './components/pickers/user-picker/user-picker.component';
import { IngredientRecipesChartComponent } from './graphics/ingredient-recipes-chart/ingredient-recipes-chart.component';
import { BitesPostsChartComponent } from './graphics/bites-posts-chart/bites-posts-chart.component';
import { TagsPostsChartComponent } from './graphics/tags-posts-chart/tags-posts-chart.component';

@NgModule({
  imports: [JhMaterialModule, ShoppedSharedLibsModule, FlexModule, RouterModule, ExtendedModule, NgxPayPalModule, ChartsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ColorDirective,
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
    RecipeTableComponent,
    RecipeTagPickerComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    BuyCookieComponent,
    BundlePickerDialogComponent,
    CheckOutComponent,
    FilterPipe,
    IngredientTagTableComponent,
    TagFilterPipe,
    GiveAwardComponent,
    AwardPickerDialogComponent,
    TagFilterPipe,
    ViewTransactionsComponent,
    BasePickerComponent,
    BasePickerDialogComponent,
    BasePickerPanelComponent,
    BasePickerChipsComponent,
    BasePickerListComponent,
    IngredientPickerComponent,
    IngredientPickerSelectionComponent,
    IngredientPickerDialogComponent,
    IngredientPickerSelectionComponent,
    IngredientPickerSelectionListComponent,
    IngredientPickerPanelComponent,
    IngredientPickerListComponent,
    AwardViewerComponent,
    RecipeListComponent,
    ChipListComponent,
    UserPickerComponent,
    WithdrawCookiesComponent,
    WithdrawCookiesModalComponent,
    BarChartComponent,
    PieChartComponent,
    LineChartComponent,
    IngredientRecipesChartComponent,
    BitesPostsChartComponent,
    TagsPostsChartComponent,
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
    TabsComponent,
    ColorDirective,
    UserTableComponent,
    DynamicTablePrototypeComponent,
    UnitTableComponent,
    TagTypeTableComponent,
    CatalogueTableComponent,
    AwardTableComponent,
    IngredientTableComponent,
    RecipeTagTableComponent,
    RecipeTableComponent,
    RecipeTagPickerComponent,
    DatepickerComponent,
    CheckboxComponent,
    PostTableComponent,
    BuyCookieComponent,
    IngredientTagTableComponent,
    AwardPickerDialogComponent,
    GiveAwardComponent,
    ViewTransactionsComponent,
    BasePickerComponent,
    BasePickerDialogComponent,
    BasePickerPanelComponent,
    BasePickerChipsComponent,
    BasePickerListComponent,
    IngredientPickerComponent,
    IngredientPickerSelectionComponent,
    IngredientPickerDialogComponent,
    IngredientPickerSelectionComponent,
    IngredientPickerSelectionListComponent,
    IngredientPickerPanelComponent,
    IngredientPickerListComponent,
    AwardViewerComponent,
    RecipeListComponent,
    ChipListComponent,
    WithdrawCookiesComponent,
    BarChartComponent,
    LineChartComponent,
    PieChartComponent,
    IngredientRecipesChartComponent,
    BitesPostsChartComponent,
    TagsPostsChartComponent,
  ],
})
export class ShoppedSharedModule {}
