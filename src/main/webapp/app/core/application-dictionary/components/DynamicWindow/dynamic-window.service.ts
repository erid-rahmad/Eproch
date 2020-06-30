import axios from 'axios';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import buildCriteriaQueryString from '@/shared/filter/filters';

const delay: number = 0;

export interface IPaginationQuery {
  page: number;
  size: number;
  sort: string[];
}

export interface IRetrieveParameter {
  criteriaQuery?: string | string[] | object;
  paginationQuery?: IPaginationQuery
}

export default class DynamicWindowService {
  
  constructor(
    public baseApiUrl: string
  ) {}

  public find(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${this.baseApiUrl}/${id}`)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(query?: IRetrieveParameter): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let queryParams = buildPaginationQueryOpts(query?.paginationQuery);

      if (query?.criteriaQuery) {
        if (queryParams.length) {
          queryParams += '&';
        }
        queryParams += buildCriteriaQueryString(query.criteriaQuery);
      }

      axios
        .get(`${this.baseApiUrl}?${queryParams}`)
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
        .delete(`${this.baseApiUrl}/${id}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${this.baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${this.baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public updateList(entities: any[]): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${this.baseApiUrl}`, entities)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
