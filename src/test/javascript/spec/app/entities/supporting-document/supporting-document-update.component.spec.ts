/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SupportingDocumentUpdateComponent from '@/entities/supporting-document/supporting-document-update.vue';
import SupportingDocumentClass from '@/entities/supporting-document/supporting-document-update.component';
import SupportingDocumentService from '@/entities/supporting-document/supporting-document.service';

import DocumentTypeService from '@/entities/document-type/document-type.service';

import VendorService from '@/entities/vendor/vendor.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SupportingDocument Management Update Component', () => {
    let wrapper: Wrapper<SupportingDocumentClass>;
    let comp: SupportingDocumentClass;
    let supportingDocumentServiceStub: SinonStubbedInstance<SupportingDocumentService>;

    beforeEach(() => {
      supportingDocumentServiceStub = sinon.createStubInstance<SupportingDocumentService>(SupportingDocumentService);

      wrapper = shallowMount<SupportingDocumentClass>(SupportingDocumentUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          supportingDocumentService: () => supportingDocumentServiceStub,

          documentTypeService: () => new DocumentTypeService(),

          vendorService: () => new VendorService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.supportingDocument = entity;
        supportingDocumentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(supportingDocumentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.supportingDocument = entity;
        supportingDocumentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(supportingDocumentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
