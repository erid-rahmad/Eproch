/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CRegionUpdateComponent from '@/entities/c-region/c-region-update.vue';
import CRegionClass from '@/entities/c-region/c-region-update.component';
import CRegionService from '@/entities/c-region/c-region.service';

import CCityService from '@/entities/c-city/c-city.service';

import CCountryService from '@/entities/c-country/c-country.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CRegion Management Update Component', () => {
    let wrapper: Wrapper<CRegionClass>;
    let comp: CRegionClass;
    let cRegionServiceStub: SinonStubbedInstance<CRegionService>;

    beforeEach(() => {
      cRegionServiceStub = sinon.createStubInstance<CRegionService>(CRegionService);

      wrapper = shallowMount<CRegionClass>(CRegionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cRegionService: () => cRegionServiceStub,

          cCityService: () => new CCityService(),

          cCountryService: () => new CCountryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cRegion = entity;
        cRegionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cRegionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cRegion = entity;
        cRegionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cRegionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
