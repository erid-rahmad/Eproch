/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CCountryUpdateComponent from '@/entities/c-country/c-country-update.vue';
import CCountryClass from '@/entities/c-country/c-country-update.component';
import CCountryService from '@/entities/c-country/c-country.service';

import CCurrencyService from '@/entities/c-currency/c-currency.service';

import CRegionService from '@/entities/c-region/c-region.service';

import CCityService from '@/entities/c-city/c-city.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CCountry Management Update Component', () => {
    let wrapper: Wrapper<CCountryClass>;
    let comp: CCountryClass;
    let cCountryServiceStub: SinonStubbedInstance<CCountryService>;

    beforeEach(() => {
      cCountryServiceStub = sinon.createStubInstance<CCountryService>(CCountryService);

      wrapper = shallowMount<CCountryClass>(CCountryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cCountryService: () => cCountryServiceStub,

          cCurrencyService: () => new CCurrencyService(),

          cRegionService: () => new CRegionService(),

          cCityService: () => new CCityService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cCountry = entity;
        cCountryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCountryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cCountry = entity;
        cCountryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCountryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
