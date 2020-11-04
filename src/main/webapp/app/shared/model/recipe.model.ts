import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecipe {
  id?: number;
  name?: string;
  portion?: number;
  description?: any;
  duration?: number;
  creation?: Moment;
  imageContentType?: string;
  image?: any;
  status?: Status;
  userLogin?: string;
  userId?: number;
  postId?: number;
}

export class Recipe implements IRecipe {
  constructor(
    public id?: number,
    public name?: string,
    public portion?: number,
    public description?: any,
    public duration?: number,
    public creation?: Moment,
    public imageContentType?: string,
    public image?: any,
    public status?: Status,
    public userLogin?: string,
    public userId?: number,
    public postId?: number
  ) {}
}
