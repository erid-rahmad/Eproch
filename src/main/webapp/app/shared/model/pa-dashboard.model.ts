export interface IPaDashboard {
  id?: number;
  name?: string;
  description?: string;
  minColumns?: number;
  maxColumns?: number;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
}

export class PaDashboard implements IPaDashboard {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public minColumns?: number,
    public maxColumns?: number,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}
