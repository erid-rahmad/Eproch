import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const baseApiAuctions = 'api/m-auctions';
const baseApiAuctionInvitations = 'api/m-auction-invitations';
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
    AdInputLookup,
    HtmlEditor
  }
})
export default class AuctionPrerequisite extends Mixins(AccessLevelMixin, AuctionPrerequisiteProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  acceptConfirmationVisible: boolean = false;
  loading: boolean = false;
  loadingPrerequisite: boolean = false;
  selectedAction: string = null;

  auction: any = {};
  acceptPrerequisite: boolean = null;

  auctionValidationSchema: any = {
    prerequisiteId: {
      required: true,
      message: 'Prerequisite Template is required'
    }
  };

  prerequisite: any = {};

  prerequisiteOptions: any[] = [];

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  get isInvitation() {
    return this.auction.documentTypeName === 'Auction Invitation';
  }

  get readOnly() {
    return this.isInvitation || (this.auction.documentStatus && this.auction.documentStatus !== 'DRF');
  }

  onAcceptConfirmationClose() {
    this.acceptPrerequisite = null;
    this.selectedAction = null;
    this.acceptConfirmationVisible = false;
  }

  onPrerequisiteChanged(id: number) {
    this.prerequisite.description = this.prerequisiteOptions.find(item => item.id === id)?.description;
  }

  onPrerequisiteConfirmed(accept: boolean) {
    this.selectedAction = accept ? 'Accept' : 'Decline';
    this.acceptConfirmationVisible = true;
  }

  created() {
    this.auction = {...this.data};

    if (this.auction.documentStatus === 'ACC') {
      this.acceptPrerequisite = true;
    } else if (this.auction.documentStatus === 'DCL') {
      this.acceptPrerequisite = false;
    }

    if (this.data.prerequisiteId) {
      this.retrievePrerequisite(this.data.prerequisiteId);
    }
  }

  confirmPrerequisite() {
    this.auction.documentAction = this.acceptPrerequisite ? 'ACC' : 'DCL';

    this.commonService(baseApiAuctionInvitations)
      .update(this.auction)
      .then(res => {
        this.$emit('saved', res);
        this.$message.success(`You have been ${this.selectedAction.toLowerCase()}ed the prerequisite`);
      })
      .catch(err => {
        console.error('Faild to save your confirmation', err);
        this.$message.error('Failed to save your confirmation');
        this.acceptPrerequisite = null;
      })
      .finally(() => {
        this.acceptConfirmationVisible = false;
      });
  }

  private retrievePrerequisite(prerequisiteId: number) {
    this.loadingPrerequisite = true;
    this.commonService(baseApiAuctionPrerequisite)
      .find(prerequisiteId)
      .then(res => this.prerequisite = res)
      .finally(() => this.loadingPrerequisite = false);
  }

  public save() {
    this.loading = true;
    (<ElForm>this.$refs.mainForm).validate(passed => {
      if (passed) {
        this.commonService(baseApiAuctions)
          .update(this.auction)
          .then(res => {
            this.$message.success('Prerequisite template has been applied successfully');
            this.$emit('saved', res);
          })
          .catch(err => {
            console.error('Failed to apply the prerequisite template', err);
            this.$message.error('Failed to apply the prerequisite template');
          })
          .finally(() => this.loading = false);
      }
    });
  }
}