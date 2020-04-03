/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentTypeBusinessCategoryDetailComponent from '@/entities/document-type-business-category/document-type-business-category-details.vue';
import DocumentTypeBusinessCategoryClass from '@/entities/document-type-business-category/document-type-business-category-details.component';
import DocumentTypeBusinessCategoryService from '@/entities/document-type-business-category/document-type-business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentTypeBusinessCategory Management Detail Component', () => {
    let wrapper: Wrapper<DocumentTypeBusinessCategoryClass>;
    let comp: DocumentTypeBusinessCategoryClass;
    let documentTypeBusinessCategoryServiceStub: SinonStubbedInstance<DocumentTypeBusinessCategoryService>;

    beforeEach(() => {
      documentTypeBusinessCategoryServiceStub = sinon.createStubInstance<DocumentTypeBusinessCategoryService>(
        DocumentTypeBusinessCategoryService
      );

      wrapper = shallowMount<DocumentTypeBusinessCategoryClass>(DocumentTypeBusinessCategoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentTypeBusinessCategoryService: () => documentTypeBusinessCategoryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentTypeBusinessCategory = { id: 123 };
        documentTypeBusinessCategoryServiceStub.find.resolves(foundDocumentTypeBusinessCategory);

        // WHEN
        comp.retrieveDocumentTypeBusinessCategory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentTypeBusinessCategory).toBe(foundDocumentTypeBusinessCategory);
      });
    });
  });
});
