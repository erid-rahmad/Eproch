/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PersonInChargeUpdateComponent from '@/entities/person-in-charge/person-in-charge-update.vue';
import PersonInChargeClass from '@/entities/person-in-charge/person-in-charge-update.component';
import PersonInChargeService from '@/entities/person-in-charge/person-in-charge.service';

import UserService from '@/admin/user-management/user-management.service';

import VendorService from '@/entities/vendor/vendor.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PersonInCharge Management Update Component', () => {
    let wrapper: Wrapper<PersonInChargeClass>;
    let comp: PersonInChargeClass;
    let personInChargeServiceStub: SinonStubbedInstance<PersonInChargeService>;

    beforeEach(() => {
      personInChargeServiceStub = sinon.createStubInstance<PersonInChargeService>(PersonInChargeService);

      wrapper = shallowMount<PersonInChargeClass>(PersonInChargeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          personInChargeService: () => personInChargeServiceStub,

          userService: () => new UserService(),

          vendorService: () => new VendorService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.personInCharge = entity;
        personInChargeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personInChargeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.personInCharge = entity;
        personInChargeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personInChargeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
