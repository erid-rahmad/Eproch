import { IADField } from '@/shared/model/ad-field.model';

export interface IADTab {
  id?: number;
  name?: string;
  description?: string;
  targetEndpoint?: string;
  writable?: boolean;
  displayLogic?: string;
  readOnlyLogic?: string;
  filterQuery?: string;
  orderQuery?: string;
  active?: boolean;
  aDTabs?: IADTab[];
  aDFields?: IADField[];
  adClientId?: number;
  adOrganizationId?: number;
  adTableId?: number;
  parentColumnId?: number;
  foreignColumnId?: number;
  adWindowId?: number;
  parentTabId?: number;
}

export class ADTab implements IADTab {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public targetEndpoint?: string,
    public writable?: boolean,
    public displayLogic?: string,
    public readOnlyLogic?: string,
    public filterQuery?: string,
    public orderQuery?: string,
    public active?: boolean,
    public aDTabs?: IADTab[],
    public aDFields?: IADField[],
    public adClientId?: number,
    public adOrganizationId?: number,
    public adTableId?: number,
    public parentColumnId?: number,
    public foreignColumnId?: number,
    public adWindowId?: number,
    public parentTabId?: number
  ) {
    this.writable = this.writable || false;
    this.active = this.active || false;
  }
}
