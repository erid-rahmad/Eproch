/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BusinessCategoryUpdateComponent from '@/entities/business-category/business-category-update.vue';
import BusinessCategoryClass from '@/entities/business-category/business-category-update.component';
import BusinessCategoryService from '@/entities/business-category/business-category.service';

import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';

import VendorService from '@/entities/vendor/vendor.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('BusinessCategory Management Update Component', () => {
    let wrapper: Wrapper<BusinessCategoryClass>;
    let comp: BusinessCategoryClass;
    let businessCategoryServiceStub: SinonStubbedInstance<BusinessCategoryService>;

    beforeEach(() => {
      businessCategoryServiceStub = sinon.createStubInstance<BusinessCategoryService>(BusinessCategoryService);

      wrapper = shallowMount<BusinessCategoryClass>(BusinessCategoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          businessCategoryService: () => businessCategoryServiceStub,

          documentTypeBusinessCategoryService: () => new DocumentTypeBusinessCategoryService(),

          vendorService: () => new VendorService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.businessCategory = entity;
        businessCategoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(businessCategoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.businessCategory = entity;
        businessCategoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(businessCategoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
