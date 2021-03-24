import { Component, Vue } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

@Component
export default class AccessLevelMixin extends Vue {

  protected adOrganizationId: number;

  protected organizationCriteria = [
    'adOrganizationId.in=1'
  ];

  created() {
    this.adOrganizationId = accountStore.organizationInfo.id;

    if (this.adOrganizationId > 1) {
      this.organizationCriteria.push(`adOrganizationId.in=${this.adOrganizationId}`);
    }
  }

  protected updateCriteria(criteria: string[]) {
    if (this.adOrganizationId > 1) {
      return [...criteria, ...this.organizationCriteria];
    }

    return criteria;
  }

}