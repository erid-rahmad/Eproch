import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import {Component, Inject, Mixins} from 'vue-property-decorator';

import circularColorBar from '../componentsChart/circularColorBar.vue';
import liveData from '../componentsChart/liveData.vue';
import lineexample from '../componentsChart/simpleLine.vue';
import lineupdate from '../componentsChart/methodUpdate.vue';
import echart from '../componentsChart/echart-bar.vue';
import echartpie from '../componentsChart/echart-pie.vue';
import rateOfContract from '../componentsChart/rateOfContract.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const baseApiTopVendor ='api/pa-dashboards/topVendorPurchase';
const baseApiDocument ='api/pa-dashboards/mydocument';

@Component({
  components: {
    WatchList,circularColorBar,liveData,lineexample,lineupdate,echart,echartpie,rateOfContract
  }
})
export default class WatchList extends  Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private dataPO:any=[];
  private dataMyDocument:any=[];

  score=[
    {
      vendorName:'Sisteck',
      Ordered:'21.000',
      Returned:0,
      Availability:'91%',
      Defect:0,
      score:1.30
    },
    {
      vendorName:'Supplier1',
      Ordered:'21.000',
      Returned:0,
      Availability:'88%',
      Defect:0,
      score:1.30
    },
    {
      vendorName:'Supplier2',
      Ordered:'21.000',
      Returned:0,
      Availability:'77%',
      Defect:0,
      score:1.30
    }
  ]

  created() {
   this.retrievePO();
   this.retrieveMyDocument();
  }


  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }
  printStatus(status: string) {
    return this.documentStatuses[status];
  }


  retrievePO(){
    this.commonService(baseApiTopVendor)
      .retrieve({
        criteriaQuery: this.updateCriteria([

        ]),
        paginationQuery: {
          page: 0,
          size: 10,
          sort: ['id']
        }
      })
      .then(async res => {

        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.grandTotal;
          const bandB = b.grandTotal;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await res.data.sort(compare);
        this.dataPO = res.data;

      })
      .finally(()=>this.retrieveMyDocument());
  }

  retrieveMyDocument(){
    this.commonService(baseApiDocument)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10,
          sort: ['date']
        }
      })
      .then(async res => {
        const myDocument = res.data;
        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.date;
          const bandB = b.date;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await myDocument.sort(compare);
        this.dataMyDocument=myDocument;
      })
      .finally();
  }

}
