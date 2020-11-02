export interface ICCity {
  id?: number;
  name?: string;
  active?: boolean;
  countryId?: number;
  regionId?: number;
}

export class CCity implements ICCity {
  constructor(public id?: number, public name?: string, public active?: boolean, public countryId?: number, public regionId?: number) {
    this.active = this.active || false;
  }
}
