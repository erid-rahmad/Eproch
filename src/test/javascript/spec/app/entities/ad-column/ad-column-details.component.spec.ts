/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADColumnDetailComponent from '@/entities/ad-column/ad-column-details.vue';
import ADColumnClass from '@/entities/ad-column/ad-column-details.component';
import ADColumnService from '@/entities/ad-column/ad-column.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADColumn Management Detail Component', () => {
    let wrapper: Wrapper<ADColumnClass>;
    let comp: ADColumnClass;
    let aDColumnServiceStub: SinonStubbedInstance<ADColumnService>;

    beforeEach(() => {
      aDColumnServiceStub = sinon.createStubInstance<ADColumnService>(ADColumnService);

      wrapper = shallowMount<ADColumnClass>(ADColumnDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDColumnService: () => aDColumnServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADColumn = { id: 123 };
        aDColumnServiceStub.find.resolves(foundADColumn);

        // WHEN
        comp.retrieveADColumn(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDColumn).toBe(foundADColumn);
      });
    });
  });
});
