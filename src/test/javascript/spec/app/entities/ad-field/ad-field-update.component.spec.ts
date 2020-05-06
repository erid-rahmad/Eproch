/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADFieldUpdateComponent from '@/entities/ad-field/ad-field-update.vue';
import ADFieldClass from '@/entities/ad-field/ad-field-update.component';
import ADFieldService from '@/entities/ad-field/ad-field.service';

import ADClientService from '@/entities/ad-client/ad-client.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import ADReferenceService from '@/entities/ad-reference/ad-reference.service';

import ADColumnService from '@/entities/ad-column/ad-column.service';

import ADTabService from '@/entities/ad-tab/ad-tab.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADField Management Update Component', () => {
    let wrapper: Wrapper<ADFieldClass>;
    let comp: ADFieldClass;
    let aDFieldServiceStub: SinonStubbedInstance<ADFieldService>;

    beforeEach(() => {
      aDFieldServiceStub = sinon.createStubInstance<ADFieldService>(ADFieldService);

      wrapper = shallowMount<ADFieldClass>(ADFieldUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDFieldService: () => aDFieldServiceStub,

          aDClientService: () => new ADClientService(),

          aDOrganizationService: () => new ADOrganizationService(),

          aDReferenceService: () => new ADReferenceService(),

          aDColumnService: () => new ADColumnService(),

          aDTabService: () => new ADTabService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDField = entity;
        aDFieldServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDFieldServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDField = entity;
        aDFieldServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDFieldServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
