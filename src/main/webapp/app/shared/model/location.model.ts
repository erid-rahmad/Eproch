export interface ILocation {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  cityId?: number;
  vendorId?: number;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public cityId?: number,
    public vendorId?: number
  ) {}
}
