/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CBankUpdateComponent from '@/entities/c-bank/c-bank-update.vue';
import CBankClass from '@/entities/c-bank/c-bank-update.component';
import CBankService from '@/entities/c-bank/c-bank.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CBank Management Update Component', () => {
    let wrapper: Wrapper<CBankClass>;
    let comp: CBankClass;
    let cBankServiceStub: SinonStubbedInstance<CBankService>;

    beforeEach(() => {
      cBankServiceStub = sinon.createStubInstance<CBankService>(CBankService);

      wrapper = shallowMount<CBankClass>(CBankUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cBankService: () => cBankServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cBank = entity;
        cBankServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cBankServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cBank = entity;
        cBankServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cBankServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
