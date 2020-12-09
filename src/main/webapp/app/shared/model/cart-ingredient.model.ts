import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICartIngredient {
  id?: number;
  cartId?: number;
  cartHasIngredientId?: number;
  amount?: number;
  name?: string;
  unitAbbrev?: string;
  imageContentType?: string;
  image?: any;
  status?: Status;
}

export class CartIngredient implements ICartIngredient {
  constructor(
    public id?: number,
    public cartId?: number,
    public cartHasIngredientId?: number,
    public amount?: number,
    public name?: string,
    public unitAbbrev?: string,
    public imageContentType?: string,
    public image?: any,
    public status?: Status
  ) {}
}
