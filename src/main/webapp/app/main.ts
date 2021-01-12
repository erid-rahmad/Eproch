// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue, { DirectiveOptions } from 'vue';
import 'normalize.css';
import ElementUI from 'element-ui';
import SvgIcon from 'vue-svgicon';
import VueHotkey from 'v-hotkey';
import Schema from 'async-validator';

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
import ADColumnService from '@/entities/ad-column/ad-column.service';
import ADWindowService from '@/entities/ad-window/ad-window.service';
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import RouterValidation from './permission';
import VueRouter, { Route } from 'vue-router';
import AdMenuService from './core/application-dictionary/components/Menu/menu.service';
import { Store } from 'vuex';
import TreeItem from './core/application-dictionary/components/TreeView/tree-item.vue';
import InputFacade from 'vue-input-facade';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Schema.warning = function() {};
Vue.use(InputFacade);
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
const menuService = new AdMenuService();
const windowService = (baseApiUrl: string) => new DynamicWindowService(baseApiUrl);

const accountServiceInitiator = async (
  vuexStore: Store<any>,
  dynWindowService: (baseApiUrl: string) => DynamicWindowService,
  i18nService: TranslationService,
  userTrackerService: TrackerService,
  adMenuService: AdMenuService,
  vueRouter: VueRouter
) => {
  const service = new AccountService(vuexStore, dynWindowService, i18nService, userTrackerService, adMenuService, vueRouter);
  await service.init();
  return service;
};

Vue.use(ElementUI, {
  size: appStore.size, // Set element-ui default size
  i18n: (key: string, value: string) => i18n.t(key, value)
});

Vue.component('tree-item', TreeItem);

Vue.use(SvgIcon, {
  tagName: 'svg-icon',
  defaultWidth: '1em',
  defaultHeight: '1em'
});

Vue.use(VueHotkey);

accountServiceInitiator(store, windowService, translationService, trackerService, menuService, router)
  .then(service => {
    const accountService = service;
    const routerValidation = new RouterValidation(router, i18n, accountService);

    // Register global directives
    Object.keys(directives).forEach(key => {
      Vue.directive(key, (directives as { [key: string]: DirectiveOptions })[key]);
    });

    router.beforeEach((to, from, next) => {
      routerValidation.runBeforeEachHook(to, from, next);
    });

    router.afterEach((to: Route) => {
      routerValidation.runAfterEachHook(to);
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
        dynamicWindowService: windowService,
        alertService: () => alertService,
        translationService: () => translationService,
        // End of jhipster default services.

        aDColumnService: () => new ADColumnService(),
        aDWindowService: () => new ADWindowService(),
        aDTabService: () => new ADTabService(),
        // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
        accountService: () => accountService
      },
      i18n,
      store
    });
  })
  .catch(err => {
    console.error('Failed to initialize Vue. ', err);
  });
