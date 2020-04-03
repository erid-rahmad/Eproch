/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PersonInChargeComponent from '@/entities/person-in-charge/person-in-charge.vue';
import PersonInChargeClass from '@/entities/person-in-charge/person-in-charge.component';
import PersonInChargeService from '@/entities/person-in-charge/person-in-charge.service';

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
  describe('PersonInCharge Management Component', () => {
    let wrapper: Wrapper<PersonInChargeClass>;
    let comp: PersonInChargeClass;
    let personInChargeServiceStub: SinonStubbedInstance<PersonInChargeService>;

    beforeEach(() => {
      personInChargeServiceStub = sinon.createStubInstance<PersonInChargeService>(PersonInChargeService);
      personInChargeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersonInChargeClass>(PersonInChargeComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          personInChargeService: () => personInChargeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      personInChargeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersonInCharges();
      await comp.$nextTick();

      // THEN
      expect(personInChargeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personInCharges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      personInChargeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(personInChargeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personInCharges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      personInChargeServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(personInChargeServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      personInChargeServiceStub.retrieve.reset();
      personInChargeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(personInChargeServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.personInCharges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      personInChargeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePersonInCharge();
      await comp.$nextTick();

      // THEN
      expect(personInChargeServiceStub.delete.called).toBeTruthy();
      expect(personInChargeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
