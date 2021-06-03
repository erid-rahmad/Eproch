import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const baseApiAuctionItem = 'api/m-auction-items';
const baseApiAuctionTeam = 'api/m-auction-teams';

const AuctionSummaryProps = Vue.extend({
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
export default class AuctionSummary extends Mixins(AccessLevelMixin, AuctionSummaryProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingTeams: boolean = false;
  loadingItems: boolean = false;

  auction: any = {};

  teams: any[] = [];
  items: any[] = [];

  roleOptions: any[] = [];

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  created() {
    this.auction = {...this.data};
    this.retrieveRoleOptions();
    this.retrieveTeams(this.data.id);
    this.retrieveItems(this.data.id);
  }

  printRoleName(value: string) {
    return this.roleOptions.find(role => role.value === value)?.name || value;
  }

  private retrieveTeams(auctionId: number) {
    this.loadingTeams = true;
    this.commonService(baseApiAuctionTeam)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => this.teams = res.data)
      .finally(() => this.loadingTeams = false);
  }

  private retrieveItems(auctionId: number) {
    this.loadingItems = true;
    this.commonService(baseApiAuctionItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => this.items = res.data)
      .finally(() => this.loadingItems = false);
  }

  private retrieveRoleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('auctionTeamRoles')
      .then(ref => this.roleOptions = ref);
  }

  public save() {
    console.log('Saving auction items...');
  }
}