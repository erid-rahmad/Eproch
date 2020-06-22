/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CRegionDetailComponent from '@/entities/c-region/c-region-details.vue';
import CRegionClass from '@/entities/c-region/c-region-details.component';
import CRegionService from '@/entities/c-region/c-region.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CRegion Management Detail Component', () => {
    let wrapper: Wrapper<CRegionClass>;
    let comp: CRegionClass;
    let cRegionServiceStub: SinonStubbedInstance<CRegionService>;

    beforeEach(() => {
      cRegionServiceStub = sinon.createStubInstance<CRegionService>(CRegionService);

      wrapper = shallowMount<CRegionClass>(CRegionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cRegionService: () => cRegionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCRegion = { id: 123 };
        cRegionServiceStub.find.resolves(foundCRegion);

        // WHEN
        comp.retrieveCRegion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cRegion).toBe(foundCRegion);
      });
    });
  });
});
