export interface ICookies {
  id?: number;
  amount?: number;
  walletKey?: string;
  userLogin?: string;
  userId?: number;
}

export class Cookies implements ICookies {
  constructor(public id?: number, public amount?: number, public walletKey?: string, public userLogin?: string, public userId?: number) {}
}
