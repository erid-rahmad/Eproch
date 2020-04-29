/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ADFieldGroupUpdateComponent from '@/entities/ad-field-group/ad-field-group-update.vue';
import ADFieldGroupClass from '@/entities/ad-field-group/ad-field-group-update.component';
import ADFieldGroupService from '@/entities/ad-field-group/ad-field-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ADFieldGroup Management Update Component', () => {
    let wrapper: Wrapper<ADFieldGroupClass>;
    let comp: ADFieldGroupClass;
    let aDFieldGroupServiceStub: SinonStubbedInstance<ADFieldGroupService>;

    beforeEach(() => {
      aDFieldGroupServiceStub = sinon.createStubInstance<ADFieldGroupService>(ADFieldGroupService);

      wrapper = shallowMount<ADFieldGroupClass>(ADFieldGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          aDFieldGroupService: () => aDFieldGroupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.aDFieldGroup = entity;
        aDFieldGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDFieldGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aDFieldGroup = entity;
        aDFieldGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aDFieldGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
