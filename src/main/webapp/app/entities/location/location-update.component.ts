import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CityService from '../city/city.service';
import { ICity } from '@/shared/model/city.model';

import VendorService from '../vendor/vendor.service';
import { IVendor } from '@/shared/model/vendor.model';

import AlertService from '@/shared/alert/alert.service';
import { ILocation, Location } from '@/shared/model/location.model';
import LocationService from './location.service';

const validations: any = {
  location: {
    streetAddress: {
      required
    },
    postalCode: {},
    cityId: {
      required
    }
  }
};

@Component({
  validations
})
export default class LocationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('locationService') private locationService: () => LocationService;
  public location: ILocation = new Location();

  @Inject('cityService') private cityService: () => CityService;

  public cities: ICity[] = [];

  @Inject('vendorService') private vendorService: () => VendorService;

  public vendors: IVendor[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.locationId) {
        vm.retrieveLocation(to.params.locationId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.location.id) {
      this.locationService()
        .update(this.location)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.location.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.locationService()
        .create(this.location)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.location.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLocation(locationId): void {
    this.locationService()
      .find(locationId)
      .then(res => {
        this.location = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
    this.vendorService()
      .retrieve()
      .then(res => {
        this.vendors = res.data;
      });
  }
}
