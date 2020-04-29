export interface IADTab {
  id?: number;
  name?: string;
  description?: string;
  targetEndpoint?: string;
  level?: number;
  writable?: boolean;
  displayLogic?: string;
  readOnlyLogic?: string;
  filterQuery?: string;
  orderQuery?: string;
  active?: boolean;
  adClientId?: number;
  adOrganizationId?: number;
  adTableId?: number;
  adWindowId?: number;
}

export class ADTab implements IADTab {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public targetEndpoint?: string,
    public level?: number,
    public writable?: boolean,
    public displayLogic?: string,
    public readOnlyLogic?: string,
    public filterQuery?: string,
    public orderQuery?: string,
    public active?: boolean,
    public adClientId?: number,
    public adOrganizationId?: number,
    public adTableId?: number,
    public adWindowId?: number
  ) {
    this.writable = this.writable || false;
    this.active = this.active || false;
  }
}
