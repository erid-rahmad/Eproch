/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADTabUpdateComponent from '@/entities/ad-tab/ad-tab-update.vue';
import ADTabClass from '@/entities/ad-tab/ad-tab-update.component';
import ADTabService from '@/entities/ad-tab/ad-tab.service';

import ADFieldService from '@/entities/ad-field/ad-field.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import ADTableService from '@/entities/ad-table/ad-table.service';

import ADColumnService from '@/entities/ad-column/ad-column.service';

import ADWindowService from '@/entities/ad-window/ad-window.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADTab Management Update Component', () => {
    let wrapper: Wrapper<ADTabClass>;
    let comp: ADTabClass;
    let aDTabServiceStub: SinonStubbedInstance<ADTabService>;

    beforeEach(() => {
      aDTabServiceStub = sinon.createStubInstance<ADTabService>(ADTabService);

      wrapper = shallowMount<ADTabClass>(ADTabUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDTabService: () => aDTabServiceStub,

          aDFieldService: () => new ADFieldService(),

          aDOrganizationService: () => new ADOrganizationService(),

          aDTableService: () => new ADTableService(),

          aDColumnService: () => new ADColumnService(),

          aDWindowService: () => new ADWindowService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDTab = entity;
        aDTabServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDTabServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDTab = entity;
        aDTabServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDTabServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
