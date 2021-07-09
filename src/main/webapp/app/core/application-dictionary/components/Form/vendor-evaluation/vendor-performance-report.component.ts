import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorAnalis from './vendor-analis.vue';

@Component({
  components: {
    VendorAnalis
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

  performanceReports = [];

  categories = [];
  subCategories = [];

  vendors = [];

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

    this.commonService("/api/m-vendor-performance-reports")
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
        this.performanceReports.forEach((elem)=>{
          elem.rating = elem.evaluationScore - elem.complaints;
        })
        if((<any[]>res.data).length){
          this.setRow(res.data[0]);
        }
      });
  }

  mounted() {
    this.refreshContent();
    
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
  }

  private setRow(record: any) {
    this.selectedRow = record;
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetailFunc(row){
    this.viewDetail = true
    this.selectedRow = row;
  }
}
