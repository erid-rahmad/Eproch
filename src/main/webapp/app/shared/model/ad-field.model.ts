export interface IADField {
  id?: number;
  name?: string;
  description?: string;
  hint?: string;
  staticText?: string;
  staticField?: boolean;
  labelOnly?: boolean;
  showLabel?: boolean;
  showInGrid?: boolean;
  showInDetail?: boolean;
  gridSequence?: number;
  detailSequence?: number;
  displayLogic?: string;
  readOnlyLogic?: string;
  writable?: boolean;
  columnNo?: number;
  columnSpan?: number;
  updatable?: boolean;
  alwaysUpdatable?: boolean;
  copyable?: boolean;
  defaultValue?: string;
  formatPattern?: string;
  active?: boolean;
  adOrganizationId?: number;
  adReferenceId?: number;
  adColumnId?: number;
  adValidationRuleId?: number;
  adTabId?: number;
}

export class ADField implements IADField {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public hint?: string,
    public staticText?: string,
    public staticField?: boolean,
    public labelOnly?: boolean,
    public showLabel?: boolean,
    public showInGrid?: boolean,
    public showInDetail?: boolean,
    public gridSequence?: number,
    public detailSequence?: number,
    public displayLogic?: string,
    public readOnlyLogic?: string,
    public writable?: boolean,
    public columnNo?: number,
    public columnSpan?: number,
    public updatable?: boolean,
    public alwaysUpdatable?: boolean,
    public copyable?: boolean,
    public defaultValue?: string,
    public formatPattern?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public adReferenceId?: number,
    public adColumnId?: number,
    public adValidationRuleId?: number,
    public adTabId?: number
  ) {
    this.staticField = this.staticField || false;
    this.labelOnly = this.labelOnly || false;
    this.showLabel = this.showLabel || true;
    this.showInGrid = this.showInGrid || true;
    this.showInDetail = this.showInDetail || true;
    this.writable = this.writable || true;
    this.active = this.active || true;
    this.showLabel = this.showLabel || false;
    this.showInGrid = this.showInGrid || false;
    this.showInDetail = this.showInDetail || false;
    this.updatable = this.updatable || false;
    this.alwaysUpdatable = this.alwaysUpdatable || false;
    this.copyable = this.copyable || false;
  }
}
