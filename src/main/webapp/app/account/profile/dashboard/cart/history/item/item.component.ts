import { Component, Inject, OnInit } from '@angular/core';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';
import { CartHasIngredientService } from 'app/entities/cart-has-ingredient/cart-has-ingredient.service';
import { ICart } from 'app/shared/model/cart.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IRecipe } from 'app/shared/model/recipe.model';
import { Subject } from 'rxjs';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';
import { CartIngredientService } from 'app/entities/cart/cart-ingredient.service';

@Component({
  selector: 'jhi-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss'],
})
export class ItemComponent implements OnInit {
  recipeList: IRecipe[] = [];
  ingredientList: ICartIngredient[] = [];
  private rl$ = new Subject<IRecipe>();
  private il$ = new Subject<ICartIngredient>();

  constructor(
    public dialogRef: MatDialogRef<ItemComponent>,
    private chrService: CartHasRecipeService,
    private ciService: CartIngredientService,
    private chiService: CartHasIngredientService,
    private ingredientService: IngredientService,
    private recipeService: RecipeService,
    @Inject(MAT_DIALOG_DATA) public data: ICart
  ) {}

  ngOnInit(): void {
    this.rl$.subscribe(recipe => {
      this.recipeList.push(recipe);
      this.recipeList.sort((a: IRecipe, b: IRecipe) => {
        if (a.name !== undefined && b.name !== undefined) {
          return a.name.localeCompare(b.name);
        }
        return 0;
      });
    });
    this.il$.subscribe(ci => {
      this.ingredientList.push(ci);
      this.ingredientList.sort((a: ICartIngredient, b: ICartIngredient) => {
        if (a.name !== undefined && b.name !== undefined) {
          return a.name.localeCompare(b.name);
        }
        return 0;
      });
    });
    this.chiService.findByCart(this.data.id!).subscribe(chiResponse => {
      const ingredientList = chiResponse.body || [];
      ingredientList.forEach(chi => {
        this.ingredientService.find(chi.ingredientId!).subscribe(ingredient => {
          if (ingredient.body !== null) {
            this.il$.next(this.ciService.map(ingredient.body, chi));
          }
        });
      });
    });
    this.chrService.findByCart(this.data.id!).subscribe(chrResponse => {
      const recipeList = chrResponse.body || [];
      recipeList.forEach(chr => {
        this.recipeService.find(chr.recipeId).subscribe(recipe => {
          if (recipe.body !== null) this.rl$.next(recipe.body);
        });
      });
    });
  }

  close(): void {
    this.dialogRef.close();
  }
}
