/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CGalleryDetailComponent from '@/entities/c-gallery/c-gallery-details.vue';
import CGalleryClass from '@/entities/c-gallery/c-gallery-details.component';
import CGalleryService from '@/entities/c-gallery/c-gallery.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CGallery Management Detail Component', () => {
    let wrapper: Wrapper<CGalleryClass>;
    let comp: CGalleryClass;
    let cGalleryServiceStub: SinonStubbedInstance<CGalleryService>;

    beforeEach(() => {
      cGalleryServiceStub = sinon.createStubInstance<CGalleryService>(CGalleryService);

      wrapper = shallowMount<CGalleryClass>(CGalleryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cGalleryService: () => cGalleryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCGallery = { id: 123 };
        cGalleryServiceStub.find.resolves(foundCGallery);

        // WHEN
        comp.retrieveCGallery(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cGallery).toBe(foundCGallery);
      });
    });
  });
});
