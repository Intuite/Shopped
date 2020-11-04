import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecipeHasIngredient {
  id?: number;
  amount?: number;
  status?: Status;
  ingredientName?: string;
  ingredientId?: number;
  recipeName?: string;
  recipeId?: number;
}

export class RecipeHasIngredient implements IRecipeHasIngredient {
  constructor(
    public id?: number,
    public amount?: number,
    public status?: Status,
    public ingredientName?: string,
    public ingredientId?: number,
    public recipeName?: string,
    public recipeId?: number
  ) {}
}
