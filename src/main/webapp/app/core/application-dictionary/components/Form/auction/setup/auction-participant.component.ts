import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const baseApiAuctionParticipant = 'api/m-auction-participants';

const AuctionParticipantProps = Vue.extend({
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
export default class AuctionParticipant extends Mixins(AccessLevelMixin, AuctionParticipantProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingParticipants: boolean = false;

  items: any[] = [];

  created() {
    console.log('auction-participant created');
    this.retrieveItems(this.data.id);
  }

  private retrieveItems(auctionId: number) {
    this.loadingParticipants = true;
    this.commonService(baseApiAuctionParticipant)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => this.items = res.data)
      .finally(() => this.loadingParticipants = false);
  }

  public save() {
    console.log('Saving auction participants...');
  }
}