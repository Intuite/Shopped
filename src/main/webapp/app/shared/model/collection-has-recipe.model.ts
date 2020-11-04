import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICollectionHasRecipe {
  id?: number;
  status?: Status;
  collectionName?: string;
  collectionId?: number;
  recipeName?: string;
  recipeId?: number;
}

export class CollectionHasRecipe implements ICollectionHasRecipe {
  constructor(
    public id?: number,
    public status?: Status,
    public collectionName?: string,
    public collectionId?: number,
    public recipeName?: string,
    public recipeId?: number
  ) {}
}
