/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MProductCatalogDetailComponent from '@/entities/m-product-catalog/m-product-catalog-details.vue';
import MProductCatalogClass from '@/entities/m-product-catalog/m-product-catalog-details.component';
import MProductCatalogService from '@/entities/m-product-catalog/m-product-catalog.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MProductCatalog Management Detail Component', () => {
    let wrapper: Wrapper<MProductCatalogClass>;
    let comp: MProductCatalogClass;
    let mProductCatalogServiceStub: SinonStubbedInstance<MProductCatalogService>;

    beforeEach(() => {
      mProductCatalogServiceStub = sinon.createStubInstance<MProductCatalogService>(MProductCatalogService);

      wrapper = shallowMount<MProductCatalogClass>(MProductCatalogDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { mProductCatalogService: () => mProductCatalogServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMProductCatalog = { id: 123 };
        mProductCatalogServiceStub.find.resolves(foundMProductCatalog);

        // WHEN
        comp.retrieveMProductCatalog(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mProductCatalog).toBe(foundMProductCatalog);
      });
    });
  });
});
