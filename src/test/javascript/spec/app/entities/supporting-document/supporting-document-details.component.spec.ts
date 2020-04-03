/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SupportingDocumentDetailComponent from '@/entities/supporting-document/supporting-document-details.vue';
import SupportingDocumentClass from '@/entities/supporting-document/supporting-document-details.component';
import SupportingDocumentService from '@/entities/supporting-document/supporting-document.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SupportingDocument Management Detail Component', () => {
    let wrapper: Wrapper<SupportingDocumentClass>;
    let comp: SupportingDocumentClass;
    let supportingDocumentServiceStub: SinonStubbedInstance<SupportingDocumentService>;

    beforeEach(() => {
      supportingDocumentServiceStub = sinon.createStubInstance<SupportingDocumentService>(SupportingDocumentService);

      wrapper = shallowMount<SupportingDocumentClass>(SupportingDocumentDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { supportingDocumentService: () => supportingDocumentServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSupportingDocument = { id: 123 };
        supportingDocumentServiceStub.find.resolves(foundSupportingDocument);

        // WHEN
        comp.retrieveSupportingDocument(123);
        await comp.$nextTick();

        // THEN
        expect(comp.supportingDocument).toBe(foundSupportingDocument);
      });
    });
  });
});
