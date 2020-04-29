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
  writable?: boolean;
  columnNo?: number;
  columnSpan?: number;
  active?: boolean;
  adClientId?: number;
  adOrganizationId?: number;
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
    public writable?: boolean,
    public columnNo?: number,
    public columnSpan?: number,
    public active?: boolean,
    public adClientId?: number,
    public adOrganizationId?: number
  ) {
    this.staticField = this.staticField || false;
    this.labelOnly = this.labelOnly || false;
    this.showLabel = this.showLabel || false;
    this.showInGrid = this.showInGrid || false;
    this.showInDetail = this.showInDetail || false;
    this.writable = this.writable || false;
    this.active = this.active || false;
  }
}
