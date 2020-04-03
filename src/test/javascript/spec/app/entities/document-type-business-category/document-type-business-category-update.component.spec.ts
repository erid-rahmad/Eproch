/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentTypeBusinessCategoryUpdateComponent from '@/entities/document-type-business-category/document-type-business-category-update.vue';
import DocumentTypeBusinessCategoryClass from '@/entities/document-type-business-category/document-type-business-category-update.component';
import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';

import DocumentTypeService from '@/entities/document-type/document-type.service';

import BusinessCategoryService from '@/entities/business-category/business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DocumentTypeBusinessCategory Management Update Component', () => {
    let wrapper: Wrapper<DocumentTypeBusinessCategoryClass>;
    let comp: DocumentTypeBusinessCategoryClass;
    let documentTypeBusinessCategoryServiceStub: SinonStubbedInstance<DocumentTypeBusinessCategoryService>;

    beforeEach(() => {
      documentTypeBusinessCategoryServiceStub = sinon.createStubInstance<DocumentTypeBusinessCategoryService>(
        DocumentTypeBusinessCategoryService
      );

      wrapper = shallowMount<DocumentTypeBusinessCategoryClass>(DocumentTypeBusinessCategoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentTypeBusinessCategoryService: () => documentTypeBusinessCategoryServiceStub,

          documentTypeService: () => new DocumentTypeService(),

          businessCategoryService: () => new BusinessCategoryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.documentTypeBusinessCategory = entity;
        documentTypeBusinessCategoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentTypeBusinessCategoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documentTypeBusinessCategory = entity;
        documentTypeBusinessCategoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentTypeBusinessCategoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
