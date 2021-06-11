import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class AccessLevelMixin extends Vue {

  protected adOrganizationId: number;

  protected additionalCriteria = [];

  get isEmployee() {
    return accountStore.isEmployee;
  }

  get isVendor() {
    return accountStore.isVendor;
  }

  get organizationInfo() {
    return accountStore.organizationInfo;
  }

  get vendorInfo() {
    return accountStore.vendorInfo;
  }

  created() {
    this.adOrganizationId = this.organizationInfo.id;

    if (this.adOrganizationId > 1) {
      this.additionalCriteria.push('adOrganizationId.in=1', `adOrganizationId.in=${this.adOrganizationId}`)
    }

    if (this.isVendor) {
      const vendorId = this.vendorInfo.id;
      this.additionalCriteria.push(`vendorId.equals=${vendorId}`);
    }
  }

  protected updateCriteria(criteria: string[]) {
    if (this.isVendor || this.adOrganizationId > 1) {
      return [...criteria, ...this.additionalCriteria];
    }

    return criteria;
  }

}