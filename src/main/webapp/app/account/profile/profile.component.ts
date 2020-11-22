import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { User } from 'app/core/user/user.model';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { UserService } from 'app/core/user/user.service';
import { Subscription } from 'rxjs';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy, AfterViewInit {
  account: Account | null = null;
  user: User | null = null;
  userProfile: IUserProfile | null = null;
  requesting = false;
  progress = 0;
  success = true;
  authSubscription?: Subscription;
  cookie: Cookies | undefined = undefined;

  constructor(
    protected dataUtils: JhiDataUtils,
    private userProfileService: UserProfileService,
    private userService: UserService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private cookiesService: CookiesService
  ) {}

  ngOnInit(): void {
    this.dataSubscription();
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  dataSubscription(): void {
    this.requesting = true;
    this.progress += 25;
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(
      account => {
        this.account = account;
        this.progress += 25;
        this.loadUser();
        this.getCookies();
      },
      () => {
        this.requesting = false;
      }
    );
  }

  loadUser(): void {
    this.activatedRoute.data.subscribe(
      ({ user }) => {
        this.user = user;
        this.progress += 25;
        this.userProfileService.findByUser(user.id).subscribe(
          userProfile => {
            this.userProfile = userProfile.body;
            this.success = true;
            this.requesting = false;
          },
          () => {
            this.requesting = false;
          }
        );
      },
      () => {
        this.requesting = false;
      }
    );
  }

  hasAnyAuthorities(list: string[]): boolean {
    return this.accountService.hasAnyAuthority(list);
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  private getCookies(): void {
    this.cookiesService
      .query({
        ...(this.account?.id && { 'userId.equals': this.account?.id }),
      })
      .subscribe((res: HttpResponse<ICookies[]>) => this.onCookieSuccess(res.body || undefined));
  }

  private onCookieSuccess(param: ICookies[] | undefined): void {
    if (param) {
      this.cookie = param[0];
    }
  }
}
