import { Status } from 'app/shared/model/enumerations/status.model';

export interface IIngredientTag {
  id?: number;
  name?: string;
  description?: string;
  status?: Status;
  typeName?: string;
  typeId?: number;
}

export class IngredientTag implements IIngredientTag {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public status?: Status,
    public typeName?: string,
    public typeId?: number
  ) {}
}
