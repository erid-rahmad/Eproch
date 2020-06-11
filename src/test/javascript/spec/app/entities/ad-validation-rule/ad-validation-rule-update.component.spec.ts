/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AdValidationRuleUpdateComponent from '@/entities/ad-validation-rule/ad-validation-rule-update.vue';
import AdValidationRuleClass from '@/entities/ad-validation-rule/ad-validation-rule-update.component';
import AdValidationRuleService from '@/entities/ad-validation-rule/ad-validation-rule.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AdValidationRule Management Update Component', () => {
    let wrapper: Wrapper<AdValidationRuleClass>;
    let comp: AdValidationRuleClass;
    let adValidationRuleServiceStub: SinonStubbedInstance<AdValidationRuleService>;

    beforeEach(() => {
      adValidationRuleServiceStub = sinon.createStubInstance<AdValidationRuleService>(AdValidationRuleService);

      wrapper = shallowMount<AdValidationRuleClass>(AdValidationRuleUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          adValidationRuleService: () => adValidationRuleServiceStub,

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.adValidationRule = entity;
        adValidationRuleServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adValidationRuleServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.adValidationRule = entity;
        adValidationRuleServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adValidationRuleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
