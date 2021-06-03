import axios from 'axios';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { IADReferenceList } from '@/shared/model/ad-reference-list.model';

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

export interface IExportWizardParameter {
  windowId: number;
  currentRowOnly?: boolean;
  recordId?: number;
  includeLines?: boolean;
  includedSubTabs?: number[];
  parameterMapping?: Record<string, any>;
}

export default class DynamicWindowService {
  
  constructor(
    public baseApiUrl?: string
  ) {}

  public count(criteriaQuery?: string | string[] | object): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const queryParams = buildCriteriaQueryString(criteriaQuery);
      axios
        .get(`${this.baseApiUrl}/count?${queryParams}`)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

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

  public retrieveReferenceLists(code: string, pagination: IPaginationQuery = { page: 0, size: 1000, sort: ['name']}) {
    return new Promise<IADReferenceList[]>((resolve, reject) => {
      const criteriaQuery = [
        'active.equals=true',
        `adReferenceValue.equals=${code}`
      ]

      const queryString = buildCriteriaQueryString(criteriaQuery) + '&' + buildPaginationQueryOpts(pagination);

      axios.get(`/api/ad-reference-lists?` + queryString)
        .then(function(res) {
          resolve(res.data as IADReferenceList[]);
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

  public applyDocAction(entity: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${this.baseApiUrl}/update-doc-status`, entity)
        .then(function(res) {
          resolve(res.status);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public import(data: FormData) {
    return new Promise<any>((resolve, reject) => {
      axios
        .post('/api/ad-tables/import', data, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(function(res) {
          resolve(res);
        })
        .catch(function(err) {
          reject(err);
        });
    })
  }

  public export(data: IExportWizardParameter) {
    let params = [
      `windowId=${data.windowId}`,
      `currentRowOnly=${data.currentRowOnly || false}`,
      data.currentRowOnly && data.recordId ? `recordId=${data.recordId}` : null
    ];

    if (data.includeLines && data.includedSubTabs?.length) {
      params.push('includeLines=true');
      params.push(`includedSubTabs=${data.includedSubTabs.join(',')}`);
    }

    if (data.parameterMapping) {
      for (const key in data.parameterMapping) {
        params.push(`parameterMapping[${key}]=${data.parameterMapping[key]}`);
      }
    }

    return new Promise<any>((resolve, reject) => {
      axios
        .get(`/api/ad-windows/export?${buildCriteriaQueryString(params)}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(function(err) {
          reject(err);
        });
    })
  }
}
