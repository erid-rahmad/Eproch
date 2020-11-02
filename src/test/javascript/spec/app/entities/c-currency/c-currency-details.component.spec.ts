/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CCurrencyDetailComponent from '@/entities/c-currency/c-currency-details.vue';
import CCurrencyClass from '@/entities/c-currency/c-currency-details.component';
import CCurrencyService from '@/entities/c-currency/c-currency.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CCurrency Management Detail Component', () => {
    let wrapper: Wrapper<CCurrencyClass>;
    let comp: CCurrencyClass;
    let cCurrencyServiceStub: SinonStubbedInstance<CCurrencyService>;

    beforeEach(() => {
      cCurrencyServiceStub = sinon.createStubInstance<CCurrencyService>(CCurrencyService);

      wrapper = shallowMount<CCurrencyClass>(CCurrencyDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cCurrencyService: () => cCurrencyServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCCurrency = { id: 123 };
        cCurrencyServiceStub.find.resolves(foundCCurrency);

        // WHEN
        comp.retrieveCCurrency(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cCurrency).toBe(foundCCurrency);
      });
    });
  });
});
