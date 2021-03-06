import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';
import AccountService from '@/account/account.service';
import router from '@/router';
import TranslationService from '@/locale/translation.service';
import TrackerService from '@/admin/tracker/tracker.service';

import * as config from '@/shared/config/config';
import LoginForm from '@/account/login-form/login-form.vue';
import LoginFormClass from '@/account/login-form/login-form.component';
import MenuService from '@/core/application-dictionary/components/Menu/menu.service';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const localVue = createLocalVue();
localVue.component('b-alert', {});
localVue.component('b-button', {});
localVue.component('b-form', {});
localVue.component('b-form-input', {});
localVue.component('b-form-group', {});
localVue.component('b-form-checkbox', {});
localVue.component('b-link', {});
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);

jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn()
}));

describe('LoginForm Component', () => {
  let wrapper: Wrapper<LoginFormClass>;
  let loginForm: LoginFormClass;

  beforeEach(() => {
    mockedAxios.get.mockReset();
    mockedAxios.get.mockReturnValue(Promise.resolve({}));
    mockedAxios.post.mockReset();

    wrapper = shallowMount<LoginFormClass>(LoginForm, {
      store,
      i18n,
      localVue,
      provide: {
        accountService: () => new AccountService(store, (url) => new DynamicWindowService(url), new TranslationService(store, i18n), new TrackerService(router), new MenuService(), router)
      }
    });
    loginForm = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  it('should not store token if authentication is KO', async () => {
    // GIVEN
    loginForm.loginForm.username = 'login';
    loginForm.loginForm.password = 'pwd';
    loginForm.loginForm.rememberMe = true;
    mockedAxios.post.mockReturnValue(Promise.reject());

    // WHEN
    loginForm.handleLogin();
    await loginForm.$nextTick();

    // THEN
    expect(mockedAxios.post).toHaveBeenCalledWith('api/authenticate', {
      username: 'login',
      password: 'pwd',
      rememberMe: true
    });

    expect(localStorage.getItem('jhi-authenticationToken')).toBeUndefined();
  });

  it('should store token if authentication is OK', async () => {
    // GIVEN
    loginForm.loginForm.username = 'login';
    loginForm.loginForm.password = 'pwd';
    loginForm.loginForm.rememberMe = true;
    const jwtSecret = 'jwt-secret';
    mockedAxios.post.mockReturnValue(Promise.resolve({ headers: { authorization: 'Bearer ' + jwtSecret } }));

    // WHEN
    loginForm.handleLogin();
    await loginForm.$nextTick();

    // THEN
    expect(mockedAxios.post).toHaveBeenCalledWith('api/authenticate', {
      username: 'login',
      password: 'pwd',
      rememberMe: true
    });

    expect(localStorage.getItem('jhi-authenticationToken')).toEqual(jwtSecret);
  });
});
