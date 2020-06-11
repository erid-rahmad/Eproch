import axios from 'axios';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import buildCriteriaQueryString from '@/shared/filter/filters';

const delay: number = 0;

interface IPaginationQuery {
  page: number;
  size: number;
  sort: string[];
}

interface IRetrieveParameter {
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
  
  public getSchemaFromAPI(schemaId: string) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (schemaId === 'table') {
          resolve({
            type: 'object',
            required: ['name'],
            properties: {
              name: {
                type: 'string'
              },
              view: {
                type: 'boolean'
              },
              active: {
                type: 'boolean'
              }
            }
          });
        } else {
          resolve({
            type: "object",
            required: ["firstName"],
            properties: {
              firstName: {
                type: "string"
              },
              lastName: {
                type: "string"
              }
            }
          });
        }
      }, delay)
    })
  }

  public getUiSchemaFromAPI(schemaId: string) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve([
          {
            component: 'el-form',
            model: 'data',
            fieldOptions: {
              attrs: {
                'label-width': '128px',
                'label-position': 'left',
                size: 'small'
              }
            },
            children: [
              {
                component: 'el-form-item',
                fieldOptions: {
                  props: {
                    label: 'Name'
                  }
                },
                children: [
                  {
                    component: 'el-row',
                    fieldOptions: {
                      props: {
                        gutter: 10
                      }
                    },
                    children: [
                      {
                        component: 'el-col',
                        fieldOptions: {
                          props: {
                            span: 8
                          }
                        },
                        children: [
                          {
                            component: 'el-input',
                            model: 'data.name',
                            fieldOptions: {
                              on: ['input'],
                              attrs: {
                                placeholder: 'Please enter table name'
                              }
                            }
                          }
                        ]
                      },
                      {
                        component: 'el-col',
                        fieldOptions: {
                          props: {
                            span: 4
                          }
                        },
                        children: [
                          {
                            component: 'el-checkbox',
                            model: 'data.view',
                            fieldOptions: {
                              on: ['change'],
                              attrs: {
                                label: 'Is Table View'
                              }
                            }
                          }
                        ]
                      }
                    ]
                  }
                ]
              },
              {
                component: 'el-form-item',
                fieldOptions: {
                  props: {
                    label: 'Active'
                  }
                },
                children: [
                  {
                    component: 'el-col',
                    fieldOptions: {
                      props: {
                        span: 4
                      }
                    },
                    children: [
                      {
                        component: 'el-switch',
                        model: 'data.active',
                        fieldOptions: {
                          on: ['change']
                        }
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ])
      }, delay)
    })
  }

  public getDataFromAPI(schemaId: string) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (schemaId === 'table') {
          resolve({
            data: {
              name: '',
              view: false,
              active: true
            }
          });
        } else {
          resolve({
            data: {
              firstName: "Ananta",
              lastName: "Aryadewa"
            }
          });
        }
      }, delay)
    })
  }
}