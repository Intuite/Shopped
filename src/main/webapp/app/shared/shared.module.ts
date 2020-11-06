import { NgModule } from '@angular/core';
import { ShoppedSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

import { JhMaterialModule } from 'app/shared/jh-material.module';
import { ColorDirectiveDirective } from './material-color/color-directive.directive';
import { ChipsComponent } from './components/chips/chips.component';
import { ListComponent } from './components/list/list.component';
import { TabsComponent } from './components/tabs/tabs.component';
import { ImgSelectorComponent } from './components/img-selector/img-selector.component';
@NgModule({
  imports: [JhMaterialModule, ShoppedSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ChipsComponent,
    ColorDirectiveDirective,
    ListComponent,
    TabsComponent,
    ImgSelectorComponent,
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
    ColorDirectiveDirective,
    ImgSelectorComponent,
  ],
})
export class ShoppedSharedModule {}
