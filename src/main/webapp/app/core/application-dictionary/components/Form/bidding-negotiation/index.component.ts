import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import BiddingNegotiationLine from './components/negotiation-line.vue';
import BiddingSchedule from "@/core/application-dictionary/components/Form/bidding/submission/bidding-schedule.vue";

@Component({
  components: {
    BiddingNegotiationLine,
    BiddingSchedule
  }
})
export default class BiddingNegotiation extends mixins(AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index = true;
  displayTable = true;
  loading = true;
  loadingSummary = true;

  showSchedule=false;
  showSummary=false;
  showVendor=false;

  biddingNegotiations: any[] = [];
  biddingStates: any[] = [];
  biddingSchedule: any[] = [];
  negoSummary: any[] = [];

  negotiationsApi = '/api/m-bidding-negotiations';
  scheduleApi = '/api/m-bidding-schedules';
  negotiationLineApi = '/api/m-bidding-negotiation-lines';
  vendorConfirmationApi = '/api/m-vendor-confirmations';
  vendorConfirmationLineApi = '/api/m-vendor-confirmation-lines';

  selectedRow: any = {};

  today:Date = new Date();
  
  // for paging
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;

  vcExist = false;

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  created() {
    this.refreshHeader();

    this.today = new Date();

    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        console.log(res);
        this.biddingStates = res.map(item => ({ key: item.value, value: item.name }));
      });
  }

  formatBiddingStatus(value: string) {
    return this.biddingStates.find(status => status.key === value)?.value;
  }

  formatEvalStatus(value: string){
    switch(value){
      case "SMT": return "Submitted"
      case "RJC": return "Rejected"
      case "APP": return "Approved"
      case "DRF": return "Dratfed"
      default: return value
    }
  }

  viewSchedule(row: any){
    this.showSchedule = true;
    this.selectedRow = row;
    /*
    this.commonService(this.scheduleApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${row.biddingId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res => {
      console.log(res);
      this.biddingSchedule = res.data;
    });
    */
  }

  closeSchedule(){
    this.biddingSchedule = [];
    this.showSchedule = false;
  }

  viewDetail(row){
    this.selectedRow = row;
    this.index = false;
    this.displayTable = false;
  }

  viewSummary(row){
    this.selectedRow = row;
    this.showSummary = true;
    this.loadingSummary = true;
    this.commonService(this.negotiationLineApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `negotiationId.equals=${this.selectedRow.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      this.negoSummary = res.data;
      this.commonService(this.vendorConfirmationApi).retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${this.selectedRow.biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      }).then((res)=>{
        if(res.data.length) {
          this.vcExist = true;
          this.commonService(this.vendorConfirmationLineApi).retrieve({
            criteriaQuery: this.updateCriteria([
              'active.equals=true',
              `vendorConfirmationId.equals=${res.data[0].id}`
            ]),
            paginationQuery: {
              page: 0,
              size: 10000,
              sort: ['id']
            }
          }).then((res)=>{
            res.data.forEach(line => {
              this.negoSummary.forEach((e)=>{
                if(e.vendorId == line.vendorId){
                  e.checkmark = true;
                }
              })
            });
          }).finally(()=>{
            this.loadingSummary = false;
          })
        } else {
          this.loadingSummary = false;
        }
      })
    })
  }

  clearSummary(){
    this.negoSummary = [];
    this.showSummary = false;
    this.vcExist = false;
    this.refreshHeader();
  }

  viewSchedule2(row){
    
  }

  closeDetail(){
    this.index = true;
    this.displayTable = true;
    this.refreshHeader();
  }

  refreshHeader(){
    this.loading=true;
    this.commonService(this.negotiationsApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true'
      ]),
      paginationQuery: {
        page: this.page-1,
        size: this.itemsPerPage,
        sort: ['id']
      }
    }).then(res => {
      console.log(res.data);
      this.biddingNegotiations = (<any[]>res.data)/*.filter((elem)=>{
        return elem.biddingStatus!=='F' && elem.evaluationStatus!=='APP';
      });*/

      this.totalItems = Number(res.headers['x-total-count']);
      this.queryCount = this.totalItems;
    }).finally(()=>{
      this.loading=false;
    });
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    this.refreshHeader();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.refreshHeader();
    }
  }

  public transition(): void {
    this.refreshHeader();
  }

  public clear(): void {
    this.page = 1;
    this.refreshHeader();
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  viewJoinVendor(negoId:number){
    this.commonService(this.negotiationLineApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `negotiationId.equals=${negoId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      this.negoSummary = res.data;
    });
    this.showVendor=true;
  }

  closeVendorScreen(){
    this.negoSummary = [];
    this.showVendor = false;
    this.refreshHeader();
  }

  createConfirmation(){
    //finalize negotiation phase
    let winningLines: any[] = [];
    this.negoSummary.forEach((elem)=>{
      if(elem.checkmark) {
        winningLines.push(elem);
      }
    })
    console.log(winningLines, winningLines.length)
    if(winningLines.length) {
      this.selectedRow.line = winningLines;
      this.commonService(`${this.vendorConfirmationApi}/generate`).create(this.selectedRow).then((res)=>{
        this.$message.success("Winner(s) chosen successfully.");
        this.clearSummary();
      }).catch((error)=>{
        console.log(error);
        this.$message.error("Failed creating vendor confirmation.");
      })
    } else {
      this.$message.error("Please choose at least 1 winner.");
    }
  }
}