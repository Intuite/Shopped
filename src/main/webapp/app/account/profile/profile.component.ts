import { Component, OnDestroy, OnInit } from '@angular/core';
import { User } from 'app/core/user/user.model';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { UserService } from 'app/core/user/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  user: User | null = null;
  progress = 0;
  success = true;
  authSubscription?: Subscription;

  constructor(
    protected dataUtils: JhiDataUtils,
    private userService: UserService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.dataSubscription();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  dataSubscription(): void {
    this.progress += 25;
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      this.progress += 25;
      this.loadUser();
    });
  }

  loadUser(): void {
    this.activatedRoute.data.subscribe(({ user }) => {
      this.user = user;
      this.progress += 25;
    });
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
