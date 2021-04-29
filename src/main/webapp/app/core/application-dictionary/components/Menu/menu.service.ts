import { defaultLayout, dynamicWindow, nestedLayout } from '@/router';
import buildCriteriaQueryString from '@/shared/filter/filters';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import axios from 'axios';
import { camelCase } from 'lodash';
import { IRetrieveParameter } from '../DynamicWindow/dynamic-window.service';
import { IMenu } from './menu.model';
import { forms, blankForm } from '@/router/forms';

const baseApiUrl = 'api/ad-menus/main-menu';

export default class AdMenuService {
  public find(id: number): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
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

  private buildRouter(data: any): IMenu {
    const children = data.adMenus;
    const hasChildren = children?.length;

    let router: IMenu = {
      path: data.path,
      component: this.getComponent(data),
      meta: {
        hidden: data.hidden || false,
        title: data.name,
        icon: data.icon,
        alwaysShow: data.alwaysShow || false,
        authorities: data.authorities || []
      }
    };

    if (data.redirect)
      router.redirect = data.redirect;

    if (data.action === 'WINDOW')
      router.meta.windowId = data.adWindowId;

    if (hasChildren) {
      router.children = [];
      for (let child of data.adMenus) {
        router.children.push(this.buildRouter(child));
      }
    } else {
      router.name = camelCase(data.name);
    }

    return router;
  }

  private getComponent({action, adFormName, parentMenuId}) {
    if (! action) {
      return parentMenuId ? nestedLayout : defaultLayout;
    } else if (action === 'WINDOW') {
      return dynamicWindow;
    } else if (action === 'FORM') {
      return forms.get(adFormName) || blankForm;
    }

    return blankForm;
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
        .get(`${baseApiUrl}?${queryParams}`)
        .then(res => {
          const routes: IMenu[] = res.data.map(((route: any) => {
            return this.buildRouter(route);
          }));
          resolve({data: routes});
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

  public create(entity: IMenu): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
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

  public update(entity: IMenu): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
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
