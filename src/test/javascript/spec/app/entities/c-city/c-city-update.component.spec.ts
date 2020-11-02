/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CCityUpdateComponent from '@/entities/c-city/c-city-update.vue';
import CCityClass from '@/entities/c-city/c-city-update.component';
import CCityService from '@/entities/c-city/c-city.service';

import CCountryService from '@/entities/c-country/c-country.service';

import CRegionService from '@/entities/c-region/c-region.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CCity Management Update Component', () => {
    let wrapper: Wrapper<CCityClass>;
    let comp: CCityClass;
    let cCityServiceStub: SinonStubbedInstance<CCityService>;

    beforeEach(() => {
      cCityServiceStub = sinon.createStubInstance<CCityService>(CCityService);

      wrapper = shallowMount<CCityClass>(CCityUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cCityService: () => cCityServiceStub,

          cCountryService: () => new CCountryService(),

          cRegionService: () => new CRegionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cCity = entity;
        cCityServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCityServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cCity = entity;
        cCityServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
