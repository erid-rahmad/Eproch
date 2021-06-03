import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import BiddingNegotiationLineConversation from './line-conversation.vue';

const NegotiationLineProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
    outerIndex: {
      type: Boolean
    }
  }
})

@Component({
  components:{
    BiddingNegotiationLineConversation
  }
})
export default class BiddingNegotiationLine extends mixins(AccessLevelMixin, NegotiationLineProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  negotiation: any = {}
  allowNegotiation = false;
  innerIndex = true;

  selectedRow:any = {};

  agreed: any[] = [];
  inProgress: any[] = [];
  disagreed: any[] = [];
  notStarted: any[] = [];

  negotiationLineApi = '/api/m-bidding-negotiation-lines';

  created() {
    console.log(this.data);
    this.negotiation = {...this.data};

    this.commonService(this.negotiationLineApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `negotiationId.equals=${this.negotiation.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      res.data.forEach(element => {
        switch(element.negotiationStatus){
          case 'not started': {
            this.notStarted.push(element);
            break;
          }
          case 'in progress': {
            this.inProgress.push(element);
            break;
          }
          case 'disagreed': {
            this.disagreed.push(element);
            break;
          }
          case 'agreed': {
            this.agreed.push(element);
            break;
          }
          default: {
            this.notStarted.push(element);
            break;
          }
        }
      });
    })

    var startDate = new Date(this.negotiation.startDate);
    var today = new Date();

    this.allowNegotiation = (startDate.getTime()<today.getTime());
    console.log(this.allowNegotiation);
  }
  
  viewNegotiationWindow(row:any){
    //hides the outer toolbar (which closes the whole detail)
    this.outerIndex = true;
    row.biddingTitle = this.negotiation.biddingTitle;
    this.selectedRow = row;
    this.innerIndex = false;
  }

  closeLine(){
    this.outerIndex = false;
    this.innerIndex = true;
  }
}