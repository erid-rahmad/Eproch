import { ICCity } from '@/shared/model/c-city.model';

export interface ICRegion {
  id?: number;
  name?: string;
  active?: boolean;
  cCities?: ICCity[];
  countryId?: number;
}

export class CRegion implements ICRegion {
  constructor(public id?: number, public name?: string, public active?: boolean, public cCities?: ICCity[], public countryId?: number) {
    this.active = this.active || false;
  }
}
