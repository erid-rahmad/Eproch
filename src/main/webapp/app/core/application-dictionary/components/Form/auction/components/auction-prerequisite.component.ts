import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiAuctionPrerequisite = 'api/c-auction-prerequisites';

const AuctionPrerequisiteProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component({
  components: {
    HtmlEditor
  }
})
export default class AuctionPrerequisite extends Mixins(AccessLevelMixin, AuctionPrerequisiteProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;

  auction: any = {};

  prerequisite: any = {};

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  created() {
    this.auction = {...this.data};

    if (this.data.prerequisiteId) {
      this.retrievePrerequisite(this.data.prerequisiteId);
    } else {
      this.prerequisite = {
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        description: null
      };
    }
  }

  private retrievePrerequisite(prerequisiteId: number) {
    this.loading = true;
    this.commonService(baseApiAuctionPrerequisite)
      .find(prerequisiteId)
      .then(res => this.prerequisite = res)
      .finally(() => this.loading = false);
  }

  public async save() {
    // No action for save prerequisite.
  }
}