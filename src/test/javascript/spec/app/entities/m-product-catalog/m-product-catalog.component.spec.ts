/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MProductCatalogComponent from '@/entities/m-product-catalog/m-product-catalog.vue';
import MProductCatalogClass from '@/entities/m-product-catalog/m-product-catalog.component';
import MProductCatalogService from '@/entities/m-product-catalog/m-product-catalog.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {}
  }
};

describe('Component Tests', () => {
  describe('MProductCatalog Management Component', () => {
    let wrapper: Wrapper<MProductCatalogClass>;
    let comp: MProductCatalogClass;
    let mProductCatalogServiceStub: SinonStubbedInstance<MProductCatalogService>;

    beforeEach(() => {
      mProductCatalogServiceStub = sinon.createStubInstance<MProductCatalogService>(MProductCatalogService);
      mProductCatalogServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MProductCatalogClass>(MProductCatalogComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          mProductCatalogService: () => mProductCatalogServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      mProductCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMProductCatalogs();
      await comp.$nextTick();

      // THEN
      expect(mProductCatalogServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mProductCatalogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      mProductCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(mProductCatalogServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mProductCatalogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      mProductCatalogServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(mProductCatalogServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      mProductCatalogServiceStub.retrieve.reset();
      mProductCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(mProductCatalogServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.mProductCatalogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      mProductCatalogServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMProductCatalog();
      await comp.$nextTick();

      // THEN
      expect(mProductCatalogServiceStub.delete.called).toBeTruthy();
      expect(mProductCatalogServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
