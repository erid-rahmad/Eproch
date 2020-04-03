/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ReferenceListDetailComponent from '@/entities/reference-list/reference-list-details.vue';
import ReferenceListClass from '@/entities/reference-list/reference-list-details.component';
import ReferenceListService from '@/entities/reference-list/reference-list.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ReferenceList Management Detail Component', () => {
    let wrapper: Wrapper<ReferenceListClass>;
    let comp: ReferenceListClass;
    let referenceListServiceStub: SinonStubbedInstance<ReferenceListService>;

    beforeEach(() => {
      referenceListServiceStub = sinon.createStubInstance<ReferenceListService>(ReferenceListService);

      wrapper = shallowMount<ReferenceListClass>(ReferenceListDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { referenceListService: () => referenceListServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundReferenceList = { id: 123 };
        referenceListServiceStub.find.resolves(foundReferenceList);

        // WHEN
        comp.retrieveReferenceList(123);
        await comp.$nextTick();

        // THEN
        expect(comp.referenceList).toBe(foundReferenceList);
      });
    });
  });
});
