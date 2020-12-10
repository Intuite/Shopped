import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { CartService } from 'app/entities/cart/cart.service';
import { User } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  @Input() account!: Account | null;
  @Input() user!: User | null;
  authSubscription?: Subscription;
  isUsersCart = false;

  constructor(private accountService: AccountService, private cartService: CartService) {}

  ngOnInit(): void {
    this.setIsCurrentUserCart();
  }

  setIsCurrentUserCart(): void {
    if (this.user !== null && this.user.login !== undefined && this.account !== null && this.account.login !== undefined)
      this.isUsersCart = this.user.login === this.account.login;
  }
}
