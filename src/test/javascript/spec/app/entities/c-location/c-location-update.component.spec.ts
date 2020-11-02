/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CLocationUpdateComponent from '@/entities/c-location/c-location-update.vue';
import CLocationClass from '@/entities/c-location/c-location-update.component';
import CLocationService from '@/entities/c-location/c-location.service';

import CCityService from '@/entities/c-city/c-city.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CLocation Management Update Component', () => {
    let wrapper: Wrapper<CLocationClass>;
    let comp: CLocationClass;
    let cLocationServiceStub: SinonStubbedInstance<CLocationService>;

    beforeEach(() => {
      cLocationServiceStub = sinon.createStubInstance<CLocationService>(CLocationService);

      wrapper = shallowMount<CLocationClass>(CLocationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cLocationService: () => cLocationServiceStub,

          cCityService: () => new CCityService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cLocation = entity;
        cLocationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cLocationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cLocation = entity;
        cLocationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cLocationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
