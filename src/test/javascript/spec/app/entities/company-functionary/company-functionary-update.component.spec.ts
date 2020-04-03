/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyFunctionaryUpdateComponent from '@/entities/company-functionary/company-functionary-update.vue';
import CompanyFunctionaryClass from '@/entities/company-functionary/company-functionary-update.component';
import CompanyFunctionaryService from '@/entities/company-functionary/company-functionary.service';

import VendorService from '@/entities/vendor/vendor.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompanyFunctionary Management Update Component', () => {
    let wrapper: Wrapper<CompanyFunctionaryClass>;
    let comp: CompanyFunctionaryClass;
    let companyFunctionaryServiceStub: SinonStubbedInstance<CompanyFunctionaryService>;

    beforeEach(() => {
      companyFunctionaryServiceStub = sinon.createStubInstance<CompanyFunctionaryService>(CompanyFunctionaryService);

      wrapper = shallowMount<CompanyFunctionaryClass>(CompanyFunctionaryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          companyFunctionaryService: () => companyFunctionaryServiceStub,

          vendorService: () => new VendorService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companyFunctionary = entity;
        companyFunctionaryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyFunctionaryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companyFunctionary = entity;
        companyFunctionaryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyFunctionaryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
