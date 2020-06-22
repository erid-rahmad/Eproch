/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CCountryDetailComponent from '@/entities/c-country/c-country-details.vue';
import CCountryClass from '@/entities/c-country/c-country-details.component';
import CCountryService from '@/entities/c-country/c-country.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CCountry Management Detail Component', () => {
    let wrapper: Wrapper<CCountryClass>;
    let comp: CCountryClass;
    let cCountryServiceStub: SinonStubbedInstance<CCountryService>;

    beforeEach(() => {
      cCountryServiceStub = sinon.createStubInstance<CCountryService>(CCountryService);

      wrapper = shallowMount<CCountryClass>(CCountryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cCountryService: () => cCountryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCCountry = { id: 123 };
        cCountryServiceStub.find.resolves(foundCCountry);

        // WHEN
        comp.retrieveCCountry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cCountry).toBe(foundCCountry);
      });
    });
  });
});
