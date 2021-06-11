import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const baseApiAuction = 'api/m-auctions';

const AuctionInfoProps = Vue.extend({
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
    AdInputLookup
  }
})
export default class AuctionInfo extends Mixins(AccessLevelMixin, AuctionInfoProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;

  auction: any = {};

  validationSchema: any = {
    name: {
      required: true,
      message: 'Auction Title is required'
    },
    ownerUserId: {
      required: true,
      message: 'Owner is required'
    }
  }

  gutterSize: number = 24;

  get editMode() {
    return !!this.auction.id;
  }

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  created() {
    this.auction = {...this.data};
  }

  mounted() {
    this.$nextTick(() => {
      (<ElInput>this.$refs.documentNo).focus();
    });
  }

  public save() {
    (<ElForm>this.$refs.auctionInfoForm).validate(passed => {
      if (passed) {
        const newRecord = !this.auction.id;

        this.loading = true;
        this.commonService(baseApiAuction)
          [newRecord ? 'create' : 'update'](this.auction)
          .then(res => {
            this.auction = res;
            this.$message.success(`Auction has been ${newRecord ? 'created' : 'updated'} successfully`);
            this.$emit('saved', res);
          })
          .catch(err => {
            console.error('Failed to save the auction', err);
            this.$message.error('Failed to save the auction');
          })
          .finally(() => this.loading = false);
      }
    })
  }
}