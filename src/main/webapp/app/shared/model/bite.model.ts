import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IBite {
  id?: number;
  created?: Moment;
  status?: Status;
  postId?: number;
  userLogin?: string;
  userId?: number;
}

export class Bite implements IBite {
  constructor(
    public id?: number,
    public created?: Moment,
    public status?: Status,
    public postId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
