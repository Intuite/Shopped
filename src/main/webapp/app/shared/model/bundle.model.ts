export interface IBundle {
  id?: number;
  name?: string;
  cost?: number;
  cookieAmount?: number;
}

export class Bundle implements IBundle {
  constructor(public id?: number, public name?: string, public cost?: number, public cookieAmount?: number) {}
}
