import { IRegion } from '@/shared/model/region.model';
import { ICity } from '@/shared/model/city.model';

export interface ICountry {
  id?: number;
  name?: string;
  code?: string;
  currencyId?: number;
  regions?: IRegion[];
  cities?: ICity[];
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public currencyId?: number,
    public regions?: IRegion[],
    public cities?: ICity[]
  ) {}
}
