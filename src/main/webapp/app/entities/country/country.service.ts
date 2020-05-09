import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICountry } from '@/shared/model/country.model';
import buildCriteriaQueryString from '@/shared/filter/filters';

const baseApiUrl = 'api/countries';

export default class CountryService {
  public find(id: number): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
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

  public retrieve(paginationQuery?: any, params?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}` + params)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveWithFilter(criteriaQuery: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let criteria = buildCriteriaQueryString(criteriaQuery);
      axios
        .get(baseApiUrl + `/selector?${criteria}`)
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

  public create(entity: ICountry): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
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

  public update(entity: ICountry): Promise<ICountry> {
    return new Promise<ICountry>((resolve, reject) => {
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
