import { IADField } from '@/shared/model/ad-field.model';

export interface IADTab {
  id?: number;
  name?: string;
  description?: string;
  deletable?: boolean;
  insertable?: boolean;
  iconName?: string;
  treeView?: boolean;
  targetEndpoint?: string;
  writable?: boolean;
  displayLogic?: string;
  readOnlyLogic?: string;
  filterQuery?: string;
  orderQuery?: string;
  tabSequence?: number;
  active?: boolean;
  aDTabs?: IADTab[];
  adfields?: IADField[];
  adOrganizationId?: number;
  adTableId?: number;
  adTableName?: string;
  parentColumnId?: number;
  parentColumnName?: string;
  foreignColumnId?: number;
  foreignColumnName?: string;
  adWindowId?: number;
  parentTabId?: number;
  parentTabName?: string;

  /**
   * The record ID of the parent tab.
   */
  parentId?: number;

  filterQueryTmp?: string;
  nativeFilterQuery?: string;

  /**
   * Used for validating form.
   */
  validationSchema?: any;
}

export class ADTab implements IADTab {

  public parentId?: number;
  public filterQueryTmp?: string = null;
  public nativeFilterQuery?: string = null;
  public validationSchema?: any = null;

  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public deletable?: boolean,
    public insertable?: boolean,
    public iconName?: string,
    public treeView?: boolean,
    public targetEndpoint?: string,
    public writable?: boolean,
    public displayLogic?: string,
    public readOnlyLogic?: string,
    public filterQuery?: string,
    public orderQuery?: string,
    public tabSequence?: number,
    public active?: boolean,
    public aDTabs?: IADTab[],
    public adfields?: IADField[],
    public adOrganizationId?: number,
    public adTableId?: number,
    public adTableName?: string,
    public parentColumnId?: number,
    public foreignColumnId?: number,
    public adWindowId?: number,
    public parentTabId?: number,
    public parentTabName?: string
  ) {
    this.writable = this.writable || true;
    this.active = this.active || true;
  }
}
