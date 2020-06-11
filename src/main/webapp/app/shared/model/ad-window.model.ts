import { IADTab } from '@/shared/model/ad-tab.model';

export const enum ADWindowType {
  MAINTAIN = 'MAINTAIN',
  QUERY = 'QUERY',
  TRANSACTION = 'TRANSACTION'
}

export interface IADWindow {
  id?: number;
  name?: string;
  description?: string;
  titleLogic?: string;
  type?: ADWindowType;
  active?: boolean;
  aDTabs?: IADTab[];
  adOrganizationId?: number;
}

export class ADWindow implements IADWindow {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public titleLogic?: string,
    public type?: ADWindowType,
    public active?: boolean,
    public aDTabs?: IADTab[],
    public adOrganizationId?: number
  ) {
    this.active = this.active || false;
  }
}
