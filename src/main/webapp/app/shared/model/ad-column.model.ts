export const enum ADColumnType {
  STRING = 'STRING',
  INTEGER = 'INTEGER',
  LONG = 'LONG',
  BIG_DECIMAL = 'BIG_DECIMAL',
  FLOAT = 'FLOAT',
  DOUBLE = 'DOUBLE',
  BOOLEAN = 'BOOLEAN',
  LOCAL_DATE = 'LOCAL_DATE',
  ZONED_DATE_TIME = 'ZONED_DATE_TIME',
  DURATION = 'DURATION',
  INSTANT = 'INSTANT'
}

export interface IADColumn {
  id?: number;
  name?: string;
  sqlName?: string;
  description?: string;
  fieldLength?: number;
  key?: boolean;
  type?: ADColumnType;
  foreignKey?: boolean;
  importedTable?: string;
  importedColumn?: string;
  mandatory?: boolean;
  mandatoryLogic?: string;
  displayLogic?: string;
  readOnlyLogic?: string;
  updatable?: boolean;
  alwaysUpdatable?: boolean;
  copyable?: boolean;
  defaultValue?: string;
  formatPattern?: string;
  minLength?: number;
  maxLength?: number;
  minValue?: number;
  maxValue?: number;
  identifier?: boolean;
  defaultSelection?: boolean;
  selectionSequence?: number;
  active?: boolean;
  adOrganizationId?: number;
  adReferenceId?: number;
  adValidationRuleId?: number;
  adTableId?: number;
}

export class ADColumn implements IADColumn {
  constructor(
    public id?: number,
    public name?: string,
    public sqlName?: string,
    public description?: string,
    public fieldLength?: number,
    public key?: boolean,
    public type?: ADColumnType,
    public foreignKey?: boolean,
    public importedTable?: string,
    public importedColumn?: string,
    public mandatory?: boolean,
    public mandatoryLogic?: string,
    public displayLogic?: string,
    public readOnlyLogic?: string,
    public updatable?: boolean,
    public alwaysUpdatable?: boolean,
    public copyable?: boolean,
    public defaultValue?: string,
    public formatPattern?: string,
    public minLength?: number,
    public maxLength?: number,
    public minValue?: number,
    public maxValue?: number,
    public identifier?: boolean,
    public defaultSelection?: boolean,
    public selectionSequence?: number,
    public active?: boolean,
    public adOrganizationId?: number,
    public adReferenceId?: number,
    public adValidationRuleId?: number,
    public adTableId?: number
  ) {
    this.key = this.key || false;
    this.foreignKey = this.foreignKey || false;
    this.mandatory = this.mandatory || false;
    this.updatable = this.updatable || true;
    this.active = this.active || true;
    this.alwaysUpdatable = this.alwaysUpdatable || false;
    this.copyable = this.copyable || true;
    this.identifier = this.identifier || false;
    this.defaultSelection = this.defaultSelection || false;
  }
}
