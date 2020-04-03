/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompanyFunctionaryDetailComponent from '@/entities/company-functionary/company-functionary-details.vue';
import CompanyFunctionaryClass from '@/entities/company-functionary/company-functionary-details.component';
import CompanyFunctionaryService from '@/entities/company-functionary/company-functionary.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompanyFunctionary Management Detail Component', () => {
    let wrapper: Wrapper<CompanyFunctionaryClass>;
    let comp: CompanyFunctionaryClass;
    let companyFunctionaryServiceStub: SinonStubbedInstance<CompanyFunctionaryService>;

    beforeEach(() => {
      companyFunctionaryServiceStub = sinon.createStubInstance<CompanyFunctionaryService>(CompanyFunctionaryService);

      wrapper = shallowMount<CompanyFunctionaryClass>(CompanyFunctionaryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { companyFunctionaryService: () => companyFunctionaryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanyFunctionary = { id: 123 };
        companyFunctionaryServiceStub.find.resolves(foundCompanyFunctionary);

        // WHEN
        comp.retrieveCompanyFunctionary(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companyFunctionary).toBe(foundCompanyFunctionary);
      });
    });
  });
});
