export interface IADOrganization {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  active?: boolean;
  adClientId?: number;
}

export class ADOrganization implements IADOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public description?: string,
    public active?: boolean,
    public adClientId?: number
  ) {
    this.active = this.active || false;
  }
}
