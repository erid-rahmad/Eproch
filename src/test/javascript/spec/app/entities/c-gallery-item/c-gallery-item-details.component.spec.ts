/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CGalleryItemDetailComponent from '@/entities/c-gallery-item/c-gallery-item-details.vue';
import CGalleryItemClass from '@/entities/c-gallery-item/c-gallery-item-details.component';
import CGalleryItemService from '@/entities/c-gallery-item/c-gallery-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CGalleryItem Management Detail Component', () => {
    let wrapper: Wrapper<CGalleryItemClass>;
    let comp: CGalleryItemClass;
    let cGalleryItemServiceStub: SinonStubbedInstance<CGalleryItemService>;

    beforeEach(() => {
      cGalleryItemServiceStub = sinon.createStubInstance<CGalleryItemService>(CGalleryItemService);

      wrapper = shallowMount<CGalleryItemClass>(CGalleryItemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cGalleryItemService: () => cGalleryItemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCGalleryItem = { id: 123 };
        cGalleryItemServiceStub.find.resolves(foundCGalleryItem);

        // WHEN
        comp.retrieveCGalleryItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cGalleryItem).toBe(foundCGalleryItem);
      });
    });
  });
});
