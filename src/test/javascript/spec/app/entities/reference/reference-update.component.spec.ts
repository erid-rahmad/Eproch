/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ReferenceUpdateComponent from '@/entities/reference/reference-update.vue';
import ReferenceClass from '@/entities/reference/reference-update.component';
import ReferenceService from '@/entities/reference/reference.service';

import ReferenceListService from '@/entities/reference-list/reference-list.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Reference Management Update Component', () => {
    let wrapper: Wrapper<ReferenceClass>;
    let comp: ReferenceClass;
    let referenceServiceStub: SinonStubbedInstance<ReferenceService>;

    beforeEach(() => {
      referenceServiceStub = sinon.createStubInstance<ReferenceService>(ReferenceService);

      wrapper = shallowMount<ReferenceClass>(ReferenceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          referenceService: () => referenceServiceStub,

          referenceListService: () => new ReferenceListService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.reference = entity;
        referenceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(referenceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.reference = entity;
        referenceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(referenceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
