import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IReportComment {
  id?: number;
  created?: Moment;
  status?: Status;
  typeName?: string;
  typeId?: number;
  commentContent?: string;
  commentId?: number;
  userLogin?: string;
  userId?: number;
}

export class ReportComment implements IReportComment {
  constructor(
    public id?: number,
    public created?: Moment,
    public status?: Status,
    public typeName?: string,
    public typeId?: number,
    public commentContent?: string,
    public commentId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
