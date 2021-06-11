import { IADColumn } from './ad-column.model';
import { IADReference } from './ad-reference.model';
import { IAdValidationRule } from './ad-validation-rule.model';

export interface IADField {
  adColumn: IADColumn;
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
  columnOffset?: number;
  columnSpan?: number;
  rowNo?: number;
  virtualColumnName?: string;
  mandatory?: boolean;
  mandatoryLogic?: string;
  updatable?: boolean;
  alwaysUpdatable?: boolean;
  copyable?: boolean;
  defaultValue?: string;
  formatPattern?: string;
  minLength?: number;
  maxLength?: number;
  minValue?: number;
  maxValue?: number;
  type?: string;
  active?: boolean;
  adOrganizationId?: number;
  referenceTypeName?: string;
  adReference?: IADReference;
  adReferenceId?: number;
  adColumnId?: number;
  adValidationRule?: IAdValidationRule;
  adValidationRuleId?: number;
  adTabId?: number;
  adTabName?: string;
}

export class ADField implements IADField {
  constructor(
    public adColumn: IADColumn,
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
    public columnOffset?: number,
    public columnSpan?: number,
    public rowNo?: number,
    public virtualColumnName?: string,
    public mandatory?: boolean,
    public mandatoryLogic?: string,
    public updatable?: boolean,
    public alwaysUpdatable?: boolean,
    public copyable?: boolean,
    public defaultValue?: string,
    public formatPattern?: string,
    public minLength?: number,
    public maxLength?: number,
    public minValue?: number,
    public maxValue?: number,
    public type?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public referenceTypeName?: string,
    public adReference?: IADReference,
    public adReferenceId?: number,
    public adColumnId?: number,
    public adValidationRule?: IAdValidationRule,
    public adValidationRuleId?: number,
    public adTabId?: number,
    public adTabName?: string
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
