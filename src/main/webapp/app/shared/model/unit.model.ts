export interface IUnit {
  id?: number;
  name?: string;
  abbrev?: string;
}

export class Unit implements IUnit {
  constructor(public id?: number, public name?: string, public abbrev?: string) {}
}
