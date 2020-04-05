import { ICity } from '@/shared/model/city.model';

export interface IRegion {
  id?: number;
  name?: string;
  cities?: ICity[];
  countryId?: number;
}

export class Region implements IRegion {
  constructor(public id?: number, public name?: string, public cities?: ICity[], public countryId?: number) {}
}
