import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IFollower {
  id?: number;
  created?: Moment;
  status?: Status;
  userFollowedLogin?: string;
  userFollowedId?: number;
  userLogin?: string;
  userId?: number;
}

export class Follower implements IFollower {
  constructor(
    public id?: number,
    public created?: Moment,
    public status?: Status,
    public userFollowedLogin?: string,
    public userFollowedId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
