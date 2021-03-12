import { ADReferenceType } from '@/shared/model/ad-reference.model'
import { ADColumnType } from '@/shared/model/ad-column.model'
import { IADField } from '@/shared/model/ad-field.model'

export const isValidUsername = (str: string) => ['admin', 'editor'].indexOf(str.trim()) >= 0

export const isExternal = (path: string) => /^(https?:|mailto:|tel:)/.test(path)

export const isArray = (arg: any) => {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

export const isValidURL = (url: string) => {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

const validatorTypes = new Map<string, string>();
validatorTypes.set('STRING', 'string');
validatorTypes.set('BIG_DECIMAL', 'number');
validatorTypes.set('DOUBLE', 'float');
validatorTypes.set('FLOAT', 'float');
validatorTypes.set('INTEGER', 'integer');
validatorTypes.set('LONG', 'number');
validatorTypes.set('BOOLEAN', 'boolean');
validatorTypes.set('LOCAL_DATE', 'date');
validatorTypes.set('ZONED_DATE_TIME', 'date');
validatorTypes.set('INSTANT', 'date');
validatorTypes.set('DURATION', 'string');

const jsonSchemaTypes = new Map<string, string>();
jsonSchemaTypes.set('STRING', 'string');
jsonSchemaTypes.set('BIG_DECIMAL', 'number');
jsonSchemaTypes.set('DOUBLE', 'float');
jsonSchemaTypes.set('FLOAT', 'float');
jsonSchemaTypes.set('INTEGER', 'integer');
jsonSchemaTypes.set('LONG', 'number');
jsonSchemaTypes.set('BOOLEAN', 'boolean');
jsonSchemaTypes.set('LOCAL_DATE', 'string');
jsonSchemaTypes.set('ZONED_DATE_TIME', 'string');
jsonSchemaTypes.set('INSTANT', 'string');
jsonSchemaTypes.set('DURATION', 'string');

export const getValidatorType = (columnType: string) => {
  const type = validatorTypes.get(columnType);
  return type || 'string';
}

export const getJsonSchemaType = (columnType: string) => {
  const type = jsonSchemaTypes.get(columnType);
  return type || 'string';
}

export const isNewRecord = (row: any): boolean => {
  return !row.id;
}

export const isTableDirectLink = (field: IADField): boolean => {
  const column = field.adColumn;
  const reference = field.adReference || column?.adReference;
  return column?.foreignKey && (reference?.value === 'direct' || reference?.value === 'table');
}

export const hasReferenceList = (field: IADField) => {
  return field.adReference?.referenceType === ADReferenceType.LIST ||
    field.adColumn?.adReference?.referenceType === ADReferenceType.LIST;
}

export const isStringField = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return type === ADColumnType.STRING;
}

export const isNumericField = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return (
    type === ADColumnType.BIG_DECIMAL ||
    type === ADColumnType.DOUBLE ||
    type === ADColumnType.FLOAT ||
    type === ADColumnType.INTEGER ||
    type === ADColumnType.LONG
  );
}

export const hasPrecision = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return type !== ADColumnType.INTEGER && type !== ADColumnType.LONG;
}

export const isDateField = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return type === ADColumnType.LOCAL_DATE || type === ADColumnType.ZONED_DATE_TIME;
}

export const isDateTimeField = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return type === ADColumnType.INSTANT;
}

export const isBooleanField = (field: IADField) => {
  const type = field.type || field.adColumn?.type;
  return type === ADColumnType.BOOLEAN;
}

export const isPasswordField = (field: IADField) => {
  const reference = field.adReference || field.adColumn?.adReference;
  return reference?.value === 'password';
}

export const isActiveStatusField = (field: IADField) => {
  return ! field.virtualColumnName && isBooleanField(field) && field.adColumn.name === 'active';
}

export const isAttachmentField = (field: IADField) => {
  return field.adReference?.value === 'file' ||
    field.adColumn?.adReference?.value === 'file';
}
