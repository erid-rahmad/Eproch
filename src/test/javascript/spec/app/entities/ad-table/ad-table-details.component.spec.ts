/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADTableDetailComponent from '@/entities/ad-table/ad-table-details.vue';
import ADTableClass from '@/entities/ad-table/ad-table-details.component';
import ADTableService from '@/entities/ad-table/ad-table.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADTable Management Detail Component', () => {
    let wrapper: Wrapper<ADTableClass>;
    let comp: ADTableClass;
    let aDTableServiceStub: SinonStubbedInstance<ADTableService>;

    beforeEach(() => {
      aDTableServiceStub = sinon.createStubInstance<ADTableService>(ADTableService);

      wrapper = shallowMount<ADTableClass>(ADTableDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDTableService: () => aDTableServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADTable = { id: 123 };
        aDTableServiceStub.find.resolves(foundADTable);

        // WHEN
        comp.retrieveADTable(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDTable).toBe(foundADTable);
      });
    });
  });
});
