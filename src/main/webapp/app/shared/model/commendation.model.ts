import { Moment } from 'moment';

export interface ICommendation {
  id?: number;
  date?: Moment;
  postId?: number;
  awardName?: string;
  awardId?: number;
  userLogin?: string;
  userId?: number;
}

export class Commendation implements ICommendation {
  constructor(
    public id?: number,
    public date?: Moment,
    public postId?: number,
    public awardName?: string,
    public awardId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
