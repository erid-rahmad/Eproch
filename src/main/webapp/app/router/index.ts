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
const DynamicWindow = () => import(/* webpackChunkName: "dynamicWindow" */ '@/core/application-dictionary/components/DynamicWindow/dynamic-window.vue');
/* tslint:disable */
// prettier-ignore
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
  },
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
      }
    ]
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  
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
export const asyncRoutes: RouteConfig[] = [];

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
