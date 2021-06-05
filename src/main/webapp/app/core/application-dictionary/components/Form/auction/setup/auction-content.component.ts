import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiAuctions = 'api/m-auctions';
const baseApiAuctionContent = 'api/m-auction-contents';

const AuctionContentProps = Vue.extend({
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
export default class AuctionContent extends Mixins(AccessLevelMixin, AuctionContentProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingContent: boolean = false;

  auction: any = {};

  content: any = {};

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  created() {
    this.auction = {...this.data};

    if (this.data.contentId) {
      this.retrieveContent(this.data.contentId);
    } else {
      this.content = {
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        description: null
      };
    }
  }

  private retrieveContent(contentId: number) {
    this.loadingContent = true;
    this.commonService(baseApiAuctionContent)
      .find(contentId)
      .then(res => this.content = res)
      .finally(() => this.loadingContent = false);
  }

  public async save() {
    const newRecord = !this.content.id;

    try {
      let res = await this.commonService(baseApiAuctionContent)
        [newRecord ? 'create' : 'update'](this.content);

      this.content = res;

      // Apply the content to the auction.
      if (newRecord) {
        this.auction.contentId = this.content.id;
        res = await this.commonService(baseApiAuctions)
          .update(this.auction);

        this.$emit('saved', res);
      }

      this.$message.success(`Content has been saved successfully`);
    } catch(err) {
        console.error('Failed to save the auction content', err);
        this.$message.error('Failed to save the content')
    }
  }
}