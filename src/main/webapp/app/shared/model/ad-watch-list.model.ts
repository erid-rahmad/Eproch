import { IAdWatchListItem } from '@/shared/model/ad-watch-list-item.model';

export interface IAdWatchList {
  id?: number;
  name?: string;
  description?: string;
  uid?: string;
  active?: boolean;
  adWatchListItems?: IAdWatchListItem[];
  adOrganizationId?: number;
}

export class AdWatchList implements IAdWatchList {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public uid?: string,
    public active?: boolean,
    public adWatchListItems?: IAdWatchListItem[],
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}
