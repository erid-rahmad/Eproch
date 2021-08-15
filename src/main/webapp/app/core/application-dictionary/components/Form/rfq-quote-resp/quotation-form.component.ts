import settings from '@/settings';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { buildCascaderOptions } from '@/utils/form';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { ElTable } from 'element-ui/types/table';
import { Inject, Watch } from 'vue-property-decorator';
import Component, { mixins } from 'vue-class-component';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';

@Component({
  components: {
    AdInputLookup
  }
})
export default class QuotationForm extends mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  rowGutter = 24;
  generating = false;
  loading = false;
  quoteDialog = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 450
  };

  // for paging
  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  selectedRow: any = {};

  dialogConfirmationVisible: boolean = false;
  gridData: any[] = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get selectionTypeCheck() {
    return this.selectedRow.selectionMethod === 'A' || this.selectedRow.selectionMethod === 'S';
  }

  quoteForm: any = {
    dateRequired: "",
    businessCategoryId: "",
    suppliers: []
  }

  quoteFormValidationSchema = {
    dateRequired: {
      required: true,
      message: 'Date Required is required'
    },
  }

  pm = [{
    value: 'A',
    label: 'Direct Appointment'
  }, {
    value: 'S',
    label: 'Direct Selection'
  }, {
    value: 'P',
    label: 'Direct Purchase'
  }, {
    value: 'T',
    label: 'Tender'
  }, ]

  businessCategoryOptions: any[] = [];
  vendorOptions: any[] = [];
  subCategoryOptions: any[] = [];

  vendorInvitations: any[] = [];
  vendorSuggestions: any[] = [];

  loadingCategories = false;
  loadingSuggestions = false;
  businessCategory: any = {};
  businessCategorieValues = {
    id: '',
    name: ''
  };

  vendorSuggestion: any = {
    vendor: '',
    vendorObj: '',
    subCategoryObj: '',
    subCategory: '',
    addressId: '',
    address: ''
  };

  public rules = {
    values: {
      type: 'array',
      required: true,
      defaultFields: {type: 'array', len: 3, required: true},
      message: 'Business Category is required'
    }
  };
  
  vendorInvitationFormVisible: boolean = false;
  dialogConfirmationVisibleVendorSuggestion: boolean = false;

  created() {
    this.refreshHeader();
    this.retrieveBusinessCategories();
    this.retrieveSubBusinessCategories();
  }

  refreshHeader(){
    this.loading=true;
    this.commonService('/api/m-rfqs').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true'
      ]),
      paginationQuery: {
        page: this.page-1,
        size: this.itemsPerPage,
        sort: ['id']
      }
    })
    .then(res => {
      this.gridData = res.data;
      console.log(this.gridData);
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

  public changeOrder(propOrder: any): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  onSelectionChanged(row: any) {
    this.selectedRow = row;
  }

  formatSelection(method: string){
    return method ? this.pm.find(el=>el.value==method)?.label : '';
  }

  public retrieveDropDownOptions(baseApi: string, prop: string): void {
    this.commonService(baseApi)
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
      })
      .then(res => {
        this[prop] = res.data.map((item: any) => {
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

  showQuoteDialog(){
    this.quoteForm.dateRequired = this.selectedRow.dateRequired;
    this.quoteDialog = true;
  }

  private retrieveBusinessCategories() {
    this.commonService('/api/c-business-categories')
      .retrieve({
        criteriaQuery: this.updateCriteria(['active.equals=true']),
        paginationQuery: {
          size: 1000,
          page: 0,
          sort: ['name']
        }
      })
      .then(res => {
        let categories = res.data.map(item => {
          return {
            value: item.id,
            label: item.name,
            parent: item.parentCategoryId
          };
        });

        // Use set to uniquely identify added category.
        this.businessCategoryOptions = buildCascaderOptions(categories);
      });
  }

  private retrieveSubBusinessCategories(): void {
    this.commonService("/api/c-business-categories")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'sector.equals=TERTIARY'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.subCategoryOptions = res.data;
      });
  }

  clearSubCategory(){
    this.vendorSuggestion.vendor = "";
    this.vendorSuggestion.vendorObj = "";
    this.vendorOptions = []
    this.vendorSuggestion.address = "";
    this.vendorSuggestion.addressId = "";
  }

  clearVendor(){
    this.vendorSuggestion.address = "";
    this.vendorSuggestion.addressId = "";
  }

  addBusinessCategory() {
    this.vendorInvitationFormVisible = true;
  }

  addVendorSuggestion() {
    this.dialogConfirmationVisibleVendorSuggestion = true;
  }

  saveBusinessCategory() {
    const nodes = (<any>this.$refs.businessCategories).getCheckedNodes();
    const classifications = new Set<number>();
    const vendorInvitations = this.vendorInvitations || [];
    let vendorSuggestionCriteria: Set<string> = new Set(this.updateCriteria[
      'active.equals=true'
    ]);

    nodes.forEach(element => {
      const path = element.path;
      const pathLabels = element.pathLabels;

      if (element.level === 3) {
        const vendorInvitation = {
          adOrganizationId: this.adOrganizationId,
          businessClassificationId: path[0],
          businessClassificationName: pathLabels[0],
          businessCategoryId: path[1],
          businessCategoryName: pathLabels[1],
          businessSubCategoryId: path[2],
          businessSubCategoryName: pathLabels[2]
        };

        const lineExist = (vendorInvitations.some((vLine: any) => {
          return vLine.businessSubCategoryId === vendorInvitation.businessSubCategoryId;
        }));

        if (!lineExist) {
          vendorInvitations.push(vendorInvitation);

          //if (this.vendorSelection === 'IVT') {
            vendorSuggestionCriteria.add(`subBusinessCategoryId.in=${path[2]}`);
          /*} else {
            if (! classifications.has(path[0])) {
              classifications.add(path[0]);
              vendorSuggestionCriteria.add(`businessClassificationId.in=${path[0]}`);
            }
          }*/
        }
      }
    });

    this.onVendorInvitationChanged(Array.from(vendorSuggestionCriteria));
    this.vendorInvitationFormVisible = false;
  }

  private onVendorInvitationChanged(criteria: string[]) {
    this.loadingSuggestions = true;
    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: this.updateCriteria(criteria),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        const categories = res.data as any[];
        const locationCriteria = categories.map(category => `vendorId.in=${category.vendorId}`);
        const subCategoryMap = new Map<number, any>();

        for (const category of categories) {
          subCategoryMap.set(category.vendorId, {
            subCategoryId: category.subBusinessCategoryId,
            subCategoryName: category.subBusinessCategoryName
          });
        }

        this.vendorOptions = categories;
        this.retrieveVendorLocations(locationCriteria, subCategoryMap);
      })
      .catch(err => {
        console.log('Failed to get business categories. %O', err);
        this.$message.error('Failed to get business categories');
        this.loadingSuggestions = false;
      });
  }

  private retrieveVendorLocations(criteria: string[], subCategoryMap?: Map<number, any>) {
    this.commonService('/api/c-vendor-locations')
      .retrieve({
        criteriaQuery: this.updateCriteria(criteria),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        let sugs = (res.data as any[]).map(location => {
          let subCategory = {
            subCategoryId: null,
            subCategoryName: null
          };

          if (subCategoryMap) {
            subCategory = subCategoryMap.get(location.vendorId);
          }

          try{
            return {
              vendorId: location.vendorId,
              vendorName: location.vendorRegisteredName,
              businessSubCategoryId: subCategory.subCategoryId,
              businessSubCategoryName: subCategory.subCategoryName,
              locationId: location.locationId,
              address: location.locationName + ', ' + location.cityName
            };
          } catch(e){
            console.log(e);
            return {};
          }
        });
        if(sugs.length>0){
          sugs.forEach((elem)=>{
            if(this.vendorSuggestions.length==0 || this.vendorSuggestions.findIndex(e=>elem.vendorId==e.vendorId)==-1){
              this.vendorSuggestions.push(elem);
            }
          })
        }
      })
      .catch(err => {
        console.log('Failed to get vendor suggestions. %O', err);
        this.$message.error('Failed to get the vendor suggestions');
      })
      .finally(() => {
        this.loadingSuggestions = false;
      })
  }

  saveVendorSuggestion() {
    this.vendorSuggestion.vendorObj = this.vendorOptions.find(item => item.vendorId === this.vendorSuggestion.vendor);
    this.vendorSuggestion.subCategoryObj = this.subCategoryOptions.find(item => item.id === this.vendorSuggestion.subCategory);
    this.vendorSuggestions.push({ ...this.vendorSuggestion });

    this.dialogConfirmationVisibleVendorSuggestion = false;

    this.vendorSuggestion = {
      vendor: '',
      vendorObj: '',
      subCategoryObj: '',
      subCategory: '',
      addressId: '',
      address: ''
    }
  }

  onBusinessCategoryChanged(values) {
    console.log('onBusinessCategoryChanged. values:', values);
  }

  removeBusinessCategory(index) {
    const removedItem = this.vendorInvitations.splice(index, 1);
  }

  removeVendorSuggestion(index) {
    const removedItem = this.vendorSuggestions.splice(index, 1);
  }

  saveQuotation(){
    if(this.vendorSuggestions.length==0){
      this.$message.error("Requires at least 1 vendor.");
      return;
    }
    this.vendorSuggestions.forEach((elem)=>{
      let inv = this.vendorInvitations.find(e=>e.businessSubCategoryId==elem.businessSubCategoryId);
      elem.businessCategoryId = inv.businessCategoryId;
      elem.businessClassificationId = inv.businessClassificationId;
      elem.quotationId = this.selectedRow.id;
      elem.dateRequired = this.selectedRow.dateRequired;
    })
    this.commonService('/api/m-quote-suppliers/save-all').create(this.vendorSuggestions)
    .then((res)=>{
      this.quoteDialog = false;
      this.$message.success("Successfully created Supplier Quotations");
    }).catch((err)=>{
      console.log(err);
      this.$message.error("Failed creating Supplier Quotations");
    })
  }
}
