/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CAttachmentUpdateComponent from '@/entities/c-attachment/c-attachment-update.vue';
import CAttachmentClass from '@/entities/c-attachment/c-attachment-update.component';
import CAttachmentService from '@/entities/c-attachment/c-attachment.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CAttachment Management Update Component', () => {
    let wrapper: Wrapper<CAttachmentClass>;
    let comp: CAttachmentClass;
    let cAttachmentServiceStub: SinonStubbedInstance<CAttachmentService>;

    beforeEach(() => {
      cAttachmentServiceStub = sinon.createStubInstance<CAttachmentService>(CAttachmentService);

      wrapper = shallowMount<CAttachmentClass>(CAttachmentUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cAttachmentService: () => cAttachmentServiceStub,

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cAttachment = entity;
        cAttachmentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cAttachmentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cAttachment = entity;
        cAttachmentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cAttachmentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
