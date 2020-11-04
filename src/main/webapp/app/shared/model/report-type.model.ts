import { Status } from 'app/shared/model/enumerations/status.model';

export interface IReportType {
  id?: number;
  name?: string;
  text?: string;
  status?: Status;
}

export class ReportType implements IReportType {
  constructor(public id?: number, public name?: string, public text?: string, public status?: Status) {}
}
