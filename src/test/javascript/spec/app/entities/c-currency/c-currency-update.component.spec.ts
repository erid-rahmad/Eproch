/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CCurrencyUpdateComponent from '@/entities/c-currency/c-currency-update.vue';
import CCurrencyClass from '@/entities/c-currency/c-currency-update.component';
import CCurrencyService from '@/entities/c-currency/c-currency.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CCurrency Management Update Component', () => {
    let wrapper: Wrapper<CCurrencyClass>;
    let comp: CCurrencyClass;
    let cCurrencyServiceStub: SinonStubbedInstance<CCurrencyService>;

    beforeEach(() => {
      cCurrencyServiceStub = sinon.createStubInstance<CCurrencyService>(CCurrencyService);

      wrapper = shallowMount<CCurrencyClass>(CCurrencyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cCurrencyService: () => cCurrencyServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cCurrency = entity;
        cCurrencyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCurrencyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cCurrency = entity;
        cCurrencyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cCurrencyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
