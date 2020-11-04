import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICartHasIngredient {
  id?: number;
  amount?: number;
  status?: Status;
  cartId?: number;
  ingredientName?: string;
  ingredientId?: number;
}

export class CartHasIngredient implements ICartHasIngredient {
  constructor(
    public id?: number,
    public amount?: number,
    public status?: Status,
    public cartId?: number,
    public ingredientName?: string,
    public ingredientId?: number
  ) {}
}
