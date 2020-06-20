import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import { RouteConfig } from 'vue-router';
import { asyncRoutes, constantRoutes } from '@/router';
import store from '@/shared/config/store';
import { Authority } from '@/shared/security/authority';

const hasPermission = (roles: Set<string>, route: RouteConfig) => {
  const authorities: Array<string> = route.meta.authorities;
  if (route.meta && authorities && authorities.length) {
    return authorities.some(authority => roles.has(authority));
  } else {
    return true;
  }
};

export const filterAsyncRoutes = (routes: RouteConfig[], roles: Set<string>) => {
  const res: RouteConfig[] = [];
  routes.forEach(route => {
    const r = { ...route };
    if (hasPermission(roles, r)) {
      if (r.children) {
        r.children = filterAsyncRoutes(r.children, roles);
      }
      res.push(r);
    }
  });
  return res;
};

export interface IPermissionState {
  routes: RouteConfig[];
  dynamicRoutes: RouteConfig[];
}

@Module({ dynamic: true, store, name: 'permissionStore', namespaced: true })
class PermissionStore extends VuexModule implements IPermissionState {
  public routes: RouteConfig[] = [];
  public dynamicRoutes: RouteConfig[] = [];

  @Mutation
  private SET_ROUTES(routes: RouteConfig[]) {
    this.routes = constantRoutes.concat(routes);
    this.dynamicRoutes = routes;
  }

  @Action
  public async generateRoutes(roles: Set<string>) {
    let accessedRoutes: RouteConfig[];
    if (roles.has(Authority.ADMIN)) {
      accessedRoutes = asyncRoutes;
    } else {
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles);
    }
    this.SET_ROUTES(accessedRoutes);
  }
}

export const PermissionStoreModule = getModule(PermissionStore);
