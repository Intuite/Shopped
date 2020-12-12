import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';
import { BehaviorSubject } from 'rxjs';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service';
import { RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';

@Component({
  selector: 'jhi-info-ingredient',
  templateUrl: './info-ingredient.component.html',
  styleUrls: ['./info-ingredient.component.scss'],
})
export class InfoIngredientComponent implements OnInit {
  form!: FormGroup;
  recipeIngredientInfo = new BehaviorSubject<RecipeHasIngredient[]>([]);
  requesting!: boolean;

  constructor(
    public dialogRef: MatDialogRef<InfoIngredientComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ICartIngredient,
    public service: CurrentCartService,
    private fb: FormBuilder,
    private cartHasRecipeService: CartHasRecipeService,
    private recipeHasIngredientService: RecipeHasIngredientService
  ) {}

  ngOnInit(): void {
    this.getRecipeInfo();
    this.form = this.fb.group({
      amount: [this.data.amount, [Validators.required, Validators.min(1)]],
    });
    this.recipeIngredientInfo.subscribe(x => {
      this.requesting = !(x.length > 0);
      console.warn(x);
      console.warn(this.requesting);
    });
  }

  updateAmount(): void {
    this.data.amount = this.form.value.amount;
    this.service.updateCartIngredientAmount(this.data);
    this.dialogRef.close();
  }

  getRecipeInfo(): void {
    this.requesting = false;
    this.cartHasRecipeService.findByCart(this.data.cartId!).subscribe(chrList => {
      const recipeList = chrList.body !== null ? chrList.body : [];
      recipeList.forEach(chr => {
        this.recipeHasIngredientService
          .query({
            'recipeId.equals': chr.recipeId,
            'ingredientId.equals': this.data.id,
          })
          .subscribe(x => {
            if (x.body !== null && x.body.length > 0) this.recipeIngredientInfo.next(x.body.concat(this.recipeIngredientInfo.value));
            else this.requesting = false;
          });
      });
    });
  }

  close(): void {
    this.dialogRef.close();
  }
}
