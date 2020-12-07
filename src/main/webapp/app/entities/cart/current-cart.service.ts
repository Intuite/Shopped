import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, forkJoin, Observable, of, Subject } from 'rxjs';
import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { CartHasIngredientService } from 'app/entities/cart-has-ingredient/cart-has-ingredient.service';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { ICart } from 'app/shared/model/cart.model';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';
import { CartIngredientService } from 'app/entities/cart/cart-ingredient.service';
import { CartService } from 'app/entities/cart/cart.service';
import { Account } from 'app/core/user/account.model';

@Injectable({
  providedIn: 'root',
})
export class CurrentCartService {
  cart!: ICart;
  ci: ICartIngredient[] = [];
  itemsLoaded = false;
  cartInfo$!: Observable<string>;
  stats$ = new BehaviorSubject<string>('0/0');
  ci$ = new Subject<ICartIngredient>();
  hasChanges$ = new Subject();
  changes: Observable<HttpResponse<ICartHasIngredient>>[] = [];

  constructor(
    private ciService: CartIngredientService,
    private chiService: CartHasIngredientService,
    private iService: IngredientService,
    private service: CartService
  ) {}

  deleteCartIngredient(ci: ICartIngredient): void {
    const obs = this.ciService.delete(ci);
    if (obs !== null) {
      this.changes.push(obs);
      this.hasChanges$.next();
      this.ci.splice(
        this.ci.findIndex(x => x.id === ci.id),
        1
      );
      this.ci$.next();
    }
  }

  toggleCartIngredientStatus(ci: ICartIngredient): void {
    const updt = this.ciService.toggleStatus(ci);
    this.changes.push(updt[1]);
    this.hasChanges$.next();
  }

  setCart(c: ICart[] | null, a: Account | null): void {
    if (c !== null && c.length > 0) {
      this.cart = c[0];
      this.cartInfo$ = new Observable<string>(obs => {
        setInterval(() => {
          if (this.cart.created !== undefined) {
            obs.next(`Created ${this.cart.created.fromNow()}`);
          }
        }, 1000);
      });
      this.setCartIngredients();
    } else if (a !== null) {
      this.service
        .create({
          userLogin: a.login,
          userId: a.id,
        })
        .subscribe(response => {
          if (response.body !== null) {
            this.cart = response.body;
            this.cartInfo$ = new Observable<string>(obs => {
              setInterval(() => {
                if (this.cart.created !== undefined) {
                  obs.next(`Created ${this.cart.created.fromNow()}`);
                }
              }, 1000);
            });
            this.setCartIngredients();
          }
        });
    }
  }

  setCartIngredients(): void {
    const queries: Observable<ICartIngredient>[] = [];
    if (this.cart.id !== undefined) {
      this.chiService.findByCart(this.cart.id).subscribe(response => {
        console.warn(response);
        if (response.body !== null) {
          this.ci = [];
          this.ci$.next();
          response.body.forEach(chi => {
            if (chi.ingredientId !== undefined) {
              this.iService.find(chi.ingredientId).subscribe(ing => {
                if (ing.body !== null) {
                  const obs = of(this.ciService.map(ing.body, chi));
                  obs.subscribe(x => {
                    this.ci$.next(x);
                    this.stats$.next(this.getFraction());
                    this.ci.sort((a: ICartIngredient, b: ICartIngredient) => {
                      if (a.name !== undefined && b.name !== undefined) {
                        return a.name.localeCompare(b.name);
                      }
                      return 0;
                    });
                    this.itemsLoaded = true;
                  });
                  queries.push(obs);
                }
              });
            }
          });
        }
      });
    }
    forkJoin(queries).subscribe();
  }

  addIngredients(ciList: ICartIngredient[]): void {
    ciList.forEach(ci => {
      const current = this.ci.find(fci => fci.id === ci.id);
      if (current && current.amount !== undefined && ci.amount !== undefined) {
        current.amount = current.amount + ci.amount;
        console.warn(current);
        this.changes.push(this.ciService.update(current));
        this.ci$.next(ci);
      } else {
        ci.cartId = this.cart.id;
        this.changes.push(this.ciService.create(ci));
        this.ci$.next(ci);
      }
      this.hasChanges$.next();
    });
  }

  saveChanges(): void {
    const obs = forkJoin(this.changes);
    obs.subscribe();
    this.changes = [];
    this.hasChanges$.next();
    this.setCartIngredients();
  }

  updateCartIngredientAmount(ci: ICartIngredient): void {
    this.changes.push(this.chiService.update(this.chiService.map(ci)));
    this.hasChanges$.next();
  }

  initTasks(): void {
    this.ci$.subscribe(ci => {
      this.setStats();
      if (ci && !this.ci.find(fci => fci.id === ci.id)) {
        this.ci.push(ci);
      }
    });
  }

  setStats(): void {
    this.stats$.next(this.getFraction());
  }

  private getFraction(): string {
    return `${
      this.ci.filter(x => {
        if (x.status !== undefined) {
          return x.status.toUpperCase() === 'ACTIVE';
        }
        return null;
      }).length
    }/${this.ci.length}`;
  }
}
