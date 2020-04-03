/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import VendorUpdateComponent from '@/entities/vendor/vendor-update.vue';
import VendorClass from '@/entities/vendor/vendor-update.component';
import VendorService from '@/entities/vendor/vendor.service';

import LocationService from '@/entities/location/location.service';

import CompanyFunctionaryService from '@/entities/company-functionary/company-functionary.service';

import PersonInChargeService from '@/entities/person-in-charge/person-in-charge.service';

import SupportingDocumentService from '@/entities/supporting-document/supporting-document.service';

import BusinessCategoryService from '@/entities/business-category/business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Vendor Management Update Component', () => {
    let wrapper: Wrapper<VendorClass>;
    let comp: VendorClass;
    let vendorServiceStub: SinonStubbedInstance<VendorService>;

    beforeEach(() => {
      vendorServiceStub = sinon.createStubInstance<VendorService>(VendorService);

      wrapper = shallowMount<VendorClass>(VendorUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          vendorService: () => vendorServiceStub,

          locationService: () => new LocationService(),

          companyFunctionaryService: () => new CompanyFunctionaryService(),

          personInChargeService: () => new PersonInChargeService(),

          supportingDocumentService: () => new SupportingDocumentService(),

          businessCategoryService: () => new BusinessCategoryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.vendor = entity;
        vendorServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vendorServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vendor = entity;
        vendorServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vendorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
