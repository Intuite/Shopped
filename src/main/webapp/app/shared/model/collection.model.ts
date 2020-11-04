import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICollection {
  id?: number;
  name?: string;
  description?: string;
  created?: Moment;
  imageContentType?: string;
  image?: any;
  status?: Status;
  userLogin?: string;
  userId?: number;
}

export class Collection implements ICollection {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public created?: Moment,
    public imageContentType?: string,
    public image?: any,
    public status?: Status,
    public userLogin?: string,
    public userId?: number
  ) {}
}
