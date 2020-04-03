/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentTypeBusinessCategoryComponent from '@/entities/document-type-business-category/document-type-business-category.vue';
import DocumentTypeBusinessCategoryClass from '@/entities/document-type-business-category/document-type-business-category.component';
import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {}
  }
};

describe('Component Tests', () => {
  describe('DocumentTypeBusinessCategory Management Component', () => {
    let wrapper: Wrapper<DocumentTypeBusinessCategoryClass>;
    let comp: DocumentTypeBusinessCategoryClass;
    let documentTypeBusinessCategoryServiceStub: SinonStubbedInstance<DocumentTypeBusinessCategoryService>;

    beforeEach(() => {
      documentTypeBusinessCategoryServiceStub = sinon.createStubInstance<DocumentTypeBusinessCategoryService>(
        DocumentTypeBusinessCategoryService
      );
      documentTypeBusinessCategoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentTypeBusinessCategoryClass>(DocumentTypeBusinessCategoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          documentTypeBusinessCategoryService: () => documentTypeBusinessCategoryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentTypeBusinessCategoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDocumentTypeBusinessCategorys();
      await comp.$nextTick();

      // THEN
      expect(documentTypeBusinessCategoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentTypeBusinessCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentTypeBusinessCategoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDocumentTypeBusinessCategory();
      await comp.$nextTick();

      // THEN
      expect(documentTypeBusinessCategoryServiceStub.delete.called).toBeTruthy();
      expect(documentTypeBusinessCategoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
