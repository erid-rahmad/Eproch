// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue, { DirectiveOptions } from 'vue';
import 'normalize.css';
import ElementUI from 'element-ui';
import SvgIcon from 'vue-svgicon';
import VueShortkey from "vue-shortkey";

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import store from '@/shared/config/store';
import { AppStoreModule as appStore } from '@/shared/config/store/app-store';
import * as directives from '@/shared/directives';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import '@/styles/element-variables.scss';
import '@/styles/index.scss';
import '@/assets/icons/components';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import TrackerService from './admin/tracker/tracker.service';
/* tslint:disable */

import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import CountryService from '@/entities/country/country.service';
import CurrencyService from '@/entities/currency/currency.service';
import LocationService from '@/entities/location/location.service';
import VendorService from '@/entities/vendor/vendor.service';
import BusinessCategoryService from '@/entities/business-category/business-category.service';
import SupportingDocumentService from '@/entities/supporting-document/supporting-document.service';
import DocumentTypeService from '@/entities/document-type/document-type.service';
import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';
import PersonInChargeService from '@/entities/person-in-charge/person-in-charge.service';
import CompanyFunctionaryService from '@/entities/company-functionary/company-functionary.service';
import ReferenceService from '@/entities/reference/reference.service';
import ReferenceListService from '@/entities/reference-list/reference-list.service';
import RegionService from '@/entities/region/region.service';
import CityService from '@/entities/city/city.service';
import PermissionService from '@/entities/permission/permission.service';
import ADClientService from '@/entities/ad-client/ad-client.service';
import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';
import ADTableService from '@/entities/ad-table/ad-table.service';
import ADColumnService from '@/entities/ad-column/ad-column.service';
import ADWindowService from '@/entities/ad-window/ad-window.service';
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import ADFieldService from '@/entities/ad-field/ad-field.service';
import ADFieldGroupService from '@/entities/ad-field-group/ad-field-group.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);

const i18n = config.initI18N(Vue);

const alertService = new AlertService(store);
const trackerService = new TrackerService(router);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, trackerService, router);

Vue.use(ElementUI, {
  size: appStore.size, // Set element-ui default size
  i18n: (key: string, value: string) => i18n.t(key, value)
});

Vue.use(SvgIcon, {
  tagName: 'svg-icon',
  defaultWidth: '1em',
  defaultHeight: '1em'
});

Vue.use(VueShortkey);

// Register global directives
Object.keys(directives).forEach(key => {
  Vue.directive(key, (directives as { [key: string]: DirectiveOptions })[key]);
});

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),
    auditsService: () => new AuditsService(),
    healthService: () => new HealthService(),
    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    trackerService: () => trackerService,
    dynamicWindowService: (baseApiUrl: string) => new DynamicWindowService(baseApiUrl),
    alertService: () => alertService,
    translationService: () => translationService,
    countryService: () => new CountryService(),
    currencyService: () => new CurrencyService(),
    locationService: () => new LocationService(),
    vendorService: () => new VendorService(),
    businessCategoryService: () => new BusinessCategoryService(),
    supportingDocumentService: () => new SupportingDocumentService(),
    documentTypeService: () => new DocumentTypeService(),
    documentTypeBusinessCategoryService: () => new DocumentTypeBusinessCategoryService(),
    personInChargeService: () => new PersonInChargeService(),
    companyFunctionaryService: () => new CompanyFunctionaryService(),
    referenceService: () => new ReferenceService(),
    referenceListService: () => new ReferenceListService(),
    regionService: () => new RegionService(),
    cityService: () => new CityService(),
    permissionService: () => new PermissionService(),
    aDClientService: () => new ADClientService(),
    aDOrganizationService: () => new ADOrganizationService(),
    aDTableService: () => new ADTableService(),
    aDColumnService: () => new ADColumnService(),
    aDWindowService: () => new ADWindowService(),
    aDTabService: () => new ADTabService(),
    aDFieldService: () => new ADFieldService(),
    aDFieldGroupService: () => new ADFieldGroupService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService
  },
  i18n,
  store
});
