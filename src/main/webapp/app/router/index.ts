import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';
import Layout from '@/layout/index.vue'
import { Authority } from '@/shared/security/authority';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/steps-form.vue');
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
/* tslint:disable */
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
    roles: ['admin', 'editor']   will control the page roles (allow setting multiple roles)
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
  all roles can be accessed
*/
export const constantRoutes: RouteConfig[] = [
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '/',
        component: Home,
        meta: {
          title: 'home',
          affix: false,
          breadcrumb: false
        }
      }
    ]
  },
  {
    path: '/forbidden',
    component: Error,
    meta: {
      hidden: true,
      error403: true
    },
  },
  {
    path: '/not-found',
    component: Error,
    meta: {
      hidden: true,
      error403: true
    },
  },
  {
    path: '/register',
    component: Register,
    meta: {
      hidden: true
    }
  },
  {
    path: '/account',
    component: Layout,
    meta: {
      title: 'myAccount'
    },
    children: [
      {
        path: '/account/password',
        name: 'ChangePassword',
        component: ChangePassword,
        meta: {
          title: 'changePassword',
          roles: [Authority.USER]
        }
      },
      {
        path: '/account/settings',
        name: 'Settings',
        component: Settings,
        meta: {
          title: 'settings',
          roles: [Authority.USER]
        }
      },
    ]
  },
  {
    path: '/account/activate',
    component: Activate,
    meta: {
      hidden: true
    }
  },
  {
    path: '/account/reset/request',
    component: ResetPasswordInit,
    meta: {
      hidden: true
    }
  },
  {
    path: '/account/reset/finish',
    component: ResetPasswordFinish,
    meta: {
      hidden: true
    }
  }
];

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
*/
export const asyncRoutes: RouteConfig[] = [
  {
    path: '/admin',
    component: Layout,
    meta: {
      title: 'admin.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: '/admin/user-management',
        name: 'JhiUserManagementComponent',
        component: JhiUserManagementComponent,
        meta: {
          title: 'admin.userManagement',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/new',
        name: 'JhiUserManagementEditComponent',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/:userId/edit',
        name: 'JhiUserManagementEditComponent',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/:userId/view',
        name: 'JhiUserManagementViewComponent',
        component: JhiUserManagementViewComponent,
        meta: {
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/docs',
        name: 'JhiDocsComponent',
        component: JhiDocsComponent,
        meta: {
          title: 'admin.api',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/audits',
        name: 'JhiAuditsComponent',
        component: JhiAuditsComponent,
        meta: {
          title: 'admin.audits',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-health',
        name: 'JhiHealthComponent',
        component: JhiHealthComponent,
        meta: {
          title: 'admin.health',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/logs',
        name: 'JhiLogsComponent',
        component: JhiLogsComponent,
        meta: {
          title: 'admin.logs',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-metrics',
        name: 'JhiMetricsComponent',
        component: JhiMetricsComponent,
        meta: {
          title: 'admin.metrics',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-configuration',
        name: 'JhiConfigurationComponent',
        component: JhiConfigurationComponent,
        meta: {
          title: 'admin.config',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-tracker',
        name: 'JhiTrackerComponent',
        component: JhiTrackerComponent,
        meta: {
          title: 'admin.tracker',
          roles: [Authority.ADMIN]
        }
      }
    ]
  }
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];

// prettier-ignore
const createRouter = () => new Router({
  mode: 'history',
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

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  (router as any).matcher = (newRouter as any).matcher // reset router
}

export default router


// export default new Router({
//   mode: 'history',
//   routes: [
//     {
//       path: '/',
//       name: 'Home',
//       component: Home
//     },
//     {
//       path: '/forbidden',
//       name: 'Forbidden',
//       component: Error,
//       meta: { error403: true }
//     },
//     {
//       path: '/not-found',
//       name: 'NotFound',
//       component: Error,
//       meta: { error404: true }
//     },
//     {
//       path: '/register',
//       name: 'Register',
//       component: Register
//     },
//     {
//       path: '/account/activate',
//       name: 'Activate',
//       component: Activate
//     },
//     {
//       path: '/account/reset/request',
//       name: 'ResetPasswordInit',
//       component: ResetPasswordInit
//     },
//     {
//       path: '/account/reset/finish',
//       name: 'ResetPasswordFinish',
//       component: ResetPasswordFinish
//     },
//     {
//       path: '/account/password',
//       name: 'ChangePassword',
//       component: ChangePassword,
//       meta: { authorities: [Authority.USER] }
//     },
//     {
//       path: '/account/settings',
//       name: 'Settings',
//       component: Settings,
//       meta: { authorities: [Authority.USER] }
//     },
//     {
//       path: '/admin/user-management',
//       name: 'JhiUser',
//       component: JhiUserManagementComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/user-management/new',
//       name: 'JhiUserCreate',
//       component: JhiUserManagementEditComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/user-management/:userId/edit',
//       name: 'JhiUserEdit',
//       component: JhiUserManagementEditComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/user-management/:userId/view',
//       name: 'JhiUserView',
//       component: JhiUserManagementViewComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/docs',
//       name: 'JhiDocsComponent',
//       component: JhiDocsComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/audits',
//       name: 'JhiAuditsComponent',
//       component: JhiAuditsComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/jhi-health',
//       name: 'JhiHealthComponent',
//       component: JhiHealthComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/logs',
//       name: 'JhiLogsComponent',
//       component: JhiLogsComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/jhi-metrics',
//       name: 'JhiMetricsComponent',
//       component: JhiMetricsComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/jhi-configuration',
//       name: 'JhiConfigurationComponent',
//       component: JhiConfigurationComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     }
// ,
//     {
//       path: '/admin/jhi-tracker',
//       name: 'JhiTrackerComponent',
//       component: JhiTrackerComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     }
//     // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
//   ]
// });
