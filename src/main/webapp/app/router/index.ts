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
// prettier-ignore
const Country = () => import('../entities/country/country.vue');
// prettier-ignore
const CountryUpdate = () => import('../entities/country/country-update.vue');
// prettier-ignore
const CountryDetails = () => import('../entities/country/country-details.vue');
// prettier-ignore
const Currency = () => import('../entities/currency/currency.vue');
// prettier-ignore
const CurrencyUpdate = () => import('../entities/currency/currency-update.vue');
// prettier-ignore
const CurrencyDetails = () => import('../entities/currency/currency-details.vue');
// prettier-ignore
const Location = () => import('../entities/location/location.vue');
// prettier-ignore
const LocationUpdate = () => import('../entities/location/location-update.vue');
// prettier-ignore
const LocationDetails = () => import('../entities/location/location-details.vue');
// prettier-ignore
const Vendor = () => import('../entities/vendor/vendor.vue');
// prettier-ignore
const VendorUpdate = () => import('../entities/vendor/vendor-update.vue');
// prettier-ignore
const VendorDetails = () => import('../entities/vendor/vendor-details.vue');
// prettier-ignore
const BusinessCategory = () => import('../entities/business-category/business-category.vue');
// prettier-ignore
const BusinessCategoryUpdate = () => import('../entities/business-category/business-category-update.vue');
// prettier-ignore
const BusinessCategoryDetails = () => import('../entities/business-category/business-category-details.vue');
// prettier-ignore
const SupportingDocument = () => import('../entities/supporting-document/supporting-document.vue');
// prettier-ignore
const SupportingDocumentUpdate = () => import('../entities/supporting-document/supporting-document-update.vue');
// prettier-ignore
const SupportingDocumentDetails = () => import('../entities/supporting-document/supporting-document-details.vue');
// prettier-ignore
const DocumentType = () => import('../entities/document-type/document-type.vue');
// prettier-ignore
const DocumentTypeUpdate = () => import('../entities/document-type/document-type-update.vue');
// prettier-ignore
const DocumentTypeDetails = () => import('../entities/document-type/document-type-details.vue');
// prettier-ignore
const PersonInCharge = () => import('../entities/person-in-charge/person-in-charge.vue');
// prettier-ignore
const PersonInChargeUpdate = () => import('../entities/person-in-charge/person-in-charge-update.vue');
// prettier-ignore
const PersonInChargeDetails = () => import('../entities/person-in-charge/person-in-charge-details.vue');
// prettier-ignore
const CompanyFunctionary = () => import('../entities/company-functionary/company-functionary.vue');
// prettier-ignore
const CompanyFunctionaryUpdate = () => import('../entities/company-functionary/company-functionary-update.vue');
// prettier-ignore
const CompanyFunctionaryDetails = () => import('../entities/company-functionary/company-functionary-details.vue');
// prettier-ignore
const Reference = () => import('../entities/reference/reference.vue');
// prettier-ignore
const ReferenceUpdate = () => import('../entities/reference/reference-update.vue');
// prettier-ignore
const ReferenceDetails = () => import('../entities/reference/reference-details.vue');
// prettier-ignore
const ReferenceList = () => import('../entities/reference-list/reference-list.vue');
// prettier-ignore
const ReferenceListUpdate = () => import('../entities/reference-list/reference-list-update.vue');
// prettier-ignore
const ReferenceListDetails = () => import('../entities/reference-list/reference-list-details.vue');
// prettier-ignore
const Region = () => import('../entities/region/region.vue');
// prettier-ignore
const RegionUpdate = () => import('../entities/region/region-update.vue');
// prettier-ignore
const RegionDetails = () => import('../entities/region/region-details.vue');
// prettier-ignore
const City = () => import('../entities/city/city.vue');
// prettier-ignore
const CityUpdate = () => import('../entities/city/city-update.vue');
// prettier-ignore
const CityDetails = () => import('../entities/city/city-details.vue');
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
        path: '/',
        component: () => import(/* webpackChunkName: "home" */ '@/core/home/home.vue'),
        meta: {
          title: 'home',
          breadcrumb: false
        }
      }
    ]
  },
  {
    path: '/forbidden',
    component: () => import(/* webpackChunkName: "error" */ '@/core/error/401.vue'),
    redirect: 'noredirect',
    meta: {
      hidden: true
    }
  },
  {
    path: '/not-found',
    component: () => import(/* webpackChunkName: "error" */ '@/core/error/404.vue'),
    redirect: 'noredirect',
    meta: {
      hidden: true
    }
  },
  {
    path: '/register',
    component: Layout,
    redirect: 'noredirect',
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
        path: '/account/password',
        name: 'ChangePassword',
        component: ChangePassword,
        meta: {
          title: 'changePassword',
          authorities: [Authority.USER]
        }
      },
      {
        path: '/account/settings',
        name: 'Settings',
        component: Settings,
        meta: {
          title: 'settings',
          authorities: [Authority.USER]
        }
      }
    ]
  },
  {
    path: '/account/activate',
    component: Activate,
    redirect: 'noredirect',
    meta: {
      hidden: true
    }
  },
  {
    path: '/account/reset/request',
    component: ResetPasswordInit,
    redirect: 'noredirect',
    meta: {
      hidden: true
    }
  },
  {
    path: '/account/reset/finish',
    component: ResetPasswordFinish,
    redirect: 'noredirect',
    meta: {
      hidden: true
    }
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
        path: '/admin/user-management',
        name: 'JhiUserManagementComponent',
        component: JhiUserManagementComponent,
        meta: {
          title: 'admin.userManagement',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/new',
        name: 'JhiUserManagementNew',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/:userId/edit',
        name: 'JhiUserManagementEditComponent',
        component: JhiUserManagementEditComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/user-management/:userId/view',
        name: 'JhiUserManagementViewComponent',
        component: JhiUserManagementViewComponent,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/docs',
        name: 'JhiDocsComponent',
        component: JhiDocsComponent,
        meta: {
          title: 'admin.api',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/audits',
        name: 'JhiAuditsComponent',
        component: JhiAuditsComponent,
        meta: {
          title: 'admin.audits',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-health',
        name: 'JhiHealthComponent',
        component: JhiHealthComponent,
        meta: {
          title: 'admin.health',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/logs',
        name: 'JhiLogsComponent',
        component: JhiLogsComponent,
        meta: {
          title: 'admin.logs',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-metrics',
        name: 'JhiMetricsComponent',
        component: JhiMetricsComponent,
        meta: {
          title: 'admin.metrics',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-configuration',
        name: 'JhiConfigurationComponent',
        component: JhiConfigurationComponent,
        meta: {
          title: 'admin.config',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/admin/jhi-tracker',
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
    path: '/country',
    //name: 'Country',
    component: Layout,
    redirect: '/country/list',
    meta: {
      breadcrumb: true,
      title: 'country.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: '/country/list',
        name: 'Country',
        component: Country,
        meta: {
          title: 'country.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/country/new',
        name: 'CountryNew',
        component: CountryUpdate,
        meta: {
          affix: false,
          title: 'country.add',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/country/:countryId/edit',
        name: 'CountryUpdate',
        component: CountryUpdate,
        meta: {
          affix: false,
          title: 'country.edit',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/country/:countryId/view',
        name: 'CountryView',
        component: CountryDetails,
        meta: {
          affix: false,
          title: 'country.view',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      }
    ]
  },
  {
    path: '/currency',
    //name: 'Currency',
    component: Layout,
    redirect: '/currency/list',
    meta: {
      breadcrumb: true,
      title: 'currency.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: '/currency/list',
        name: 'Currency',
        component: Currency,
        meta: {
          title: 'currency.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/currency/new',
        name: 'CurrencyNew',
        component: CurrencyUpdate,
        meta: {
          title: 'currency.add',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/currency/:currencyId/edit',
        name: 'CurrencyUpdate',
        component: CurrencyUpdate,
        meta: {
          title: 'currency.edit',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/currency/:currencyId/view',
        name: 'CurrencyView',
        component: CurrencyDetails,
        meta: {
          title: 'currency.view',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      }
    ]
  },
  // {
  //   path: '/location',
  //   name: 'Location',
  //   component: Location,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/location/new',
  //   name: 'LocationCreate',
  //   component: LocationUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/location/:locationId/edit',
  //   name: 'LocationEdit',
  //   component: LocationUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/location/:locationId/view',
  //   name: 'LocationView',
  //   component: LocationDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/vendor',
  //   name: 'Vendor',
  //   component: Vendor,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/vendor/new',
  //   name: 'VendorCreate',
  //   component: VendorUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/vendor/:vendorId/edit',
  //   name: 'VendorEdit',
  //   component: VendorUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/vendor/:vendorId/view',
  //   name: 'VendorView',
  //   component: VendorDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  {
    path: '/business-category',
    component: Layout,
    redirect: '/business-category/list',
    meta: {
      breadcrumb: false,
      title: 'business-category.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: '/business-category/list',
        name: 'BusinessCategory',
        component: BusinessCategory,
        meta: {
          title: 'business-category.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/business-category/new',
        component: BusinessCategoryUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/business-category/:businessCategoryId/edit',
        component: BusinessCategoryUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/business-category/:businessCategoryId/view',
        component: BusinessCategoryDetails,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      }
    ]
  },
  {
    path: '/supporting-document',
    component: Layout,
    redirect: '/supporting-document/list',
    meta: {
      title: 'supporting-document.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: '/supporting-document/list',
        name: 'SupportingDocument',
        component: SupportingDocument,
        meta: {
          title: 'supporting-document.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/supporting-document/new',
        name: 'SupportingDocumentNew',
        component: SupportingDocumentUpdate,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/supporting-document/:supportingDocumentId/edit',
        name: 'SupportingDocumentUpdate',
        component: SupportingDocumentUpdate,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/supporting-document/:supportingDocumentId/view',
        name: 'SupportingDocumentDetails',
        component: SupportingDocumentDetails,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      }
    ]
  },
  {
    path: '/document-type',
    component: Layout,
    redirect: '/document-type/list',
    meta: {
      breadcrumb: false,
      title: 'document-type.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: '/document-type/list',
        name: 'DocumentType',
        component: DocumentType,
        meta: {
          title: 'document-type.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/document-type/new',
        component: DocumentTypeUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/document-type/:documentTypeId/edit',
        component: DocumentTypeUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: '/document-type/:documentTypeId/view',
        component: DocumentTypeDetails,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      }
    ]
  },
  // {
  //   path: '/person-in-charge',
  //   name: 'PersonInCharge',
  //   component: PersonInCharge,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/person-in-charge/new',
  //   name: 'PersonInChargeCreate',
  //   component: PersonInChargeUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/person-in-charge/:personInChargeId/edit',
  //   name: 'PersonInChargeEdit',
  //   component: PersonInChargeUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/person-in-charge/:personInChargeId/view',
  //   name: 'PersonInChargeView',
  //   component: PersonInChargeDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/company-functionary',
  //   name: 'CompanyFunctionary',
  //   component: CompanyFunctionary,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/company-functionary/new',
  //   name: 'CompanyFunctionaryCreate',
  //   component: CompanyFunctionaryUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/company-functionary/:companyFunctionaryId/edit',
  //   name: 'CompanyFunctionaryEdit',
  //   component: CompanyFunctionaryUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/company-functionary/:companyFunctionaryId/view',
  //   name: 'CompanyFunctionaryView',
  //   component: CompanyFunctionaryDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference',
  //   name: 'Reference',
  //   component: Reference,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference/new',
  //   name: 'ReferenceCreate',
  //   component: ReferenceUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference/:referenceId/edit',
  //   name: 'ReferenceEdit',
  //   component: ReferenceUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference/:referenceId/view',
  //   name: 'ReferenceView',
  //   component: ReferenceDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference-list',
  //   name: 'ReferenceList',
  //   component: ReferenceList,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference-list/new',
  //   name: 'ReferenceListCreate',
  //   component: ReferenceListUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference-list/:referenceListId/edit',
  //   name: 'ReferenceListEdit',
  //   component: ReferenceListUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/reference-list/:referenceListId/view',
  //   name: 'ReferenceListView',
  //   component: ReferenceListDetails,
  //   meta: { authorities: [Authority.USER] }
  // },
  {
    path: '/region',
    //name: 'Region',
    component: Layout,
    redirect: '/region/list',
    meta: {
      breadcrumb: true,
      title: 'region.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: '/region/list',
        name: 'Region',
        component: Region,
        meta: {
          title: 'region.list',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/region/new',
        name: 'RegionNew',
        component: RegionUpdate,
        meta: {
          affix: false,
          title: 'region.add',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/region/:regionId/edit',
        name: 'RegionUpdate',
        component: RegionUpdate,
        meta: {
          affix: false,
          title: 'region.edit',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/region/:regionId/view',
        name: 'RegionDetails',
        component: RegionDetails,
        meta: {
          affix: false,
          title: 'region.view',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      }
    ]
  },

  {
    path: '/city',
    //name: 'Region',
    component: Layout,
    redirect: '/city/list',
    meta: {
      breadcrumb: true,
      title: 'city.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: '/city/list',
        name: 'City',
        component: City,
        meta: {
          title: 'city.list',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/city/new',
        name: 'CityNew',
        component: CityUpdate,
        meta: {
          affix: false,
          title: 'city.add',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/city/:cityId/edit',
        name: 'CityUpdate',
        component: CityUpdate,
        meta: {
          affix: false,
          title: 'city.edit',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/city/:cityId/view',
        name: 'CityDetails',
        component: CityDetails,
        meta: {
          affix: false,
          title: 'city.view',
          hidden: true,
          roles: [Authority.ADMIN]
        }
      }
    ]
  }

  // {
  //   path: '/permission',
  //   name: 'Permission',
  //   component: Permission,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/permission/new',
  //   name: 'PermissionCreate',
  //   component: PermissionUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/permission/:permissionId/edit',
  //   name: 'PermissionEdit',
  //   component: PermissionUpdate,
  //   meta: { authorities: [Authority.USER] }
  // },
  // {
  //   path: '/permission/:permissionId/view',
  //   name: 'PermissionView',
  //   component: PermissionDetails,
  //   meta: { authorities: [Authority.USER] }
  // }
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
//       name: 'JhiUserManagementNew',
//       component: JhiUserManagementEditComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/user-management/:userId/edit',
//       name: 'JhiUserManagementEditComponent',
//       component: JhiUserManagementEditComponent,
//       meta: { authorities: [Authority.ADMIN] }
//     },
//     {
//       path: '/admin/user-management/:userId/view',
//       name: 'JhiUserManagementViewComponent',
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
//   ]
// });
