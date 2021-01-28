export interface IPaDashboardPreference {
  id?: number;
  columnNo?: number;
  rowNo?: number;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
  adUserId?: number;
  paDashboardItemId?: number;
}

export class PaDashboardPreference implements IPaDashboardPreference {
  constructor(
    public id?: number,
    public columnNo?: number,
    public rowNo?: number,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public adUserId?: number,
    public paDashboardItemId?: number
  ) {
    this.active = this.active || false;
  }
}
