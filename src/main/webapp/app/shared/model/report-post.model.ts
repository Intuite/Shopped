import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IReportPost {
  id?: number;
  created?: Moment;
  status?: Status;
  typeName?: string;
  typeId?: number;
  postCaption?: string;
  postId?: number;
  userLogin?: string;
  userId?: number;
}

export class ReportPost implements IReportPost {
  constructor(
    public id?: number,
    public created?: Moment,
    public status?: Status,
    public typeName?: string,
    public typeId?: number,
    public postCaption?: string,
    public postId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
