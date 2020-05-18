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

export const getValidatorType = (columnType: string) => {
  const type = validatorTypes.get(columnType);
  if (type !== undefined) {
    return type;
  }
  return 'any';
}
