/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CLocationDetailComponent from '@/entities/c-location/c-location-details.vue';
import CLocationClass from '@/entities/c-location/c-location-details.component';
import CLocationService from '@/entities/c-location/c-location.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CLocation Management Detail Component', () => {
    let wrapper: Wrapper<CLocationClass>;
    let comp: CLocationClass;
    let cLocationServiceStub: SinonStubbedInstance<CLocationService>;

    beforeEach(() => {
      cLocationServiceStub = sinon.createStubInstance<CLocationService>(CLocationService);

      wrapper = shallowMount<CLocationClass>(CLocationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cLocationService: () => cLocationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCLocation = { id: 123 };
        cLocationServiceStub.find.resolves(foundCLocation);

        // WHEN
        comp.retrieveCLocation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cLocation).toBe(foundCLocation);
      });
    });
  });
});
