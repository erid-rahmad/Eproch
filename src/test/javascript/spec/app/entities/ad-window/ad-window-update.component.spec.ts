/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADWindowUpdateComponent from '@/entities/ad-window/ad-window-update.vue';
import ADWindowClass from '@/entities/ad-window/ad-window-update.component';
import ADWindowService from '@/entities/ad-window/ad-window.service';

import ADTabService from '@/entities/ad-tab/ad-tab.service';

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
  describe('ADWindow Management Update Component', () => {
    let wrapper: Wrapper<ADWindowClass>;
    let comp: ADWindowClass;
    let aDWindowServiceStub: SinonStubbedInstance<ADWindowService>;

    beforeEach(() => {
      aDWindowServiceStub = sinon.createStubInstance<ADWindowService>(ADWindowService);

      wrapper = shallowMount<ADWindowClass>(ADWindowUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDWindowService: () => aDWindowServiceStub,

          aDTabService: () => new ADTabService(),

          aDClientService: () => new ADClientService(),

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDWindow = entity;
        aDWindowServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDWindowServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDWindow = entity;
        aDWindowServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDWindowServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
