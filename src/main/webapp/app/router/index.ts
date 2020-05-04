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
// prettier-ignore
const ADReference = () => import('../entities/ad-reference/ad-reference.vue');
// prettier-ignore
const ADReferenceUpdate = () => import('../entities/ad-reference/ad-reference-update.vue');
// prettier-ignore
const ADReferenceDetails = () => import('../entities/ad-reference/ad-reference-details.vue');
// prettier-ignore
const ADReferenceList = () => import('../entities/ad-reference-list/ad-reference-list.vue');
// prettier-ignore
const ADReferenceListUpdate = () => import('../entities/ad-reference-list/ad-reference-list-update.vue');
// prettier-ignore
const ADReferenceListDetails = () => import('../entities/ad-reference-list/ad-reference-list-details.vue');
// prettier-ignore
const ADTable = () => import('../entities/ad-table/ad-table.vue');
// prettier-ignore
const ADTableUpdate = () => import('../entities/ad-table/ad-table-update.vue');
// prettier-ignore
const ADTableDetails = () => import('../entities/ad-table/ad-table-details.vue');
// prettier-ignore
const ADColumn = () => import('../entities/ad-column/ad-column.vue');
// prettier-ignore
const ADColumnUpdate = () => import('../entities/ad-column/ad-column-update.vue');
// prettier-ignore
const ADColumnDetails = () => import('../entities/ad-column/ad-column-details.vue');
// prettier-ignore
const ADWindow = () => import('../entities/ad-window/ad-window.vue');
// prettier-ignore
const ADWindowUpdate = () => import('../entities/ad-window/ad-window-update.vue');
// prettier-ignore
const ADWindowDetails = () => import('../entities/ad-window/ad-window-details.vue');
// prettier-ignore
const ADTab = () => import('../entities/ad-tab/ad-tab.vue');
// prettier-ignore
const ADTabUpdate = () => import('../entities/ad-tab/ad-tab-update.vue');
// prettier-ignore
const ADTabDetails = () => import('../entities/ad-tab/ad-tab-details.vue');
// prettier-ignore
const ADField = () => import('../entities/ad-field/ad-field.vue');
// prettier-ignore
const ADFieldUpdate = () => import('../entities/ad-field/ad-field-update.vue');
// prettier-ignore
const ADFieldDetails = () => import('../entities/ad-field/ad-field-details.vue');
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
          baseApiUrl: '/api/ad-tables',
          schemaId: 'table'
        }
      },
      {
        path: 'window',
        name: 'WindowEditor',
        component: DynamicWindow,
        meta: {
          title: 'applicationDictionary.window',
          authorities: [Authority.ADMIN],
          baseApiUrl: '/api/ad-windows',
          schemaId: 'window'
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
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'list',
        name: 'Country',
        component: Country,
        meta: {
          title: 'country.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
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
        path: ':countryId/edit',
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
        path: ':countryId/view',
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
    component: Layout,
    redirect: '/currency/list',
    meta: {
      title: 'currency.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'list',
        name: 'Currency',
        component: Currency,
        meta: {
          title: 'currency.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
        name: 'CurrencyNew',
        component: CurrencyUpdate,
        meta: {
          title: 'currency.add',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':currencyId/edit',
        name: 'CurrencyUpdate',
        component: CurrencyUpdate,
        meta: {
          title: 'currency.edit',
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':currencyId/view',
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
        path: 'list',
        name: 'BusinessCategory',
        component: BusinessCategory,
        meta: {
          title: 'business-category.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
        component: BusinessCategoryUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':businessCategoryId/edit',
        component: BusinessCategoryUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':businessCategoryId/view',
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
        path: 'list',
        name: 'SupportingDocument',
        component: SupportingDocument,
        meta: {
          title: 'supporting-document.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
        name: 'SupportingDocumentNew',
        component: SupportingDocumentUpdate,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':supportingDocumentId/edit',
        name: 'SupportingDocumentUpdate',
        component: SupportingDocumentUpdate,
        meta: {
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':supportingDocumentId/view',
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
      title: 'document-type.default',
      authorities: [Authority.ADMIN]
    },
    children: [
      {
        path: 'list',
        name: 'DocumentType',
        component: DocumentType,
        meta: {
          title: 'document-type.list',
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
        component: DocumentTypeUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':documentTypeId/edit',
        component: DocumentTypeUpdate,
        meta: {
          affix: false,
          hidden: true,
          authorities: [Authority.ADMIN]
        }
      },
      {
        path: ':documentTypeId/view',
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
  {
    path: '/region',
    //name: 'Region',
    component: Layout,
    redirect: 'list',
    meta: {
      breadcrumb: true,
      title: 'region.default',
      roles: [Authority.ADMIN]
    },
    children: [
      {
        path: 'list',
        name: 'Region',
        component: Region,
        meta: {
          title: 'region.list',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
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
        path: ':regionId/edit',
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
        path: ':regionId/view',
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
        path: 'list',
        name: 'City',
        component: City,
        meta: {
          title: 'city.list',
          roles: [Authority.ADMIN]
        }
      },
      {
        path: 'new',
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
        path: ':cityId/edit',
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
        path: ':cityId/view',
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
  }/* ,
  {
    path: '/ad-client',
    name: 'ADClient',
    component: ADClient,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-client/new',
    name: 'ADClientCreate',
    component: ADClientUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-client/:aDClientId/edit',
    name: 'ADClientEdit',
    component: ADClientUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-client/:aDClientId/view',
    name: 'ADClientView',
    component: ADClientDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-organization',
    name: 'ADOrganization',
    component: ADOrganization,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-organization/new',
    name: 'ADOrganizationCreate',
    component: ADOrganizationUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-organization/:aDOrganizationId/edit',
    name: 'ADOrganizationEdit',
    component: ADOrganizationUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-organization/:aDOrganizationId/view',
    name: 'ADOrganizationView',
    component: ADOrganizationDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-table',
    name: 'ADTable',
    component: ADTable,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-table/new',
    name: 'ADTableCreate',
    component: ADTableUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-table/:aDTableId/edit',
    name: 'ADTableEdit',
    component: ADTableUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-table/:aDTableId/view',
    name: 'ADTableView',
    component: ADTableDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-column',
    name: 'ADColumn',
    component: ADColumn,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-column/new',
    name: 'ADColumnCreate',
    component: ADColumnUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-column/:aDColumnId/edit',
    name: 'ADColumnEdit',
    component: ADColumnUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-column/:aDColumnId/view',
    name: 'ADColumnView',
    component: ADColumnDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-window',
    name: 'ADWindow',
    component: ADWindow,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-window/new',
    name: 'ADWindowCreate',
    component: ADWindowUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-window/:aDWindowId/edit',
    name: 'ADWindowEdit',
    component: ADWindowUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-window/:aDWindowId/view',
    name: 'ADWindowView',
    component: ADWindowDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-tab',
    name: 'ADTab',
    component: ADTab,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-tab/new',
    name: 'ADTabCreate',
    component: ADTabUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-tab/:aDTabId/edit',
    name: 'ADTabEdit',
    component: ADTabUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-tab/:aDTabId/view',
    name: 'ADTabView',
    component: ADTabDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-field',
    name: 'ADField',
    component: ADField,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-field/new',
    name: 'ADFieldCreate',
    component: ADFieldUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-field/:aDFieldId/edit',
    name: 'ADFieldEdit',
    component: ADFieldUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-field/:aDFieldId/view',
    name: 'ADFieldView',
    component: ADFieldDetails,
    meta: { authorities: [Authority.USER] }
  },
  
  {
    path: '/c-bank',
    name: 'CBank',
    component: CBank,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/c-bank/new',
    name: 'CBankCreate',
    component: CBankUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/c-bank/:cBankId/edit',
    name: 'CBankEdit',
    component: CBankUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/c-bank/:cBankId/view',
    name: 'CBankView',
    component: CBankDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference/new',
    name: 'ADReferenceCreate',
    component: ADReferenceUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference/:aDReferenceId/edit',
    name: 'ADReferenceEdit',
    component: ADReferenceUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference/:aDReferenceId/view',
    name: 'ADReferenceView',
    component: ADReferenceDetails,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference-list',
    name: 'ADReferenceList',
    component: ADReferenceList,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference-list/new',
    name: 'ADReferenceListCreate',
    component: ADReferenceListUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference-list/:aDReferenceListId/edit',
    name: 'ADReferenceListEdit',
    component: ADReferenceListUpdate,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/ad-reference-list/:aDReferenceListId/view',
    name: 'ADReferenceListView',
    component: ADReferenceListDetails,
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
  } */
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  
  ,{
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
