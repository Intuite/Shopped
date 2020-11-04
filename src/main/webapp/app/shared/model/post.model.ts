import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IPost {
  id?: number;
  caption?: string;
  date?: Moment;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public caption?: string,
    public date?: Moment,
    public status?: Status,
    public recipeName?: string,
    public recipeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
