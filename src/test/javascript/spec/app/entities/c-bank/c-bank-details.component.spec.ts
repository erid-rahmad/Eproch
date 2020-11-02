/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CBankDetailComponent from '@/entities/c-bank/c-bank-details.vue';
import CBankClass from '@/entities/c-bank/c-bank-details.component';
import CBankService from '@/entities/c-bank/c-bank.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CBank Management Detail Component', () => {
    let wrapper: Wrapper<CBankClass>;
    let comp: CBankClass;
    let cBankServiceStub: SinonStubbedInstance<CBankService>;

    beforeEach(() => {
      cBankServiceStub = sinon.createStubInstance<CBankService>(CBankService);

      wrapper = shallowMount<CBankClass>(CBankDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cBankService: () => cBankServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCBank = { id: 123 };
        cBankServiceStub.find.resolves(foundCBank);

        // WHEN
        comp.retrieveCBank(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cBank).toBe(foundCBank);
      });
    });
  });
});
