import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';
import Layout from '@/layout/index.vue';
import { Authority } from '@/shared/security/authority';
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
const JhiTrackerComponent = () => import('../admin/tracker/tracker.vue');
const DynamicWindow = () =>
  import(/* webpackChunkName: "dynamicWindow" */ '@/core/application-dictionary/components/DynamicWindow/dynamic-window.vue');
/* tslint:disable */
// prettier-ignore
// prettier-ignore
const CCountry = () => import('../entities/c-country/c-country.vue');
// prettier-ignore
const CCountryUpdate = () => import('../entities/c-country/c-country-update.vue');
// prettier-ignore
const CCountryDetails = () => import('../entities/c-country/c-country-details.vue');
// prettier-ignore
const CCurrency = () => import('../entities/c-currency/c-currency.vue');
// prettier-ignore
const CCurrencyUpdate = () => import('../entities/c-currency/c-currency-update.vue');
// prettier-ignore
const CCurrencyDetails = () => import('../entities/c-currency/c-currency-details.vue');
// prettier-ignore
const CRegion = () => import('../entities/c-region/c-region.vue');
// prettier-ignore
const CRegionUpdate = () => import('../entities/c-region/c-region-update.vue');
// prettier-ignore
const CRegionDetails = () => import('../entities/c-region/c-region-details.vue');
// prettier-ignore
const CCity = () => import('../entities/c-city/c-city.vue');
// prettier-ignore
const CCityUpdate = () => import('../entities/c-city/c-city-update.vue');
// prettier-ignore
const CCityDetails = () => import('../entities/c-city/c-city-details.vue');
// prettier-ignore
const CLocation = () => import('../entities/c-location/c-location.vue');
// prettier-ignore
const CLocationUpdate = () => import('../entities/c-location/c-location-update.vue');
// prettier-ignore
const CLocationDetails = () => import('../entities/c-location/c-location-details.vue');
// prettier-ignore
const CBank = () => import('../entities/c-bank/c-bank.vue');
// prettier-ignore
const CBankUpdate = () => import('../entities/c-bank/c-bank-update.vue');
// prettier-ignore
const CBankDetails = () => import('../entities/c-bank/c-bank-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

/*
  Note: sub-menu only appear when children.length>=1
  Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
*/

/*
  name:'router-name'             the name field is required when using <keep-alive>, it should also match its component's name property
                                 detail see : https://vuejs.org/v2/guide/components-dynamic-async.html#keep-alive-with-Dynamic-Components
  redirect:                      if set to 'noredirect', no redirect action will be trigger when clicking the breadcrumb
  meta: {
    authorities: ['admin', 'editor']   will control the page authorities (allow setting multiple authorities)
    title: 'title'               the name showed in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon showed in the sidebar
    hidden: true                 if true, this route will not show in the sidebar (default is false)
    alwaysShow: true             if true, will always show the root menu (default is false)
                                 if false, hide the root menu when has less or equal than one children route
    breadcrumb: false            if false, the item will be hidden in breadcrumb (default is true)
    noCache: true                if true, the page will not be cached (default is false)
    affix: true                  if true, the tag will affix in the tags-view
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
*/

/**
  ConstantRoutes
  a base page that does not have permission requirements
  all authorities can be accessed
*/
export const constantRoutes: RouteConfig[] = [
  {
    path: '/redirect',
    component: Layout,
    meta: { hidden: true },
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import(/* webpackChunkName: "redirect" */ '@/shared/components/redirect/index.vue')
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import(/* webpackChunkName: "home" */ '@/core/home/home.vue'),
        meta: {
          affix: true,
          title: 'home',
          breadcrumb: false
        }
      }
    ]
  },
  {
    path: '/forbidden',
    component: () => import(/* webpackChunkName: "error" */ '@/core/error/401.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/not-found',
    component: () => import(/* webpackChunkName: "error" */ '@/core/error/404.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/register',
    component: Layout,
    children: [
      {
        path: '/register',
        //component: () => import(/* webpackChunkName: "register" */ '@/account/register/register.vue')
        component: () => import(/* webpackChunkName: "register" */ '@/account/register/steps-form.vue')
      }
    ]
  },
  {
    path: '/account',
    component: Layout,
    redirect: '/account/settings',
    meta: {
      title: 'myAccount'
    },
    children: [
      {
        path: 'password',
        name: 'ChangePassword',
        component: ChangePassword,
        meta: {
          title: 'changePassword',
          authorities: [Authority.USER]
        }
      },
      {
        path: 'settings',
        name: 'Settings',
        component: Settings,
        meta: {
          title: 'settings',
          authorities: [Authority.USER]
        }
      },
      {
        path: 'activate',
        component: Activate,
        meta: {
          hidden: true
        }
      },
      {
        path: 'reset/request',
        component: ResetPasswordInit,
        meta: {
          hidden: true
        }
      },
      {
        path: 'reset/finish',
        component: ResetPasswordFinish,
        redirect: 'noredirect',
        meta: {
          hidden: true
        }
      }
    ]
  },
];

/**
 * This route should be placed in the end of the route list.
 */
export const notFoundRoute: RouteConfig[] = [
  {
    path: '*',
    redirect: '/404',
    meta: { hidden: true }
  }
];

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user authorities
 */
export const asyncRoutes: RouteConfig[] = [
  {
    path: '/admin',
    component: Layout,
    meta: {
      title: 'admin.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'user-management',
        name: 'JhiUserManagementComponent',
        component: JhiUserManagementComponent,
        meta: {
          title: 'admin.userManagement',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'user-management/new',
        name: 'JhiUserManagementNew',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'user-management/:userId/edit',
        name: 'JhiUserManagementEditComponent',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'user-management/:userId/view',
        name: 'JhiUserManagementViewComponent',
        component: JhiUserManagementViewComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'docs',
        name: 'JhiDocsComponent',
        component: JhiDocsComponent,
        meta: {
          title: 'admin.api',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'audits',
        name: 'JhiAuditsComponent',
        component: JhiAuditsComponent,
        meta: {
          title: 'admin.audits',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'jhi-health',
        name: 'JhiHealthComponent',
        component: JhiHealthComponent,
        meta: {
          title: 'admin.health',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'logs',
        name: 'JhiLogsComponent',
        component: JhiLogsComponent,
        meta: {
          title: 'admin.logs',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'jhi-metrics',
        name: 'JhiMetricsComponent',
        component: JhiMetricsComponent,
        meta: {
          title: 'admin.metrics',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'jhi-configuration',
        name: 'JhiConfigurationComponent',
        component: JhiConfigurationComponent,
        meta: {
          title: 'admin.config',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'jhi-tracker',
        name: 'JhiTrackerComponent',
        component: JhiTrackerComponent,
        meta: {
          title: 'admin.tracker',
          authorities: [Authority.ADMIN]
        }
      }
    ]
  }/* ,
  {
    path: '/application-dictionary',
    component: Layout,
    redirect: 'application-dictionary/window',
    meta: {
      title: 'applicationDictionary.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'table',
        name: 'TableEditor',
        component: DynamicWindow,
        meta: {
          title: 'applicationDictionary.table',
          authorities: [Authority.ADMIN],
          windowId: 1
        }
      },
      {
        path: 'window',
        name: 'WindowEditor',
        component: DynamicWindow,
        meta: {
          title: 'applicationDictionary.window',
          authorities: [Authority.ADMIN],
          windowId: 2
        }
      },
      {
        path: 'reference',
        name: 'References',
        component: DynamicWindow,
        meta: {
          title: 'applicationDictionary.reference',
          authorities: [Authority.ADMIN],
          windowId: 1051
        }
      },
      {
        path: 'validation-rule',
        name: 'ValidationRule',
        component: DynamicWindow,
        meta: {
          title: 'applicationDictionary.validationRule',
          authorities: [Authority.ADMIN],
          windowId: 3001
        }

      }
    ]
  },
  {
    path: '/master-data',
    component: Layout,
    redirect: 'master-data/currency',
    meta: {
      title: 'masterData.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'currency',
        name: 'Currency',
        component: DynamicWindow,
        meta: {
          title: 'currency.list',
          authorities: [Authority.ADMIN],
          windowId: 3851
        }
      },
      {
        path: 'country',
        name: 'Country',
        component: DynamicWindow,
        meta: {
          title: 'country.list',
          authorities: [Authority.ADMIN],
          windowId: 3852
        }
      },
      {
        path: 'location',
        name: 'Location',
        component: DynamicWindow,
        meta: {
          title: 'location.list',
          authorities: [Authority.ADMIN],
          windowId: 3853
        }
      },
      {
        path: 'bank',
        name: 'Bank',
        component: DynamicWindow,
        meta: {
          title: 'bank.list',
          authorities: [Authority.ADMIN],
          windowId: 3854
        }
      }
    ]
  } */
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];

// prettier-ignore
const createRouter = () => new Router({
  // mode: 'history',
  scrollBehavior: (to, from, savedPosition) => {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  },
  base: process.env.BASE_URL,
  routes: constantRoutes
})

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  (router as any).matcher = (newRouter as any).matcher; // reset router
}

export default router;
export const defaultLayout = Layout;
export const dynamicWindow = DynamicWindow;
