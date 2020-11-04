import { Status } from 'app/shared/model/enumerations/status.model';

export interface IIngredientHasIngredientTag {
  id?: number;
  status?: Status;
  ingredientName?: string;
  ingredientId?: number;
  ingredientTagName?: string;
  ingredientTagId?: number;
}

export class IngredientHasIngredientTag implements IIngredientHasIngredientTag {
  constructor(
    public id?: number,
    public status?: Status,
    public ingredientName?: string,
    public ingredientId?: number,
    public ingredientTagName?: string,
    public ingredientTagId?: number
  ) {}
}
