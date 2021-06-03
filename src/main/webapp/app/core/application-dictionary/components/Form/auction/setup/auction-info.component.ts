import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';

const baseApiAuction = 'api/m-auctions';
const baseApiCurrency = 'api/c-currencies';
const baseApiCostCenter = 'api/c-cost-centers';
const baseApiUser = 'api/ad-users';

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

@Component
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

  currencyOptions: any[] = [];
  departmentOptions: any[] = [];
  ownerOptions: any[] = [];

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
    this.retrieveCurrencies();
    this.retrieveDepartments();
    this.retrieveOwners();
  }

  mounted() {
    this.$nextTick(() => {
      (<ElInput>this.$refs.documentNo).focus();
    });
  }

  private retrieveCurrencies() {
    this.commonService(baseApiCurrency)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ])
      })
      .then(res => this.currencyOptions = res.data);
  }

  private retrieveDepartments() {
    this.commonService(baseApiCostCenter)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ])
      })
      .then(res => this.departmentOptions = res.data);
  }

  private retrieveOwners() {
    this.commonService(baseApiUser)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'employee.equals=true'
        ])
      })
      .then(res => this.ownerOptions = res.data);
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