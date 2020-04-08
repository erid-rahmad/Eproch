export default function buildCriteriaQueryString(options) {
    if (options) {
        let query = '';
        for (let key in options) {
          if (query.length > 0) {
            query += '&';
          }
          query += key + '=' + options[key];
        }
        return query;
      }
      return '';
}