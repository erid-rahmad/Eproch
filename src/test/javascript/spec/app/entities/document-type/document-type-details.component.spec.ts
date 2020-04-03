/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentTypeDetailComponent from '@/entities/document-type/document-type-details.vue';
import DocumentTypeClass from '@/entities/document-type/document-type-details.component';
import DocumentTypeService from '@/entities/document-type/document-type.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentType Management Detail Component', () => {
    let wrapper: Wrapper<DocumentTypeClass>;
    let comp: DocumentTypeClass;
    let documentTypeServiceStub: SinonStubbedInstance<DocumentTypeService>;

    beforeEach(() => {
      documentTypeServiceStub = sinon.createStubInstance<DocumentTypeService>(DocumentTypeService);

      wrapper = shallowMount<DocumentTypeClass>(DocumentTypeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentTypeService: () => documentTypeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentType = { id: 123 };
        documentTypeServiceStub.find.resolves(foundDocumentType);

        // WHEN
        comp.retrieveDocumentType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentType).toBe(foundDocumentType);
      });
    });
  });
});
