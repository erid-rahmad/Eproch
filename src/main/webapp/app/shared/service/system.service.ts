import axios from 'axios';

const baseApiUrl = 'api/system';

export default class SystemService {
  public getMasterClock(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/master-clock`)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
