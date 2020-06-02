/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADReferenceUpdateComponent from '@/entities/ad-reference/ad-reference-update.vue';
import ADReferenceClass from '@/entities/ad-reference/ad-reference-update.component';
import ADReferenceService from '@/entities/ad-reference/ad-reference.service';

import ADReferenceListService from '@/entities/ad-reference-list/ad-reference-list.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADReference Management Update Component', () => {
    let wrapper: Wrapper<ADReferenceClass>;
    let comp: ADReferenceClass;
    let aDReferenceServiceStub: SinonStubbedInstance<ADReferenceService>;

    beforeEach(() => {
      aDReferenceServiceStub = sinon.createStubInstance<ADReferenceService>(ADReferenceService);

      wrapper = shallowMount<ADReferenceClass>(ADReferenceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDReferenceService: () => aDReferenceServiceStub,

          aDReferenceListService: () => new ADReferenceListService(),

          aDOrganizationService: () => new ADOrganizationService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDReference = entity;
        aDReferenceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDReferenceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDReference = entity;
        aDReferenceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDReferenceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
