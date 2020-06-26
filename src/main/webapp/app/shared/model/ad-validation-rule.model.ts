export interface IAdValidationRule {
  id?: number;
  uid?: string;
  name?: string;
  description?: string;
  query?: string;
  active?: boolean;
  adOrganizationId?: number;
}

export class AdValidationRule implements IAdValidationRule {
  constructor(
    public id?: number,
    public uid?: string,
    public name?: string,
    public description?: string,
    public query?: string,
    public active?: boolean,
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}
