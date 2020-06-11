/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AdValidationRuleDetailComponent from '@/entities/ad-validation-rule/ad-validation-rule-details.vue';
import AdValidationRuleClass from '@/entities/ad-validation-rule/ad-validation-rule-details.component';
import AdValidationRuleService from '@/entities/ad-validation-rule/ad-validation-rule.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AdValidationRule Management Detail Component', () => {
    let wrapper: Wrapper<AdValidationRuleClass>;
    let comp: AdValidationRuleClass;
    let adValidationRuleServiceStub: SinonStubbedInstance<AdValidationRuleService>;

    beforeEach(() => {
      adValidationRuleServiceStub = sinon.createStubInstance<AdValidationRuleService>(AdValidationRuleService);

      wrapper = shallowMount<AdValidationRuleClass>(AdValidationRuleDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { adValidationRuleService: () => adValidationRuleServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdValidationRule = { id: 123 };
        adValidationRuleServiceStub.find.resolves(foundAdValidationRule);

        // WHEN
        comp.retrieveAdValidationRule(123);
        await comp.$nextTick();

        // THEN
        expect(comp.adValidationRule).toBe(foundAdValidationRule);
      });
    });
  });
});
