/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADReferenceListUpdateComponent from '@/entities/ad-reference-list/ad-reference-list-update.vue';
import ADReferenceListClass from '@/entities/ad-reference-list/ad-reference-list-update.component';
import ADReferenceListService from '@/entities/ad-reference-list/ad-reference-list.service';

import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

import ADReferenceService from '@/entities/ad-reference/ad-reference.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADReferenceList Management Update Component', () => {
    let wrapper: Wrapper<ADReferenceListClass>;
    let comp: ADReferenceListClass;
    let aDReferenceListServiceStub: SinonStubbedInstance<ADReferenceListService>;

    beforeEach(() => {
      aDReferenceListServiceStub = sinon.createStubInstance<ADReferenceListService>(ADReferenceListService);

      wrapper = shallowMount<ADReferenceListClass>(ADReferenceListUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDReferenceListService: () => aDReferenceListServiceStub,

          aDOrganizationService: () => new ADOrganizationService(),

          aDReferenceService: () => new ADReferenceService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDReferenceList = entity;
        aDReferenceListServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDReferenceListServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDReferenceList = entity;
        aDReferenceListServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDReferenceListServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
