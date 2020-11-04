import { Status } from 'app/shared/model/enumerations/status.model';

export interface INotificationType {
  id?: number;
  name?: string;
  status?: Status;
}

export class NotificationType implements INotificationType {
  constructor(public id?: number, public name?: string, public status?: Status) {}
}
