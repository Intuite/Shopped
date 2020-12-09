import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BehaviorSubject } from 'rxjs';
import { AddIngredientsComponent } from './dialog/add-ingredients/add-ingredients.component';
import { CartService } from 'app/entities/cart/cart.service';
import { ICart } from 'app/shared/model/cart.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-dashboard-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  @Input() account!: Account | null;

  act = Status.ACTIVE;
  pen = Status.PENDING;

  requesting = true;
  stats = '0/0';
  changes = 0;

  cart!: ICart;

  visibilityAll$ = new BehaviorSubject<boolean>(true);

  constructor(public cartService: CartService, public service: CurrentCartService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.service.stats$.subscribe((x: string) => (this.stats = x));
    this.service.hasChanges$.subscribe(() => (this.changes = this.service.changes.length));
    if (this.service.cart === undefined) {
      this.initializeCart();
    }
    this.service.initTasks();
  }

  closeCart(): void {
    this.service.cart.status = Status.INACTIVE.toUpperCase() as Status;
    this.cartService.update(this.service.cart).subscribe(() => {
      this.initializeCart();
    });
  }

  saveCart(): void {
    this.service.saveChanges();
  }

  openAddIngredients(): void {
    this.dialog.open(AddIngredientsComponent);
  }

  toggleVisibility(): void {
    this.visibilityAll$.next(!this.visibilityAll$.value);
  }

  private initializeCart(): void {
    if (this.account !== null && this.account.login !== undefined) {
      this.cartService
        .query({
          'userId.equals': this.account.id,
          'status.equals': 'ACTIVE',
        })
        .subscribe(response => {
          this.service.setCart(response.body, this.account);
          this.requesting = false;
        });
    }
  }
}
