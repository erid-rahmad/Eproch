import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import RfqDetail from './rfq-detail.vue';
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';

@Component({
  components: {
    RfqDetail
  }
})
export default class VendorPerformanceReport extends mixins(AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  viewDetail=false;

  columnSpacing = 24;
  selectedRow: any = {};

  filter = {
    categoryId: null,
    subCategoryId: null,
    vendorId: null
  }

  currentDate = new Date();

  performanceReports = [];

  categories = [];
  subCategories = [];

  vendors = [];

  showJoinedVendors = false;
  loadingJoinedVendors = false;
  joinedVendors: any[] = [];

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  refreshContent() {
    let filterQuery = [
      'active.equals=true'
    ]

    if(!!this.filter.vendorId){
      filterQuery.push(`vendorId.equals=${this.filter.vendorId}`)
    }
    if(!!this.filter.categoryId){
      filterQuery.push(`businessCategoryId.equals=${this.filter.categoryId}`)
    }

    this.commonService("/api/m-rfq-views")
      .retrieve({
        criteriaQuery: filterQuery,
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      })
      .then((res)=>{
        this.performanceReports = res.data;
        if((<any[]>res.data).length){
          this.setRow(res.data[0]);
        }
      });
  }

  mounted() {
    this.refreshContent();
    /*
    this.commonService("/api/c-business-categories")
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      })
      .then(res => {
        this.categories = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.code
          };
        });
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      });

      this.commonService("/api/c-vendors").retrieve({
        criteriaQuery: ['active.equals=true'],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendors = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.code
          };
        });
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      });
    */
  }

  private setRow(record: any) {
    this.selectedRow = record;
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetailFunc(row){
    this.viewDetail = true
    this.selectedRow = row;
  }

  formatDecimal(row: any, col: any): string{
    let val= Math.round(Number(row[col.property]) * 100 ) / 100 ;
    return `${val}`;
  }

  formatTimeRemaining(date) {
    if (!date) {
      return '-';
    }

    if (this.currentDate >= new Date(date)) {
      return '-';
    }

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(date)
    });

    return formatDuration(duration, {
      format: ['days', 'hours', 'minutes']
    });
  }

  viewJoinVendor(biddingId: number) {
    this.showJoinedVendors = true;
    this.loadingJoinedVendors = true;
    this.commonService("/api/m-rfq-submissions")
      .retrieve({
        criteriaQuery: [
          `quotationId.equals=${biddingId}`,
          'documentStatus.equals=SMT'
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => this.joinedVendors = res.data)
      .finally(() => this.loadingJoinedVendors = false);
  }

  showContract(){
    (<any>this.$refs.rfqDetail).createContract();
  }
}
