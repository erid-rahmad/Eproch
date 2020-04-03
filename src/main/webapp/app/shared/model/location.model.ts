export interface ILocation {
  id?: number;
  streetAddress?: string;
  postalCode?: number;
  city?: string;
  stateProvince?: string;
  countryId?: number;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: number,
    public city?: string,
    public stateProvince?: string,
    public countryId?: number
  ) {}
}
