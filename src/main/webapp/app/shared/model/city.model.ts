export interface ICity {
  id?: number;
  name?: string;
  countryId?: number;
  regionId?: number;
}

export class City implements ICity {
  constructor(public id?: number, public name?: string, public countryId?: number, public regionId?: number) {}
}
