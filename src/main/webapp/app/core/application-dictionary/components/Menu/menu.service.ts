import axios from 'axios';
import { IMenu } from './menu.model';
import { Authority } from '@/shared/security/authority';
import { dynamicWindow, defaultLayout } from '@/router';
import { IRetrieveParameter } from '../DynamicWindow/dynamic-window.service';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { camelCase } from 'lodash';

const baseApiUrl = 'api/ad-menus';

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
      component: this.getComponent(data.action),
      meta: {
        title: data.name,
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

  private getComponent(action: string) {
    return action === 'WINDOW' ? dynamicWindow : defaultLayout;
  }

  public retrieve(query?: IRetrieveParameter): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      let queryParams = buildPaginationQueryOpts(query?.paginationQuery);
      const buildRouter = this.buildRouter;

      if (query?.criteriaQuery) {
        if (queryParams.length) {
          queryParams += '&';
        }
        queryParams += buildCriteriaQueryString(query.criteriaQuery);
      }

      axios
        .get(`${baseApiUrl}?${queryParams}`)
        .then(function(res) {
          const routes: IMenu[] = res.data.map(((route: any) => {
            return this.buildRouter(route);
          }).bind(this));
          resolve({data: routes});
        }.bind(this))
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
