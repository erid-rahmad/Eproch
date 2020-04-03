import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVendor } from '@/shared/model/vendor.model';
import VendorService from './vendor.service';

@Component
export default class VendorDetails extends Vue {
  @Inject('vendorService') private vendorService: () => VendorService;
  public vendor: IVendor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vendorId) {
        vm.retrieveVendor(to.params.vendorId);
      }
    });
  }

  public retrieveVendor(vendorId) {
    this.vendorService()
      .find(vendorId)
      .then(res => {
        this.vendor = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
