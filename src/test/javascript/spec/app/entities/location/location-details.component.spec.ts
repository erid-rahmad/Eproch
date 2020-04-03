/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LocationDetailComponent from '@/entities/location/location-details.vue';
import LocationClass from '@/entities/location/location-details.component';
import LocationService from '@/entities/location/location.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Location Management Detail Component', () => {
    let wrapper: Wrapper<LocationClass>;
    let comp: LocationClass;
    let locationServiceStub: SinonStubbedInstance<LocationService>;

    beforeEach(() => {
      locationServiceStub = sinon.createStubInstance<LocationService>(LocationService);

      wrapper = shallowMount<LocationClass>(LocationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { locationService: () => locationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLocation = { id: 123 };
        locationServiceStub.find.resolves(foundLocation);

        // WHEN
        comp.retrieveLocation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.location).toBe(foundLocation);
      });
    });
  });
});
