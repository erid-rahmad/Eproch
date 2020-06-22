/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CCityDetailComponent from '@/entities/c-city/c-city-details.vue';
import CCityClass from '@/entities/c-city/c-city-details.component';
import CCityService from '@/entities/c-city/c-city.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CCity Management Detail Component', () => {
    let wrapper: Wrapper<CCityClass>;
    let comp: CCityClass;
    let cCityServiceStub: SinonStubbedInstance<CCityService>;

    beforeEach(() => {
      cCityServiceStub = sinon.createStubInstance<CCityService>(CCityService);

      wrapper = shallowMount<CCityClass>(CCityDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cCityService: () => cCityServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCCity = { id: 123 };
        cCityServiceStub.find.resolves(foundCCity);

        // WHEN
        comp.retrieveCCity(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cCity).toBe(foundCCity);
      });
    });
  });
});
