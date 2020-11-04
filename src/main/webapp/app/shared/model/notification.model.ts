import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface INotification {
  id?: number;
  content?: string;
  created?: Moment;
  status?: Status;
  typeName?: string;
  typeId?: number;
  userLogin?: string;
  userId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public content?: string,
    public created?: Moment,
    public status?: Status,
    public typeName?: string,
    public typeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
