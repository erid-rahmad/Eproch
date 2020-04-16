import axios from 'axios';

import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import buildCriteriaQueryString from '@/shared/filter/filters';

const baseApiUrl = 'api/document-type-business-categories';

export default class DocumentTypeBusinessCategoryService {
  public find(id: number): Promise<IDocumentTypeBusinessCategory> {
    return new Promise<IDocumentTypeBusinessCategory>((resolve, reject) => {
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

  public retrieveWithFilter(criteriaQuery: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let criteria = buildCriteriaQueryString(criteriaQuery);
      axios
        .get(baseApiUrl + `?${criteria}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
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

  public create(entity: IDocumentTypeBusinessCategory): Promise<IDocumentTypeBusinessCategory> {
    return new Promise<IDocumentTypeBusinessCategory>((resolve, reject) => {
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

  public update(entity: IDocumentTypeBusinessCategory): Promise<IDocumentTypeBusinessCategory> {
    return new Promise<IDocumentTypeBusinessCategory>((resolve, reject) => {
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
