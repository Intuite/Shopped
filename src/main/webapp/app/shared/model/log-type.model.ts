import { Status } from 'app/shared/model/enumerations/status.model';

export interface ILogType {
  id?: number;
  name?: string;
  template?: string;
  status?: Status;
}

export class LogType implements ILogType {
  constructor(public id?: number, public name?: string, public template?: string, public status?: Status) {}
}
