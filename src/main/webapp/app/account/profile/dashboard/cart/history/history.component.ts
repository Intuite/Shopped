import { Component, Input, OnInit } from '@angular/core';
import { ICart } from 'app/shared/model/cart.model';
import { Account } from 'app/core/user/account.model';
import { CartService } from 'app/entities/cart/cart.service';
import { MatDialog } from '@angular/material/dialog';
import { ItemComponent } from 'app/account/profile/dashboard/cart/history/item/item.component';

@Component({
  selector: 'jhi-cart-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss'],
})
export class HistoryComponent implements OnInit {
  inactiveCartList!: ICart[];
  @Input() account!: Account | null;

  constructor(private cartService: CartService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.cartService
      .query({
        'status.equals': 'INACTIVE',
        'userLogin.id': this.account!.id,
      })
      .subscribe(cartListResponse => {
        if (cartListResponse.body !== null) this.inactiveCartList = cartListResponse.body.reverse();
        else this.inactiveCartList = [];
      });
  }

  open(cart: ICart): void {
    this.dialog.open(ItemComponent, {
      data: cart,
      minWidth: '60%',
    });
  }
}
