export interface ICountry {
  id?: number;
  name?: string;
  code?: string;
  currencyId?: number;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string, public code?: string, public currencyId?: number) {}
}
