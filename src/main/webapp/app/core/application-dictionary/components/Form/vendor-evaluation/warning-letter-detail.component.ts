import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const WarningLetterDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class WarningLetterDetail extends Mixins(AccessLevelMixin, WarningLetterDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 24;

  mainForm = {
    documentAction: null,
    documentStatus: null
  };

  vendorOptions: any[] = [];
  categoryOptions: any[] = [];

  loadingVendorList = false;

  warningTypes = [
    {
      id: 1,
      name: 'Admonition',
      value: 'A'
    },
    {
      id: 2,
      name: 'Admonition 1',
      value: 'A1'
    },
    {
      id: 3,
      name: 'Admonition 2',
      value: 'A2'
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get readOnly() {
    return this.mainForm.documentStatus === 'CLS' || this.mainForm.documentStatus === 'SMT';
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.mainForm = data;
  }

  created() {
    this.onDataChanged(this.data);
    if(!!(<any>this.mainForm).id) this.updateVendorList();
    
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
        this.categoryOptions = res.data.map((item: any) => {
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

  updateVendorList(): void {
    this.loadingVendorList = true;
    const filterQuery = [
      'active.equals=true'
    ];

    if (!!(<any>this.mainForm).businessCategoryId) {
      console.log((<any>this.mainForm).businessCategoryId)
      filterQuery.push(`businessCategoryId.equals=${(<any>this.mainForm).businessCategoryId}`);
    }

    if (!!(<any>this.mainForm).subBusinessCategoryId) {
      console.log((<any>this.mainForm).subBusinessCategoryId)
      filterQuery.push(`subBusinessCategoryId.equals=${(<any>this.mainForm).subBusinessCategoryId}`)
    }

    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: filterQuery,
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        let vendorIds:number[] = res.data.map((item)=>{return item.vendorId});
        let uniqueIds = [...new Set(vendorIds)];

        let vendorFilter: any[] = ['active.equals=true'];
        uniqueIds.forEach((id)=>{
          vendorFilter.push(`id.in=${id}`)
        });

        this.commonService("/api/c-vendors").retrieve({
          criteriaQuery: vendorFilter,
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id']
          }
        })
        .then(res => {
          this.vendorOptions = res.data.map((item: any) => {
            return {
              key: item.id,
              value: item.name,
              code: item.code
            };
          });
          this.loadingVendorList = false
        })
        .catch(err => {
          console.error('Failed getting the record. %O', err);
          this.$message({
            type: 'error',
            message: err.detail || err.message
          });
          this.loadingVendorList = false
        });
      })
      .catch(err => {
        console.log('Failed to get vendor from categories. %O', err);
        this.$message.error('Failed to get vendor from categories');
        this.loadingVendorList = false
      });
  }
}