/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADFieldDetailComponent from '@/entities/ad-field/ad-field-details.vue';
import ADFieldClass from '@/entities/ad-field/ad-field-details.component';
import ADFieldService from '@/entities/ad-field/ad-field.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADField Management Detail Component', () => {
    let wrapper: Wrapper<ADFieldClass>;
    let comp: ADFieldClass;
    let aDFieldServiceStub: SinonStubbedInstance<ADFieldService>;

    beforeEach(() => {
      aDFieldServiceStub = sinon.createStubInstance<ADFieldService>(ADFieldService);

      wrapper = shallowMount<ADFieldClass>(ADFieldDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDFieldService: () => aDFieldServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADField = { id: 123 };
        aDFieldServiceStub.find.resolves(foundADField);

        // WHEN
        comp.retrieveADField(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDField).toBe(foundADField);
      });
    });
  });
});
