import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElSelect } from 'element-ui/types/select';
import { ElForm } from 'element-ui/types/form';

const baseApiAuctions = 'api/m-auctions';
const baseApiAuctionRule = 'api/m-auction-rules';

const AuctionRuleProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class AuctionRule extends Mixins(AccessLevelMixin, AuctionRuleProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingRule: boolean = false;

  auction: any = {};

  auctionRule: any = {};

  auctionRuleValidationSchema = {
    bidPrevPeriod: {
      required: true,
      message: 'Place Bid During Preview Period is required'
    },
    bidImprovementUnit: {
      required: true,
      message: 'Improve Bid Amount is required'
    },
    tieBidsRule: {
      required: true,
      message: 'Tie Bids Rule is required'
    }
  };

  // Dropdown field options.
  bidPrevPeriodOptions: any[] = [];
  bidImprovementUnitOptions: any[] = [];
  tieBidsRuleOptions: any[] = [];

  gutterSize: number = 24;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateTimeDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  created() {
    this.auction = {...this.data};
    this.retrieveBidPrevPeriodOptions();
    this.retrieveBidImprovementUnitOptions();
    this.retrieveTieBidsRuleOptions();

    if (this.data.ruleId) {
      this.retrieveRule(this.data.ruleId);
    } else {
      this.auctionRule = {
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        bidPrevPeriod: null,
        preBidEndDate: null,
        startDate: null,
        firstLotRunTime: null,
        bidRankOvertime: null,
        startOvertimeWithin: null,
        overtimePeriod: null,
        estAwardDate: null,
        bidImprovementUnit: null,
        tieBidsRule: null,
        showResponse: null,
        showLeader: null
      };
    }
  }

  mounted() {
    if (!this.data.ruleId) {
      this.$nextTick(() => {
        (<ElSelect>this.$refs.bidPrevPeriod).focus();
      });
    }
  }

  private retrieveRule(ruleId: number) {
    this.loadingRule = true;
    this.commonService(baseApiAuctionRule)
      .find(ruleId)
      .then(res => this.auctionRule = res)
      .finally(() => this.loadingRule = false);
  }

  private retrieveBidPrevPeriodOptions() {
    this.commonService(null)
      .retrieveReferenceLists('bidPrevPeriodOptions')
      .then(res => this.bidPrevPeriodOptions = res);
  }

  private retrieveBidImprovementUnitOptions() {
    this.commonService(null)
      .retrieveReferenceLists('bidImprovementUnitOptions')
      .then(res => this.bidImprovementUnitOptions = res);
  }

  private retrieveTieBidsRuleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('tieBidsRuleOptions')
      .then(res => this.tieBidsRuleOptions = res);
  }

  public save() {
    (<ElForm>this.$refs.auctionRuleForm).validate(async passed => {
      if (passed) {
        const newRecord = !this.auctionRule.id;
        this.loadingRule = true;

        try {
          let res = await this.commonService(baseApiAuctionRule)
            [newRecord ? 'create' : 'update'](this.auctionRule);
          
          this.auctionRule = { ...this.auctionRule, ...res };

          // Apply the rule to the auction.
          if (newRecord) {
            this.auction.ruleId = this.auctionRule.id;
            res = await this.commonService(baseApiAuctions)
              .update(this.auction);

            this.$emit('saved', res);
          }

          this.$message.success(`Auction rule has been saved successfully`);
        } catch(err) {
            console.error('Failed to save auction rule', err);
            this.$message.error('Failed to save auction rule');
        } finally {
          this.loadingRule = false
        };
      }
    });
  }
}