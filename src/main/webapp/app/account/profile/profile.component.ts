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

  constructor(
    protected dataUtils: JhiDataUtils,
    private userProfileService: UserProfileService,
    private userService: UserService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute
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
      },
      () => {
        this.requesting = false;
      }
    );
    // this.accountService.identity().subscribe(account => {
    //   if (account) {
    //     this.progress += 25;
    //     this.account = account;
    //     this.loadUser();
    //   }
    // }, ()=> {
    //   this.requesting = false;
    // });
  }

  loadUser(): void {
    this.activatedRoute.data.subscribe(
      ({ userProfile }) => {
        this.userProfile = userProfile;
        this.progress += 25;
        this.userService.find(userProfile.userLogin).subscribe(
          user => {
            this.user = user;
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
}
