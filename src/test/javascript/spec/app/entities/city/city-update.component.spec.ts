/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CityUpdateComponent from '@/entities/city/city-update.vue';
import CityClass from '@/entities/city/city-update.component';
import CityService from '@/entities/city/city.service';

import CountryService from '@/entities/country/country.service';

import RegionService from '@/entities/region/region.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('City Management Update Component', () => {
    let wrapper: Wrapper<CityClass>;
    let comp: CityClass;
    let cityServiceStub: SinonStubbedInstance<CityService>;

    beforeEach(() => {
      cityServiceStub = sinon.createStubInstance<CityService>(CityService);

      wrapper = shallowMount<CityClass>(CityUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cityService: () => cityServiceStub,

          countryService: () => new CountryService(),

          regionService: () => new RegionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.city = entity;
        cityServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cityServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.city = entity;
        cityServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
