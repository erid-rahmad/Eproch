/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ReferenceDetailComponent from '@/entities/reference/reference-details.vue';
import ReferenceClass from '@/entities/reference/reference-details.component';
import ReferenceService from '@/entities/reference/reference.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Reference Management Detail Component', () => {
    let wrapper: Wrapper<ReferenceClass>;
    let comp: ReferenceClass;
    let referenceServiceStub: SinonStubbedInstance<ReferenceService>;

    beforeEach(() => {
      referenceServiceStub = sinon.createStubInstance<ReferenceService>(ReferenceService);

      wrapper = shallowMount<ReferenceClass>(ReferenceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { referenceService: () => referenceServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundReference = { id: 123 };
        referenceServiceStub.find.resolves(foundReference);

        // WHEN
        comp.retrieveReference(123);
        await comp.$nextTick();

        // THEN
        expect(comp.reference).toBe(foundReference);
      });
    });
  });
});
