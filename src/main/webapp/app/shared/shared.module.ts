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
import { FlexModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';
import { DynamicTablePrototypeComponent } from './tables/dinamic-table-prototype/dynamic-table-prototype.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { DatepickerComponent } from './components/datepicker/datepicker.component';
import { CheckboxComponent } from './components/checkbox/checkbox.component';
@NgModule({
  imports: [JhMaterialModule, ShoppedSharedLibsModule, FlexModule, RouterModule],
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
    DialogComponent,
    DatepickerComponent,
    CheckboxComponent,
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
  ],
})
export class ShoppedSharedModule {}
