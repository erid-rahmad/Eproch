/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AdValidationRuleComponent from '@/entities/ad-validation-rule/ad-validation-rule.vue';
import AdValidationRuleClass from '@/entities/ad-validation-rule/ad-validation-rule.component';
import AdValidationRuleService from '@/entities/ad-validation-rule/ad-validation-rule.service';

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
  describe('AdValidationRule Management Component', () => {
    let wrapper: Wrapper<AdValidationRuleClass>;
    let comp: AdValidationRuleClass;
    let adValidationRuleServiceStub: SinonStubbedInstance<AdValidationRuleService>;

    beforeEach(() => {
      adValidationRuleServiceStub = sinon.createStubInstance<AdValidationRuleService>(AdValidationRuleService);
      adValidationRuleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdValidationRuleClass>(AdValidationRuleComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          adValidationRuleService: () => adValidationRuleServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adValidationRuleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdValidationRules();
      await comp.$nextTick();

      // THEN
      expect(adValidationRuleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adValidationRules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      adValidationRuleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(adValidationRuleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adValidationRules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      adValidationRuleServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(adValidationRuleServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      adValidationRuleServiceStub.retrieve.reset();
      adValidationRuleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(adValidationRuleServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.adValidationRules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      adValidationRuleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAdValidationRule();
      await comp.$nextTick();

      // THEN
      expect(adValidationRuleServiceStub.delete.called).toBeTruthy();
      expect(adValidationRuleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
