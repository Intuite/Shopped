import { Status } from 'app/shared/model/enumerations/status.model';

export interface IIngredient {
  id?: number;
  name?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
  status?: Status;
  unitAbbrev?: string;
  unitId?: number;
}

export class Ingredient implements IIngredient {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public imageContentType?: string,
    public image?: any,
    public status?: Status,
    public unitAbbrev?: string,
    public unitId?: number
  ) {}
}
