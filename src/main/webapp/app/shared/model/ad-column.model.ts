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
  description?: string;
  key?: boolean;
  type?: ADColumnType;
  mandatory?: boolean;
  mandatoryLogic?: string;
  readOnlyLogic?: string;
  updatable?: boolean;
  defaultValue?: string;
  formatPattern?: string;
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
    public description?: string,
    public key?: boolean,
    public type?: ADColumnType,
    public mandatory?: boolean,
    public mandatoryLogic?: string,
    public readOnlyLogic?: string,
    public updatable?: boolean,
    public defaultValue?: string,
    public formatPattern?: string,
    public minValue?: number,
    public maxValue?: number,
    public active?: boolean,
    public adClientId?: number,
    public adOrganizationId?: number,
    public adReferenceId?: number,
    public adTableId?: number
  ) {
    this.key = this.key || false;
    this.mandatory = this.mandatory || false;
    this.updatable = this.updatable || false;
    this.active = this.active || false;
  }
}
