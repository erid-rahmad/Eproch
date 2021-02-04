export const enum PaDashboardItemType {
  CHART = 'CHART',
  WATCHLIST = 'WATCHLIST',
  CUSTOM = 'CUSTOM'
}

export interface IPaDashboardItem {
  id?: number;
  name?: string;
  description?: string;
  columnNo?: number;
  rowNo?: number;
  type?: PaDashboardItemType;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
  adWatchListId?: number;
  paDashboardId?: number;
}

export class PaDashboardItem implements IPaDashboardItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public columnNo?: number,
    public rowNo?: number,
    public type?: PaDashboardItemType,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public adWatchListId?: number,
    public paDashboardId?: number
  ) {
    this.active = this.active || false;
  }
}
