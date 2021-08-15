import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdTrigger } from '@/shared/model/ad-trigger.model';
import buildCriteriaQueryString from '@/shared/filter/filters';

const baseApiUrl = 'api/ad-triggers';

export default class AdTriggerService {
  public find(id: number): Promise<IAdTrigger> {
    return new Promise<IAdTrigger>((resolve, reject) => {
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

  public retrieveWithFilter(criteriaQuery: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let criteria = buildCriteriaQueryString(criteriaQuery);
      const pagination = buildPaginationQueryOpts(paginationQuery);
      const separator = pagination.length ? '&' : '';
      axios
        .get(baseApiUrl + `?${criteria}${separator}${pagination}`)
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

  public create(entity: IAdTrigger): Promise<IAdTrigger> {
    return new Promise<IAdTrigger>((resolve, reject) => {
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

  public update(entity: IAdTrigger): Promise<IAdTrigger> {
    return new Promise<IAdTrigger>((resolve, reject) => {
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

  public runService(serviceName: string, params: any): Promise<any> {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/process/${serviceName}`, params)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
