import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  amount?: number;
  created?: Moment;
  description?: string;
  cookiesAmount?: number;
  userLogin?: string;
  userId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public amount?: number,
    public created?: Moment,
    public description?: string,
    public cookiesAmount?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
