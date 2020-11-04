import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecipeShared {
  id?: number;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
}

export class RecipeShared implements IRecipeShared {
  constructor(
    public id?: number,
    public status?: Status,
    public recipeName?: string,
    public recipeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
