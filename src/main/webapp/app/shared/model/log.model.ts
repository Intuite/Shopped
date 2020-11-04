import { Moment } from 'moment';

export interface ILog {
  id?: number;
  description?: string;
  created?: Moment;
  typeName?: string;
  typeId?: number;
  userLogin?: string;
  userId?: number;
}

export class Log implements ILog {
  constructor(
    public id?: number,
    public description?: string,
    public created?: Moment,
    public typeName?: string,
    public typeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
