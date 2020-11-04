import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITagType {
  id?: number;
  name?: string;
  description?: string;
  status?: Status;
}

export class TagType implements ITagType {
  constructor(public id?: number, public name?: string, public description?: string, public status?: Status) {}
}
