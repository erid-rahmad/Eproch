/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CGalleryUpdateComponent from '@/entities/c-gallery/c-gallery-update.vue';
import CGalleryClass from '@/entities/c-gallery/c-gallery-update.component';
import CGalleryService from '@/entities/c-gallery/c-gallery.service';

import CGalleryItemService from '@/entities/c-gallery-item/c-gallery-item.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CGallery Management Update Component', () => {
    let wrapper: Wrapper<CGalleryClass>;
    let comp: CGalleryClass;
    let cGalleryServiceStub: SinonStubbedInstance<CGalleryService>;

    beforeEach(() => {
      cGalleryServiceStub = sinon.createStubInstance<CGalleryService>(CGalleryService);

      wrapper = shallowMount<CGalleryClass>(CGalleryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cGalleryService: () => cGalleryServiceStub,

          cGalleryItemService: () => new CGalleryItemService(),

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cGallery = entity;
        cGalleryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cGalleryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cGallery = entity;
        cGalleryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cGalleryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
