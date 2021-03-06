import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store'
import Settings from '@/account/settings/settings.vue';
import SettingsClass from '@/account/settings/settings.component';
import { EMAIL_ALREADY_USED_TYPE } from '@/constants';

const localVue = createLocalVue();
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);

jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn()
}));

describe('Settings Component', () => {
  let wrapper: Wrapper<SettingsClass>;
  let settings: SettingsClass;
  const account = {
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@jhipster.org'
  };

  beforeEach(async () => {
    mockedAxios.get.mockReset();
    mockedAxios.get.mockReturnValue(Promise.resolve({}));
    mockedAxios.post.mockReset();

    await accountStore.setAuthenticated(account)
    wrapper = shallowMount<SettingsClass>(Settings, {
      store,
      i18n,
      localVue
    });
    settings = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  it('should send the current identity upon save', async () => {
    // GIVEN
    mockedAxios.post.mockReturnValue(Promise.resolve({}));

    // WHEN
    settings.save();
    await settings.$nextTick();

    // THEN
    expect(mockedAxios.post).toHaveBeenCalledWith('api/account', account);
  });

  it('should notify of success upon successful save', async () => {
    // GIVEN
    mockedAxios.post.mockReturnValue(Promise.resolve(account));

    // WHEN
    settings.save();
    await settings.$nextTick();

    // THEN
    expect(settings.error).toBeNull();
    expect(settings.success).toBe('OK');
  });

  it('should notify of error upon failed save', async () => {
    // GIVEN
    const error = { response: { status: 417 } };
    mockedAxios.post.mockReturnValue(Promise.reject(error));

    // WHEN
    settings.save();
    await settings.$nextTick();

    // THEN
    expect(settings.error).toEqual('ERROR');
    expect(settings.errorEmailExists).toBeNull();
    expect(settings.success).toBeNull();
  });

  it('should notify of error upon error 400', async () => {
    // GIVEN
    const error = { response: { status: 400, data: {} } };
    mockedAxios.post.mockReturnValue(Promise.reject(error));

    // WHEN
    settings.save();
    await settings.$nextTick();

    // THEN
    expect(settings.error).toEqual('ERROR');
    expect(settings.errorEmailExists).toBeNull();
    expect(settings.success).toBeNull();
  });

  it('should notify of error upon email already used', async () => {
    // GIVEN
    const error = { response: { status: 400, data: { type: EMAIL_ALREADY_USED_TYPE } } };
    mockedAxios.post.mockReturnValue(Promise.reject(error));

    // WHEN
    settings.save();
    await settings.$nextTick();

    // THEN
    expect(settings.errorEmailExists).toEqual('ERROR');
    expect(settings.error).toBeNull();
    expect(settings.success).toBeNull();
  });
});
