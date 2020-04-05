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
const DocumentTypeBusinessCategory = () => import('../entities/document-type-business-category/document-type-business-category.vue');
// prettier-ignore
const DocumentTypeBusinessCategoryUpdate = () => import('../entities/document-type-business-category/document-type-business-category-update.vue');
// prettier-ignore
const DocumentTypeBusinessCategoryDetails = () => import('../entities/document-type-business-category/document-type-business-category-details.vue');
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
// prettier-ignore
const Permission = () => import('../entities/permission/permission.vue');
// prettier-ignore
const PermissionUpdate = () => import('../entities/permission/permission-update.vue');
// prettier-ignore
const PermissionDetails = () => import('../entities/permission/permission-details.vue');
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
    }
  },
  {
    path: '/not-found',
    component: Error,
    meta: {
      hidden: true,
      error403: true
    }
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
      }
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
  },
  {
    path: '/country',
    component: Layout,
    redirect: '/country/list',
    meta: {
      title: 'country.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: '/list',
        name: 'Country',
        component: Country,
        meta: { roles: [Authority.ADMIN] }
      },
      {
        path: '/country/new',
        name: 'CountryCreate',
        component: CountryUpdate,
        meta: { roles: [Authority.ADMIN] }
      },
      {
        path: '/country/:countryId/edit',
        name: 'CountryEdit',
        component: CountryUpdate,
        meta: { roles: [Authority.ADMIN] }
      },
      {
        path: '/country/:countryId/view',
        name: 'CountryView',
        component: CountryDetails,
        meta: { roles: [Authority.ADMIN] }
      }
    ]
  },
  {
    path: '/currency',
    component: Layout,
    redirect: '/currency/list',
    meta: {
      title: 'currency.default',
      roles: [Authority.ADMIN]
    }
  },
  {
    path: '/currency',
    name: 'Currency',
    component: Currency,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/currency/new',
    name: 'CurrencyCreate',
    component: CurrencyUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/currency/:currencyId/edit',
    name: 'CurrencyEdit',
    component: CurrencyUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/currency/:currencyId/view',
    name: 'CurrencyView',
    component: CurrencyDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/location',
    name: 'Location',
    component: Location,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/location/new',
    name: 'LocationCreate',
    component: LocationUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/location/:locationId/edit',
    name: 'LocationEdit',
    component: LocationUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/location/:locationId/view',
    name: 'LocationView',
    component: LocationDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/vendor',
    name: 'Vendor',
    component: Vendor,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/vendor/new',
    name: 'VendorCreate',
    component: VendorUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/vendor/:vendorId/edit',
    name: 'VendorEdit',
    component: VendorUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/vendor/:vendorId/view',
    name: 'VendorView',
    component: VendorDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/business-category',
    name: 'BusinessCategory',
    component: BusinessCategory,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/business-category/new',
    name: 'BusinessCategoryCreate',
    component: BusinessCategoryUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/business-category/:businessCategoryId/edit',
    name: 'BusinessCategoryEdit',
    component: BusinessCategoryUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/business-category/:businessCategoryId/view',
    name: 'BusinessCategoryView',
    component: BusinessCategoryDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/supporting-document',
    name: 'SupportingDocument',
    component: SupportingDocument,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/supporting-document/new',
    name: 'SupportingDocumentCreate',
    component: SupportingDocumentUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/supporting-document/:supportingDocumentId/edit',
    name: 'SupportingDocumentEdit',
    component: SupportingDocumentUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/supporting-document/:supportingDocumentId/view',
    name: 'SupportingDocumentView',
    component: SupportingDocumentDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/document-type',
    component: Layout,
    redirect: '/document-type/list',
    meta: {
      title: 'document-type.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: '/list',
        name: 'DocumentType',
        component: DocumentType,
        meta: {
          title: 'document-type.default',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/new',
        name: 'DocumentTypeCreate',
        component: DocumentTypeUpdate,
        meta: {
          title: 'document-type.create',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/:documentTypeId/edit',
        name: 'DocumentTypeEdit',
        component: DocumentTypeUpdate,
        meta: {
          title: 'document-type.edit',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: '/:documentTypeId/view',
        name: 'DocumentTypeView',
        component: DocumentTypeDetails,
        meta: {
          title: 'document-type.detail',
          roles: [Authority.ADMIN]
        }
      }
    ]
  },
  {
    path: '/person-in-charge',
    name: 'PersonInCharge',
    component: PersonInCharge,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/person-in-charge/new',
    name: 'PersonInChargeCreate',
    component: PersonInChargeUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/person-in-charge/:personInChargeId/edit',
    name: 'PersonInChargeEdit',
    component: PersonInChargeUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/person-in-charge/:personInChargeId/view',
    name: 'PersonInChargeView',
    component: PersonInChargeDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/company-functionary',
    name: 'CompanyFunctionary',
    component: CompanyFunctionary,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/company-functionary/new',
    name: 'CompanyFunctionaryCreate',
    component: CompanyFunctionaryUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/company-functionary/:companyFunctionaryId/edit',
    name: 'CompanyFunctionaryEdit',
    component: CompanyFunctionaryUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/company-functionary/:companyFunctionaryId/view',
    name: 'CompanyFunctionaryView',
    component: CompanyFunctionaryDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference',
    name: 'Reference',
    component: Reference,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference/new',
    name: 'ReferenceCreate',
    component: ReferenceUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference/:referenceId/edit',
    name: 'ReferenceEdit',
    component: ReferenceUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference/:referenceId/view',
    name: 'ReferenceView',
    component: ReferenceDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference-list',
    name: 'ReferenceList',
    component: ReferenceList,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference-list/new',
    name: 'ReferenceListCreate',
    component: ReferenceListUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference-list/:referenceListId/edit',
    name: 'ReferenceListEdit',
    component: ReferenceListUpdate,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/reference-list/:referenceListId/view',
    name: 'ReferenceListView',
    component: ReferenceListDetails,
    meta: { roles: [Authority.USER] }
  },
  {
    path: '/country',
    name: 'Country',
    component: Country,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/country/new',
    name: 'CountryCreate',
    component: CountryUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/country/:countryId/edit',
    name: 'CountryEdit',
    component: CountryUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/country/:countryId/view',
    name: 'CountryView',
    component: CountryDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/region',
    name: 'Region',
    component: Region,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/region/new',
    name: 'RegionCreate',
    component: RegionUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/region/:regionId/edit',
    name: 'RegionEdit',
    component: RegionUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/region/:regionId/view',
    name: 'RegionView',
    component: RegionDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/city',
    name: 'City',
    component: City,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/city/new',
    name: 'CityCreate',
    component: CityUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/city/:cityId/edit',
    name: 'CityEdit',
    component: CityUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/city/:cityId/view',
    name: 'CityView',
    component: CityDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/location',
    name: 'Location',
    component: Location,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/location/new',
    name: 'LocationCreate',
    component: LocationUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/location/:locationId/edit',
    name: 'LocationEdit',
    component: LocationUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/location/:locationId/view',
    name: 'LocationView',
    component: LocationDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/vendor',
    name: 'Vendor',
    component: Vendor,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/vendor/new',
    name: 'VendorCreate',
    component: VendorUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/vendor/:vendorId/edit',
    name: 'VendorEdit',
    component: VendorUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/vendor/:vendorId/view',
    name: 'VendorView',
    component: VendorDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/permission',
    name: 'Permission',
    component: Permission,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/permission/new',
    name: 'PermissionCreate',
    component: PermissionUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/permission/:permissionId/edit',
    name: 'PermissionEdit',
    component: PermissionUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/permission/:permissionId/view',
    name: 'PermissionView',
    component: PermissionDetails,
    meta: { authorities: [Authority.USER] }
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
//   ]
// });
