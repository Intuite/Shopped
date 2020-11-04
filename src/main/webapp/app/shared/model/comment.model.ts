import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IComment {
  id?: number;
  content?: string;
  created?: Moment;
  status?: Status;
  postId?: number;
  userLogin?: string;
  userId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public content?: string,
    public created?: Moment,
    public status?: Status,
    public postId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
