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
// prettier-ignore
const CVendor = () => import('../entities/c-vendor/c-vendor.vue');
// prettier-ignore
const CVendorUpdate = () => import('../entities/c-vendor/c-vendor-update.vue');
// prettier-ignore
const CVendorDetails = () => import('../entities/c-vendor/c-vendor-details.vue');
// prettier-ignore
const CBusinessCategory = () => import('../entities/c-business-category/c-business-category.vue');
// prettier-ignore
const CBusinessCategoryUpdate = () => import('../entities/c-business-category/c-business-category-update.vue');
// prettier-ignore
const CBusinessCategoryDetails = () => import('../entities/c-business-category/c-business-category-details.vue');
// prettier-ignore
const CSupportingDocument = () => import('../entities/c-supporting-document/c-supporting-document.vue');
// prettier-ignore
const CSupportingDocumentUpdate = () => import('../entities/c-supporting-document/c-supporting-document-update.vue');
// prettier-ignore
const CSupportingDocumentDetails = () => import('../entities/c-supporting-document/c-supporting-document-details.vue');
// prettier-ignore
const CDocumentType = () => import('../entities/c-document-type/c-document-type.vue');
// prettier-ignore
const CDocumentTypeUpdate = () => import('../entities/c-document-type/c-document-type-update.vue');
// prettier-ignore
const CDocumentTypeDetails = () => import('../entities/c-document-type/c-document-type-details.vue');
// prettier-ignore
const CDocumentTypeBusinessCategory = () => import('../entities/c-document-type-business-category/c-document-type-business-category.vue');
// prettier-ignore
const CDocumentTypeBusinessCategoryUpdate = () => import('../entities/c-document-type-business-category/c-document-type-business-category-update.vue');
// prettier-ignore
const CDocumentTypeBusinessCategoryDetails = () => import('../entities/c-document-type-business-category/c-document-type-business-category-details.vue');
// prettier-ignore
const CPersonInCharge = () => import('../entities/c-person-in-charge/c-person-in-charge.vue');
// prettier-ignore
const CPersonInChargeUpdate = () => import('../entities/c-person-in-charge/c-person-in-charge-update.vue');
// prettier-ignore
const CPersonInChargeDetails = () => import('../entities/c-person-in-charge/c-person-in-charge-details.vue');
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
const ADPermission = () => import('../entities/ad-permission/ad-permission.vue');
// prettier-ignore
const ADPermissionUpdate = () => import('../entities/ad-permission/ad-permission-update.vue');
// prettier-ignore
const ADPermissionDetails = () => import('../entities/ad-permission/ad-permission-details.vue');
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
  },
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
  }
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ,
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
