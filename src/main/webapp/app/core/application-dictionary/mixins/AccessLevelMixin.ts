import { Component, Vue } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

@Component
export default class AccessLevelMixin extends Vue {

  protected adOrganizationId: number;

  protected additionalCriteria = [
    'adOrganizationId.in=1'
  ];

  created() {
    this.adOrganizationId = accountStore.organizationInfo.id;

    if (this.adOrganizationId > 1) {
      this.additionalCriteria.push(`adOrganizationId.in=${this.adOrganizationId}`);
    }

    if (accountStore.isVendor) {
      const vendorId = accountStore.vendorInfo.id;
      this.additionalCriteria.push(`vendorId.equals=${vendorId}`);
    }
  }

  protected updateCriteria(criteria: string[]) {
    if (accountStore.isVendor || this.adOrganizationId > 1) {
      return [...criteria, ...this.additionalCriteria];
    }

    return criteria;
  }

}