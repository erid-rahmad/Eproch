import _ from 'lodash';

export default function buildCriteriaQueryString(options: any) {
  if (options) {
    let query = '';

    if (_.isPlainObject(options)) {
      for (let key in options) {
        if (query.length > 0) {
          query += '&';
        }
        query += key + '=' + options[key];
      }
    } else if (_.isArray(options)) {
      query = options.filter(option => option !== null).join('&');
    } else if (_.isString(options)) {
      query = options;
    }

    return query;
  }
  return '';
}
