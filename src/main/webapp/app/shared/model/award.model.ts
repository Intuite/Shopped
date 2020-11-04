import { Status } from 'app/shared/model/enumerations/status.model';

export interface IAward {
  id?: number;
  name?: string;
  description?: string;
  cost?: number;
  imageContentType?: string;
  image?: any;
  status?: Status;
}

export class Award implements IAward {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public cost?: number,
    public imageContentType?: string,
    public image?: any,
    public status?: Status
  ) {}
}
