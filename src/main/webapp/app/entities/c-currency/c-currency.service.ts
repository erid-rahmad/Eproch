import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICCurrency } from '@/shared/model/c-currency.model';

const baseApiUrl = 'api/c-currencies';

export default class CCurrencyService {
  public find(id: number): Promise<ICCurrency> {
    return new Promise<ICCurrency>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ICCurrency): Promise<ICCurrency> {
    return new Promise<ICCurrency>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ICCurrency): Promise<ICCurrency> {
    return new Promise<ICCurrency>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
