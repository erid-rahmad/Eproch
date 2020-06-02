/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADColumnUpdateComponent from '@/entities/ad-column/ad-column-update.vue';
import ADColumnClass from '@/entities/ad-column/ad-column-update.component';
import ADColumnService from '@/entities/ad-column/ad-column.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import ADReferenceService from '@/entities/ad-reference/ad-reference.service';

import AdValidationRuleService from '@/entities/ad-validation-rule/ad-validation-rule.service';

import ADTableService from '@/entities/ad-table/ad-table.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADColumn Management Update Component', () => {
    let wrapper: Wrapper<ADColumnClass>;
    let comp: ADColumnClass;
    let aDColumnServiceStub: SinonStubbedInstance<ADColumnService>;

    beforeEach(() => {
      aDColumnServiceStub = sinon.createStubInstance<ADColumnService>(ADColumnService);

      wrapper = shallowMount<ADColumnClass>(ADColumnUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDColumnService: () => aDColumnServiceStub,

          aDOrganizationService: () => new ADOrganizationService(),

          aDReferenceService: () => new ADReferenceService(),

          adValidationRuleService: () => new AdValidationRuleService(),

          aDTableService: () => new ADTableService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDColumn = entity;
        aDColumnServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDColumnServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDColumn = entity;
        aDColumnServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDColumnServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
