import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { CartService } from 'app/entities/cart/cart.service';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  @Input() account!: Account | null;
  authSubscription?: Subscription;
  isUsersCart = false;

  constructor(private accountService: AccountService, private cartService: CartService) {}

  ngOnInit(): void {
    console.warn(this.account);
    this.setIsCurrentUserCart();
  }

  setIsCurrentUserCart(): void {
    if (this.account !== null && this.account.login !== undefined)
      this.cartService
        .query({
          'userId.equals': this.account.id,
          'status.equals': 'ACTIVE',
        })
        .subscribe(() => (this.isUsersCart = true));
  }
}
