/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BusinessCategoryDetailComponent from '@/entities/business-category/business-category-details.vue';
import BusinessCategoryClass from '@/entities/business-category/business-category-details.component';
import BusinessCategoryService from '@/entities/business-category/business-category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BusinessCategory Management Detail Component', () => {
    let wrapper: Wrapper<BusinessCategoryClass>;
    let comp: BusinessCategoryClass;
    let businessCategoryServiceStub: SinonStubbedInstance<BusinessCategoryService>;

    beforeEach(() => {
      businessCategoryServiceStub = sinon.createStubInstance<BusinessCategoryService>(BusinessCategoryService);

      wrapper = shallowMount<BusinessCategoryClass>(BusinessCategoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { businessCategoryService: () => businessCategoryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBusinessCategory = { id: 123 };
        businessCategoryServiceStub.find.resolves(foundBusinessCategory);

        // WHEN
        comp.retrieveBusinessCategory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.businessCategory).toBe(foundBusinessCategory);
      });
    });
  });
});
