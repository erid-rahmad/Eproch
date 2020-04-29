/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADTabDetailComponent from '@/entities/ad-tab/ad-tab-details.vue';
import ADTabClass from '@/entities/ad-tab/ad-tab-details.component';
import ADTabService from '@/entities/ad-tab/ad-tab.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADTab Management Detail Component', () => {
    let wrapper: Wrapper<ADTabClass>;
    let comp: ADTabClass;
    let aDTabServiceStub: SinonStubbedInstance<ADTabService>;

    beforeEach(() => {
      aDTabServiceStub = sinon.createStubInstance<ADTabService>(ADTabService);

      wrapper = shallowMount<ADTabClass>(ADTabDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDTabService: () => aDTabServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADTab = { id: 123 };
        aDTabServiceStub.find.resolves(foundADTab);

        // WHEN
        comp.retrieveADTab(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDTab).toBe(foundADTab);
      });
    });
  });
});
