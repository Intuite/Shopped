export interface ICatalogue {
  id?: number;
  idCatalogue?: string;
  value?: string;
}

export class Catalogue implements ICatalogue {
  constructor(public id?: number, public idCatalogue?: string, public value?: string) {}
}
