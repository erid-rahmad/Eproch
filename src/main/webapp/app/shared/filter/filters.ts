import { isArray, isPlainObject, isString } from 'lodash';

export default function buildCriteriaQueryString(options: any) {
  if (options) {
    let query = '';

    if (isPlainObject(options)) {
      for (let key in options) {
        if (query.length > 0) {
          query += '&';
        }
        query += key + '=' + options[key];
      }
    } else if (isArray(options)) {
      query = options.filter(option => !!option).join('&');
    } else if (isString(options)) {
      query = options;
    }

    return query;
  }
  return '';
}
