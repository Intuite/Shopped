import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ShoppedSharedModule } from 'app/shared/shared.module';
import { ShoppedCoreModule } from 'app/core/core.module';
import { ShoppedAppRoutingModule } from './app-routing.module';
import { ShoppedHomeModule } from './home/home.module';
import { ShoppedEntityModule } from './entities/entity.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    ShoppedSharedModule,
    ShoppedCoreModule,
    ShoppedHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ShoppedEntityModule,
    ShoppedAppRoutingModule,
    NgbModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class ShoppedAppModule {}
