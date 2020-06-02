/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADTableUpdateComponent from '@/entities/ad-table/ad-table-update.vue';
import ADTableClass from '@/entities/ad-table/ad-table-update.component';
import ADTableService from '@/entities/ad-table/ad-table.service';

import ADColumnService from '@/entities/ad-column/ad-column.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADTable Management Update Component', () => {
    let wrapper: Wrapper<ADTableClass>;
    let comp: ADTableClass;
    let aDTableServiceStub: SinonStubbedInstance<ADTableService>;

    beforeEach(() => {
      aDTableServiceStub = sinon.createStubInstance<ADTableService>(ADTableService);

      wrapper = shallowMount<ADTableClass>(ADTableUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDTableService: () => aDTableServiceStub,

          aDColumnService: () => new ADColumnService(),

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDTable = entity;
        aDTableServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDTableServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDTable = entity;
        aDTableServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDTableServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
