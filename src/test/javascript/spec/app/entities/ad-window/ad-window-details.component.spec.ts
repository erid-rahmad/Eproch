/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADWindowDetailComponent from '@/entities/ad-window/ad-window-details.vue';
import ADWindowClass from '@/entities/ad-window/ad-window-details.component';
import ADWindowService from '@/entities/ad-window/ad-window.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADWindow Management Detail Component', () => {
    let wrapper: Wrapper<ADWindowClass>;
    let comp: ADWindowClass;
    let aDWindowServiceStub: SinonStubbedInstance<ADWindowService>;

    beforeEach(() => {
      aDWindowServiceStub = sinon.createStubInstance<ADWindowService>(ADWindowService);

      wrapper = shallowMount<ADWindowClass>(ADWindowDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDWindowService: () => aDWindowServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADWindow = { id: 123 };
        aDWindowServiceStub.find.resolves(foundADWindow);

        // WHEN
        comp.retrieveADWindow(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDWindow).toBe(foundADWindow);
      });
    });
  });
});
