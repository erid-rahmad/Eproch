/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentTypeUpdateComponent from '@/entities/document-type/document-type-update.vue';
import DocumentTypeClass from '@/entities/document-type/document-type-update.component';
import DocumentTypeService from '@/entities/document-type/document-type.service';

import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DocumentType Management Update Component', () => {
    let wrapper: Wrapper<DocumentTypeClass>;
    let comp: DocumentTypeClass;
    let documentTypeServiceStub: SinonStubbedInstance<DocumentTypeService>;

    beforeEach(() => {
      documentTypeServiceStub = sinon.createStubInstance<DocumentTypeService>(DocumentTypeService);

      wrapper = shallowMount<DocumentTypeClass>(DocumentTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentTypeService: () => documentTypeServiceStub,

          documentTypeBusinessCategoryService: () => new DocumentTypeBusinessCategoryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.documentType = entity;
        documentTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documentType = entity;
        documentTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
