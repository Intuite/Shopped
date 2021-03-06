import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/core/language/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss'],
})
export class NavbarComponent implements OnInit, AfterViewInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  version: string;
  currentAccount: Account | null = null;
  accountLoaded = false;
  // userProfile!: UserProfile | null;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private alertService: JhiAlertService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  ngAfterViewInit(): void {}

  loadAccount(): void {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
      // if (this.isAuthenticated() && account) this.loadAccountData(account.id);
    });
    this.accountLoaded = true;
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    const isAuthenticated = this.accountService.isAuthenticated();
    if (isAuthenticated) {
      this.prepareReset();
      this.loadAccount();
    }
    return isAuthenticated;
  }

  login(): void {
    this.loginModalService.open(() => {
      this.prepareReset();
      this.loadAccount();
    });
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.prepareReset();
    this.router.navigate(['']);
  }

  prepareReset(): void {
    this.accountLoaded = false;
    this.currentAccount = null;
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }

  getNotification(): void {
    this.alertService.addAlert({ toast: true, type: 'danger', msg: 'global.menu.home' }, []);
  }
}
