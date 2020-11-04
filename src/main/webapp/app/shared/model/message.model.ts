export interface IMessage {
  id?: number;
  text?: string;
  description?: string;
}

export class Message implements IMessage {
  constructor(public id?: number, public text?: string, public description?: string) {}
}
