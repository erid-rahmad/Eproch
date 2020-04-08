import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IBusinessCategory } from '@/shared/model/business-category.model';
import buildCriteriaQueryString from '@/shared/filter/filters';

const baseApiUrl = 'api/business-categories';

export default class BusinessCategoryService {
  public find(id: number): Promise<IBusinessCategory> {
    return new Promise<IBusinessCategory>((resolve, reject) => {
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

  public create(entity: IBusinessCategory): Promise<IBusinessCategory> {
    return new Promise<IBusinessCategory>((resolve, reject) => {
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

  public update(entity: IBusinessCategory): Promise<IBusinessCategory> {
    return new Promise<IBusinessCategory>((resolve, reject) => {
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
