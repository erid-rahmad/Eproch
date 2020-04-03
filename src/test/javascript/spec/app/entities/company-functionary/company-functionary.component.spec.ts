/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyFunctionaryComponent from '@/entities/company-functionary/company-functionary.vue';
import CompanyFunctionaryClass from '@/entities/company-functionary/company-functionary.component';
import CompanyFunctionaryService from '@/entities/company-functionary/company-functionary.service';

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
  describe('CompanyFunctionary Management Component', () => {
    let wrapper: Wrapper<CompanyFunctionaryClass>;
    let comp: CompanyFunctionaryClass;
    let companyFunctionaryServiceStub: SinonStubbedInstance<CompanyFunctionaryService>;

    beforeEach(() => {
      companyFunctionaryServiceStub = sinon.createStubInstance<CompanyFunctionaryService>(CompanyFunctionaryService);
      companyFunctionaryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompanyFunctionaryClass>(CompanyFunctionaryComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          companyFunctionaryService: () => companyFunctionaryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      companyFunctionaryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompanyFunctionarys();
      await comp.$nextTick();

      // THEN
      expect(companyFunctionaryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.companyFunctionaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      companyFunctionaryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(companyFunctionaryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.companyFunctionaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      companyFunctionaryServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(companyFunctionaryServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      companyFunctionaryServiceStub.retrieve.reset();
      companyFunctionaryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(companyFunctionaryServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.companyFunctionaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      companyFunctionaryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompanyFunctionary();
      await comp.$nextTick();

      // THEN
      expect(companyFunctionaryServiceStub.delete.called).toBeTruthy();
      expect(companyFunctionaryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
