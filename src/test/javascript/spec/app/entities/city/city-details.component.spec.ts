/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CityDetailComponent from '@/entities/city/city-details.vue';
import CityClass from '@/entities/city/city-details.component';
import CityService from '@/entities/city/city.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('City Management Detail Component', () => {
    let wrapper: Wrapper<CityClass>;
    let comp: CityClass;
    let cityServiceStub: SinonStubbedInstance<CityService>;

    beforeEach(() => {
      cityServiceStub = sinon.createStubInstance<CityService>(CityService);

      wrapper = shallowMount<CityClass>(CityDetailComponent, { store, i18n, localVue, provide: { cityService: () => cityServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCity = { id: 123 };
        cityServiceStub.find.resolves(foundCity);

        // WHEN
        comp.retrieveCity(123);
        await comp.$nextTick();

        // THEN
        expect(comp.city).toBe(foundCity);
      });
    });
  });
});
