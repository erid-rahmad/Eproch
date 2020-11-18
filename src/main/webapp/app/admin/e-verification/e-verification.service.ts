import axios from 'axios';

export default class EVerificationService {
  public processSubmit(data: any): Promise<any> {
    return axios.post('api/m-verifications-submit', data);
  }
}
