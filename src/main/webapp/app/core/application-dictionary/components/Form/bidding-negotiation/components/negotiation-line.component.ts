import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import BiddingNegotiationLineConversation from './line-conversation.vue';
import settings from '@/settings';

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

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  created() {
    console.log(this.data);
    this.negotiation = {...this.data};

    this.refreshLine();

    var today = new Date();

    this.allowNegotiation = ((new Date(this.negotiation.startDate)).getTime()<today.getTime());
    console.log(this.allowNegotiation);
  }
  
  viewNegotiationWindow(row:any){
    //hides the outer toolbar (which closes the whole detail)
    (<any>document.querySelector("#innerToolbar")).style.display = "none";
    row.biddingTitle = this.negotiation.biddingTitle;
    row.biddingId = this.negotiation.biddingId;
    row.biddingNo = this.negotiation.biddingNo;
    row.biddingType = this.negotiation.biddingType;
    this.selectedRow = row;
    this.innerIndex = false;
  }

  closeLine(){
    (<any>document.querySelector("#innerToolbar")).style.display = "";
    this.innerIndex = true;

    this.refreshLine();
  }

  refreshLine(){
    this.agreed = [];
    this.inProgress = [];
    this.disagreed= [];
    this.notStarted = [];
    
    this.commonService(this.negotiationLineApi).retrieve({
      criteriaQuery: this.updateCriteria([
        //'active.equals=true',
        `negotiationId.equals=${this.negotiation.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      if(this.isVendor){
        this.inProgress = res.data.filter((line)=>{return line.vendorId == AccountStoreModule.vendorInfo.id && 
        line.negotiationStatus === 'in progress'})
      } else {
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
      }
    });
  }

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }
}