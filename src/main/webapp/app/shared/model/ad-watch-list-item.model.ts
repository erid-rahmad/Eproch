export const enum AdWatchListActionType {
  MENU = 'MENU',
  URL = 'URL'
}

export interface IAdWatchListItem {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  serviceName?: string;
  restApiEndpoint?: string;
  websocketEndpoint?: string;
  actionType?: AdWatchListActionType;
  actionUrl?: string;
  filterQuery?: string;
  accentColor?: string;
  icon?: string;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
  adMenu?: any;
  adMenuId?: number;
  adWatchListId?: number;
  count?: number;
}

export class AdWatchListItem implements IAdWatchListItem {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public serviceName?: string,
    public restApiEndpoint?: string,
    public websocketEndpoint?: string,
    public actionType?: AdWatchListActionType,
    public actionUrl?: string,
    public filterQuery?: string,
    public accentColor?: string,
    public icon?: string,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public adMenu?: any,
    public adMenuId?: number,
    public adWatchListId?: number,
    public count?: number
  ) {
    this.active = this.active || false;
  }
}
