import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IUserProfile {
  id?: number;
  description?: string;
  birthDate?: Moment;
  imageContentType?: string;
  image?: any;
  status?: Status;
  userLogin?: string;
  userId?: number;
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public description?: string,
    public birthDate?: Moment,
    public imageContentType?: string,
    public image?: any,
    public status?: Status,
    public userLogin?: string,
    public userId?: number
  ) {}
}
