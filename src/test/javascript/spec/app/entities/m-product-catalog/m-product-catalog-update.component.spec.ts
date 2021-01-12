/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MProductCatalogUpdateComponent from '@/entities/m-product-catalog/m-product-catalog-update.vue';
import MProductCatalogClass from '@/entities/m-product-catalog/m-product-catalog-update.component';
import MProductCatalogService from '@/entities/m-product-catalog/m-product-catalog.service';

import CGalleryService from '@/entities/c-gallery/c-gallery.service';

import MProductPriceService from '@/entities/m-product-price/m-product-price.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import CDocumentTypeService from '@/entities/c-document-type/c-document-type.service';

import CCurrencyService from '@/entities/c-currency/c-currency.service';

import CUnitOfMeasureService from '@/entities/c-unit-of-measure/c-unit-of-measure.service';

import CVendorService from '@/entities/c-vendor/c-vendor.service';

import MBrandService from '@/entities/m-brand/m-brand.service';

import CProductService from '@/entities/c-product/c-product.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MProductCatalog Management Update Component', () => {
    let wrapper: Wrapper<MProductCatalogClass>;
    let comp: MProductCatalogClass;
    let mProductCatalogServiceStub: SinonStubbedInstance<MProductCatalogService>;

    beforeEach(() => {
      mProductCatalogServiceStub = sinon.createStubInstance<MProductCatalogService>(MProductCatalogService);

      wrapper = shallowMount<MProductCatalogClass>(MProductCatalogUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          mProductCatalogService: () => mProductCatalogServiceStub,

          cGalleryService: () => new CGalleryService(),

          mProductPriceService: () => new MProductPriceService(),

          aDOrganizationService: () => new ADOrganizationService(),

          cDocumentTypeService: () => new CDocumentTypeService(),

          cCurrencyService: () => new CCurrencyService(),

          cUnitOfMeasureService: () => new CUnitOfMeasureService(),

          cVendorService: () => new CVendorService(),

          mBrandService: () => new MBrandService(),

          cProductService: () => new CProductService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.mProductCatalog = entity;
        mProductCatalogServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mProductCatalogServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.mProductCatalog = entity;
        mProductCatalogServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mProductCatalogServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
