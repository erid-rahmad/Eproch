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
  readOnlyLogic?: string;
  updatable?: boolean;
  defaultValue?: string;
  formatPattern?: string;
  minLength?: number;
  maxLength?: number;
  minValue?: number;
  maxValue?: number;
  active?: boolean;
  adClientId?: number;
  adOrganizationId?: number;
  adReferenceId?: number;
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
    public readOnlyLogic?: string,
    public updatable?: boolean,
    public defaultValue?: string,
    public formatPattern?: string,
    public minLength?: number,
    public maxLength?: number,
    public minValue?: number,
    public maxValue?: number,
    public active?: boolean,
    public adClientId?: number,
    public adOrganizationId?: number,
    public adReferenceId?: number,
    public adTableId?: number
  ) {
    this.key = this.key || false;
    this.foreignKey = this.foreignKey || false;
    this.mandatory = this.mandatory || false;
    this.updatable = this.updatable || true;
    this.active = this.active || true;
  }
}
