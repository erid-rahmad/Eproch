// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue, { DirectiveOptions } from 'vue';
import 'normalize.css';
import ElementUI from 'element-ui';
import SvgIcon from 'vue-svgicon';
import VueHotkey from 'v-hotkey';

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
import ADReferenceService from '@/entities/ad-reference/ad-reference.service';
import ADReferenceListService from '@/entities/ad-reference-list/ad-reference-list.service';
import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';
import ADTableService from '@/entities/ad-table/ad-table.service';
import ADColumnService from '@/entities/ad-column/ad-column.service';
import ADWindowService from '@/entities/ad-window/ad-window.service';
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import ADFieldService from '@/entities/ad-field/ad-field.service';
import ADFieldGroupService from '@/entities/ad-field-group/ad-field-group.service';
import AdValidationRuleService from '@/entities/ad-validation-rule/ad-validation-rule.service';
import CCountryService from '@/entities/c-country/c-country.service';
import CCurrencyService from '@/entities/c-currency/c-currency.service';
import CRegionService from '@/entities/c-region/c-region.service';
import CCityService from '@/entities/c-city/c-city.service';
import CLocationService from '@/entities/c-location/c-location.service';
import CBankService from '@/entities/c-bank/c-bank.service';
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

Vue.use(VueHotkey);

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
    // End of jhipster default services.

    aDReferenceService: () => new ADReferenceService(),
    aDReferenceListService: () => new ADReferenceListService(),
    aDOrganizationService: () => new ADOrganizationService(),
    aDTableService: () => new ADTableService(),
    aDColumnService: () => new ADColumnService(),
    aDWindowService: () => new ADWindowService(),
    aDTabService: () => new ADTabService(),
    aDFieldService: () => new ADFieldService(),
    aDFieldGroupService: () => new ADFieldGroupService(),
    adValidationRuleService: () => new AdValidationRuleService(),
    cCountryService: () => new CCountryService(),
    cCurrencyService: () => new CCurrencyService(),
    cRegionService: () => new CRegionService(),
    cCityService: () => new CCityService(),
    cLocationService: () => new CLocationService(),
    cBankService: () => new CBankService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService
  },
  i18n,
  store
});
