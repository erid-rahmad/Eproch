/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADFieldGroupDetailComponent from '@/entities/ad-field-group/ad-field-group-details.vue';
import ADFieldGroupClass from '@/entities/ad-field-group/ad-field-group-details.component';
import ADFieldGroupService from '@/entities/ad-field-group/ad-field-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADFieldGroup Management Detail Component', () => {
    let wrapper: Wrapper<ADFieldGroupClass>;
    let comp: ADFieldGroupClass;
    let aDFieldGroupServiceStub: SinonStubbedInstance<ADFieldGroupService>;

    beforeEach(() => {
      aDFieldGroupServiceStub = sinon.createStubInstance<ADFieldGroupService>(ADFieldGroupService);

      wrapper = shallowMount<ADFieldGroupClass>(ADFieldGroupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDFieldGroupService: () => aDFieldGroupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADFieldGroup = { id: 123 };
        aDFieldGroupServiceStub.find.resolves(foundADFieldGroup);

        // WHEN
        comp.retrieveADFieldGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDFieldGroup).toBe(foundADFieldGroup);
      });
    });
  });
});
