/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADClientDetailComponent from '@/entities/ad-client/ad-client-details.vue';
import ADClientClass from '@/entities/ad-client/ad-client-details.component';
import ADClientService from '@/entities/ad-client/ad-client.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADClient Management Detail Component', () => {
    let wrapper: Wrapper<ADClientClass>;
    let comp: ADClientClass;
    let aDClientServiceStub: SinonStubbedInstance<ADClientService>;

    beforeEach(() => {
      aDClientServiceStub = sinon.createStubInstance<ADClientService>(ADClientService);

      wrapper = shallowMount<ADClientClass>(ADClientDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDClientService: () => aDClientServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADClient = { id: 123 };
        aDClientServiceStub.find.resolves(foundADClient);

        // WHEN
        comp.retrieveADClient(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDClient).toBe(foundADClient);
      });
    });
  });
});
