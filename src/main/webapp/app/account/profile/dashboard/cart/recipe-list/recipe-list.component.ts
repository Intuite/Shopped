import { Component, OnInit } from '@angular/core';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';
import { ICartHasRecipe } from 'app/shared/model/cart-has-recipe.model';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service';
import { RemoveIngredientComponent } from 'app/account/profile/dashboard/cart/dialog/remove-ingredient/remove-ingredient.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'jhi-cart-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit {
  recipeList!: ICartHasRecipe[];

  constructor(
    private rhiService: RecipeHasIngredientService,
    private service: CurrentCartService,
    private chrService: CartHasRecipeService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.chrService.findByCart(this.service.cart.id!).subscribe(chrResponse => {
      this.recipeList = chrResponse.body || [];
    });
  }

  delete(index: number): void {
    const chr = this.recipeList[index];
    const dialogRef = this.dialog.open(RemoveIngredientComponent, {
      width: '300px',
      maxWidth: '400px',
      data: { name: chr.recipeName },
    });
    dialogRef.afterClosed().subscribe((response: any) => {
      if (response) {
        this.recipeList.splice(index, 1);
        this.service.deleteCartRecipe(chr);
      }
    });
  }
}
