import { ICRegion } from '@/shared/model/c-region.model';
import { ICCity } from '@/shared/model/c-city.model';

export interface ICCountry {
  id?: number;
  name?: string;
  code?: string;
  withRegion?: boolean;
  active?: boolean;
  currencyId?: number;
  cRegions?: ICRegion[];
  cCities?: ICCity[];
}

export class CCountry implements ICCountry {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public withRegion?: boolean,
    public active?: boolean,
    public currencyId?: number,
    public cRegions?: ICRegion[],
    public cCities?: ICCity[]
  ) {
    this.withRegion = this.withRegion || false;
    this.active = this.active || false;
  }
}
