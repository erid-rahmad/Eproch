/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ReferenceListUpdateComponent from '@/entities/reference-list/reference-list-update.vue';
import ReferenceListClass from '@/entities/reference-list/reference-list-update.component';
import ReferenceListService from '@/entities/reference-list/reference-list.service';

import ReferenceService from '@/entities/reference/reference.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ReferenceList Management Update Component', () => {
    let wrapper: Wrapper<ReferenceListClass>;
    let comp: ReferenceListClass;
    let referenceListServiceStub: SinonStubbedInstance<ReferenceListService>;

    beforeEach(() => {
      referenceListServiceStub = sinon.createStubInstance<ReferenceListService>(ReferenceListService);

      wrapper = shallowMount<ReferenceListClass>(ReferenceListUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          referenceListService: () => referenceListServiceStub,

          referenceService: () => new ReferenceService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.referenceList = entity;
        referenceListServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(referenceListServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.referenceList = entity;
        referenceListServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(referenceListServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
