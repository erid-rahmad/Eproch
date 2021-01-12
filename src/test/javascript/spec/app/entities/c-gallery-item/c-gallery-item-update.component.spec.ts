/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CGalleryItemUpdateComponent from '@/entities/c-gallery-item/c-gallery-item-update.vue';
import CGalleryItemClass from '@/entities/c-gallery-item/c-gallery-item-update.component';
import CGalleryItemService from '@/entities/c-gallery-item/c-gallery-item.service';

import CAttachmentService from '@/entities/c-attachment/c-attachment.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import CGalleryService from '@/entities/c-gallery/c-gallery.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CGalleryItem Management Update Component', () => {
    let wrapper: Wrapper<CGalleryItemClass>;
    let comp: CGalleryItemClass;
    let cGalleryItemServiceStub: SinonStubbedInstance<CGalleryItemService>;

    beforeEach(() => {
      cGalleryItemServiceStub = sinon.createStubInstance<CGalleryItemService>(CGalleryItemService);

      wrapper = shallowMount<CGalleryItemClass>(CGalleryItemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cGalleryItemService: () => cGalleryItemServiceStub,

          cAttachmentService: () => new CAttachmentService(),

          aDOrganizationService: () => new ADOrganizationService(),

          cGalleryService: () => new CGalleryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cGalleryItem = entity;
        cGalleryItemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cGalleryItemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cGalleryItem = entity;
        cGalleryItemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cGalleryItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
