/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ADOrganizationDetailComponent from '@/entities/ad-organization/ad-organization-details.vue';
import ADOrganizationClass from '@/entities/ad-organization/ad-organization-details.component';
import ADOrganizationService from '@/entities/ad-organization/ad-organization.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ADOrganization Management Detail Component', () => {
    let wrapper: Wrapper<ADOrganizationClass>;
    let comp: ADOrganizationClass;
    let aDOrganizationServiceStub: SinonStubbedInstance<ADOrganizationService>;

    beforeEach(() => {
      aDOrganizationServiceStub = sinon.createStubInstance<ADOrganizationService>(ADOrganizationService);

      wrapper = shallowMount<ADOrganizationClass>(ADOrganizationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { aDOrganizationService: () => aDOrganizationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundADOrganization = { id: 123 };
        aDOrganizationServiceStub.find.resolves(foundADOrganization);

        // WHEN
        comp.retrieveADOrganization(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aDOrganization).toBe(foundADOrganization);
      });
    });
  });
});
