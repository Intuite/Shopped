import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BehaviorSubject } from 'rxjs';
import { AddIngredientsComponent } from './dialog/add-ingredients/add-ingredients.component';
import { CartService } from 'app/entities/cart/cart.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { Account } from 'app/core/user/account.model';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';

@Component({
  selector: 'jhi-dashboard-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  @Input() account!: Account | null;

  act = Status.ACTIVE;
  pen = Status.PENDING;
  cartView = true;

  hasRecipes = false;
  stats = '0/0';
  changes = 0;

  visibilityAll$ = new BehaviorSubject<boolean>(true);

  constructor(
    public cartService: CartService,
    public service: CurrentCartService,
    public dialog: MatDialog,
    public chrService: CartHasRecipeService
  ) {}

  ngOnInit(): void {
    this.service.stats$.subscribe((x: string) => (this.stats = x));
    this.service.hasChanges$.subscribe(() => (this.changes = this.service.changes.length));
    this.initializeCart();
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

  showRecipeList(): void {
    this.cartView = false;
    this.checkIfHasRecipes(this.service.cart.id!);
  }

  showCart(): void {
    this.cartView = true;
    this.checkIfHasRecipes(this.service.cart.id!);
  }

  private initializeCart(): void {
    if (this.account !== null && this.account.login !== undefined)
      this.cartService
        .query({
          'userId.equals': this.account.id,
          'status.equals': 'ACTIVE',
        })
        .subscribe(response => {
          this.service.setCart(response.body, this.account);
          if (response.body !== null && response.body.length > 0) {
            this.checkIfHasRecipes(response.body[0].id!);
          }
        });
  }

  private checkIfHasRecipes(cartId: number): void {
    this.chrService.query({ 'cartId.equals': cartId }).subscribe(chrResponse => {
      this.hasRecipes = chrResponse.body !== null && chrResponse.body.length > 0;
    });
  }
}
