import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const baseApiAuctionContent = 'api/m-auction-contents';
const baseApiAuctionItem = 'api/m-auction-items';
const baseApiAuctionParticipant = 'api/m-auction-participants';
const baseApiAuctionRule = 'api/m-auction-rules';
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

  loadingContent: boolean = false;
  loadingRule: boolean = false;
  loadingTeams: boolean = false;
  loadingParticipants: boolean = false;
  loadingItems: boolean = false;

  activeGroups: string[] = ['INF'];
  loadedGroups: Set<string> = new Set('INF');

  activatedGroupActions: Map<string, Function> = new Map([
    ['INF', () => {}]
  ]);

  auction: any = {};
  auctionRule: any = {};
  content: any = {};
  teams: any[] = [];
  participants: any[] = [];
  items: any[] = [];

  roleOptions: any[] = [];

  // Reference lists that is related to Auction Rules.
  bidPrevPeriodOptions: any[] = [];
  bidImprovementUnitOptions: any[] = [];
  tieBidsRuleOptions: any[] = [];


  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  onCollapseChanged(items: string[]) {
    items.filter(item => !this.loadedGroups.has(item))
      .forEach(item => {
        if (this.activatedGroupActions.has(item)) {
          this.activatedGroupActions.get(item)();
          this.loadedGroups.add(item);
        }
      });
  }

  created() {
    this.auction = {...this.data};

    this.activatedGroupActions.set('CTN', () => this.retrieveContent(this.data.contentId));
    this.activatedGroupActions.set('ITM', () => this.retrieveItems(this.data.id));
    this.activatedGroupActions.set('PCP', () => this.retrieveParticipants(this.data.id));

    this.activatedGroupActions.set('RUL', () => {
      this.retrieveBidPrevPeriodOptions();
      this.retrieveBidImprovementUnitOptions();
      this.retrieveTieBidsRuleOptions();
      this.retrieveRule(this.data.ruleId);
    });

    this.activatedGroupActions.set('TEM', () => {
      this.retrieveRoleOptions();
      this.retrieveTeams(this.data.id);
    });
  }

  printBidPrevPeriodName(value: string) {
    return this.bidPrevPeriodOptions
      .find(item => item.value === value)?.name || value;
  }

  printBidImprovementUnitName(value: string) {
    return this.bidImprovementUnitOptions
      .find(item => item.value === value)?.name || value;
  }

  printTieBidsRuleName(value: string) {
    return this.tieBidsRuleOptions
      .find(item => item.value === value)?.name || value;
  }

  printRoleName(value: string) {
    return this.roleOptions
      .find(role => role.value === value)?.name || value;
  }

  private retrieveContent(contentId: number) {
    this.loadingContent = true;
    this.commonService(baseApiAuctionContent)
      .find(contentId)
      .then(res => this.content = res)
      .finally(() => this.loadingContent = false);
  }

  private retrieveRule(ruleId: number) {
    this.loadingRule = true;
    this.commonService(baseApiAuctionRule)
      .find(ruleId)
      .then(res => this.auctionRule = res)
      .finally(() => this.loadingRule = false);
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

  private retrieveParticipants(auctionId: number) {
    this.loadingParticipants = true;
    this.commonService(baseApiAuctionParticipant)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => this.participants = res.data)
      .finally(() => this.loadingParticipants = false);
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

  // Reference lists data retrieval.

  /**
   * Retrieve reference list for Auction Rule > Bid Prev Period.
   */
  private retrieveBidPrevPeriodOptions() {
    this.commonService(null)
      .retrieveReferenceLists('bidPrevPeriodOptions')
      .then(res => this.bidPrevPeriodOptions = res);
  }

  /**
   * Retrieve refernce list for Auction Rule > Bid Improvement Unit.
   */
  private retrieveBidImprovementUnitOptions() {
    this.commonService(null)
      .retrieveReferenceLists('bidImprovementUnitOptions')
      .then(res => this.bidImprovementUnitOptions = res);
  }

  /**
   * Retrieve refernce list for Auction Rule > Tie Bids Rule.
   */
  private retrieveTieBidsRuleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('tieBidsRuleOptions')
      .then(res => this.tieBidsRuleOptions = res);
  }

  /**
   * Retrieve refernce list for Auction Team > Member Role.
   */
  private retrieveRoleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('auctionTeamRoles')
      .then(ref => this.roleOptions = ref);
  }

  public save() {
    console.log('Saving auction items...');
  }
}