/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CurrencyDetailComponent from '@/entities/currency/currency-details.vue';
import CurrencyClass from '@/entities/currency/currency-details.component';
import CurrencyService from '@/entities/currency/currency.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Currency Management Detail Component', () => {
    let wrapper: Wrapper<CurrencyClass>;
    let comp: CurrencyClass;
    let currencyServiceStub: SinonStubbedInstance<CurrencyService>;

    beforeEach(() => {
      currencyServiceStub = sinon.createStubInstance<CurrencyService>(CurrencyService);

      wrapper = shallowMount<CurrencyClass>(CurrencyDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { currencyService: () => currencyServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCurrency = { id: 123 };
        currencyServiceStub.find.resolves(foundCurrency);

        // WHEN
        comp.retrieveCurrency(123);
        await comp.$nextTick();

        // THEN
        expect(comp.currency).toBe(foundCurrency);
      });
    });
  });
});
