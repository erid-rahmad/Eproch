/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RegionDetailComponent from '@/entities/region/region-details.vue';
import RegionClass from '@/entities/region/region-details.component';
import RegionService from '@/entities/region/region.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Region Management Detail Component', () => {
    let wrapper: Wrapper<RegionClass>;
    let comp: RegionClass;
    let regionServiceStub: SinonStubbedInstance<RegionService>;

    beforeEach(() => {
      regionServiceStub = sinon.createStubInstance<RegionService>(RegionService);

      wrapper = shallowMount<RegionClass>(RegionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { regionService: () => regionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegion = { id: 123 };
        regionServiceStub.find.resolves(foundRegion);

        // WHEN
        comp.retrieveRegion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.region).toBe(foundRegion);
      });
    });
  });
});
