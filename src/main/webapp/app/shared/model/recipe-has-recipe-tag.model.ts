import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecipeHasRecipeTag {
  id?: number;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  recipeTagName?: string;
  recipeTagId?: number;
}

export class RecipeHasRecipeTag implements IRecipeHasRecipeTag {
  constructor(
    public id?: number,
    public status?: Status,
    public recipeName?: string,
    public recipeId?: number,
    public recipeTagName?: string,
    public recipeTagId?: number
  ) {}
}
