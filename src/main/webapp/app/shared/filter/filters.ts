import _ from 'lodash';

export default function buildCriteriaQueryString(options: Array<string> | object) {
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
      query = options.join('&');
    }
    return query;
  }
  return '';
}
