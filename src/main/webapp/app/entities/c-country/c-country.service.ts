import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICCountry } from '@/shared/model/c-country.model';

const baseApiUrl = 'api/c-countries';

export default class CCountryService {
  public find(id: number): Promise<ICCountry> {
    return new Promise<ICCountry>((resolve, reject) => {
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

  public create(entity: ICCountry): Promise<ICCountry> {
    return new Promise<ICCountry>((resolve, reject) => {
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

  public update(entity: ICCountry): Promise<ICCountry> {
    return new Promise<ICCountry>((resolve, reject) => {
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
