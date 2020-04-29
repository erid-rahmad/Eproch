/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADClientUpdateComponent from '@/entities/ad-client/ad-client-update.vue';
import ADClientClass from '@/entities/ad-client/ad-client-update.component';
import ADClientService from '@/entities/ad-client/ad-client.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADClient Management Update Component', () => {
    let wrapper: Wrapper<ADClientClass>;
    let comp: ADClientClass;
    let aDClientServiceStub: SinonStubbedInstance<ADClientService>;

    beforeEach(() => {
      aDClientServiceStub = sinon.createStubInstance<ADClientService>(ADClientService);

      wrapper = shallowMount<ADClientClass>(ADClientUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDClientService: () => aDClientServiceStub,

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDClient = entity;
        aDClientServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDClientServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDClient = entity;
        aDClientServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDClientServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
