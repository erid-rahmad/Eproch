import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';

@Component({
  components: {
    AdInputLookup
  }
})
export default class GenerateRfq extends Vue {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  rowGutter = 24;
  generating = false;
  loadingPr = false;
  loadingPrLines = false;

  submit = false;
  finalSubmit = false;
  requestForm = {
    title:"",
    selectionMethod:"",
    dateTrx:"",
    dateRequired:"",
    description:"",
    totalAmount:0
  }

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 450
  };

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;


  dialogConfirmationVisible: boolean = false;
  gridData: any[] = [];
  currencyOptions: any[] = [];
  vendorOptions: any[] = [];
  warehouseOptions: any[] = [];
  classificationOptions: any[] = [];
  //paymentTermOptions: any[] = [];
  organizationOptions: any[] = [];

  filter = {
    requisitionNo: null,
    vendorId: null,
    documentTypeId: null
  };

  form:any = {
    adOrganizationId: accountStore.organizationInfo.id,
    businessClassificationId: null,
    costCenterId: null,
    currencyId: null,
    documentTypeId: null,
    documentAction: 'SMT',
    documentStatus: 'DRF',
    //paymentTermId: null,
    //tax: false,
    warehouseId: null,
    active: true,
    requisitionLines: []
  }

  mainFormValidationSchema = {
    warehouseId: {
      required: true,
      message: 'Warehouse is required'
    },
    businessClassificationId: {
      required: true,
      message: 'Classification is required'
    },
    currencyId: {
      required: true,
      message: 'Currency is required'
    },
    adOrganizationId: {
      required: true,
      message: 'Organization is required'
    }
  };

  reqFormValidationSchema = {
    title: {
      required: true,
      message: 'Title is required'
    },
    selectionMethod: {
      required: true,
      message: 'Selection Method is required'
    },
    dateTrx: {
      required: true,
      message: 'Date Trx is required'
    },
    dateRequired: {
      required: true,
      message: 'Date Required is required'
    }
  };

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  private get mainTable() {
    return this.$refs.mainTable as ElTable;
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

  created() {
    this.retrieveDropDownOptions('/api/c-vendors', 'vendorOptions');
    this.retrieveDropDownOptions('/api/c-warehouses', 'warehouseOptions');
    this.retrieveDropDownOptions('/api/c-currencies', 'currencyOptions');
    this.retrieveDropDownOptions('/api/c-business-categories', 'classificationOptions');
    this.retrieveDropDownOptions('/api/ad-organizations', 'organizationOptions');
  }

  mounted() {
    (<ElInput>this.$refs.requisitionNo).focus();
  }

  retrievePurchaseRequisitionLines(requisitionNo?: number, vendorId?: number): void {
    this.loadingPrLines = true;
    this.mainTable.clearSelection();

    const prNo = requisitionNo || this.filter.requisitionNo;
    const filterQuery = [
      'active.equals=true',
      'quantityBalance.greaterThan=0',
      'requisitionProcessed.equals=true',
      'requisitionApproved.equals=true'
    ];

    if (!!prNo) {
      filterQuery.push(`requisitionNo.contains=${prNo}`);
    }

    if (!!vendorId) {
      filterQuery.push(`vendorId.equals=${vendorId}`)
    }

    if (this.form.adOrganizationId>1) {
      filterQuery.push(`adOrganizationId.equals=${this.form.adOrganizationId}`);
    }

    this.commonService('/api/m-requisition-lines')
      .retrieve({
        criteriaQuery: filterQuery,
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: this.sort()
        }
      })
      .then(res => {
        const lines = res.data as any[];

        console.log(lines)

        if (lines.length) {
          if (lines.length == 1) {
            this.$set(this.filter, 'requisitionNo', lines[0].requisitionName);
          } else {
            const line = lines[0];

            if (! lines.some(l => l.requisitionName !== line.requisitionName)) {
              this.$set(this.filter, 'requisitionNo', lines[0].requisitionName);
            }
          }
        }

        this.gridData = res.data.map((line: any) => {
          line.orderAmount = line.quantityBalance * line.unitPrice;
          line.originalQuantity = line.quantity;
          line.quantity = line.quantityBalance;
          return line;
        });

        this.mainTable.toggleAllSelection();
      })
      .catch(err => {
        console.error('Failed getting the requisition lines. %O', err);
        this.$message.error(err.detail || err.message);
      })
      .finally(() => {
        this.loadingPrLines = false;
      });
  }

  /**
   * Action to be performed upon quantity ordered change event.
   * Update the quantityBalance by subtracting quantity and quantityOrdered.
   * @param row Current row being edited.
   * @param index The row index.
   * @param value The ordered quantity.
   */
  onQuantityOrderedChanged(row: any, index: number, value: number) {
    // const line = {...row};
    row.quantityBalance = row.quantity - value;
    row.orderAmount = row.quantityBalance * row.unitPrice;

    // this.$set(this.gridData, index, line);
  }

  public onSelectionChanged(selection: any) {
    this.form.requisitionLines = selection;
  }

  onVendorChange(vendorId: number) {
    this.retrievePurchaseRequisitionLines(this.filter.requisitionNo, vendorId);
  }

  public changeOrder(propOrder: any): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  /* public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    //this.retrieveAllRecords();
  } */

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  /* public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  } */

  /* @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  } */

  public transition(): void {
    this.retrievePurchaseRequisitionLines(this.filter.requisitionNo, this.filter.vendorId);
  }

  /* public clear(): void {
    this.page = 1;
    //this.retrieveAllRecords();
  } */

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

  generateQuotation() {
    if (! this.form.requisitionLines.length) {
      this.$message.error('Please select one or more lines');
      return;
    }

    (<ElForm>this.$refs.mainForm).validate((passed, error) => {
      if (passed) {
        this.requestForm.totalAmount=0;
        this.form.requisitionLines.forEach((elem)=>{
          this.requestForm.totalAmount+=elem.quantityOrdered*elem.unitPrice;
        })
        this.submit = true;
      }
    });
  }

  finalSubmitCheck(){
    (<ElForm>this.$refs.reqForm).validate((passed, error) => {
      if(passed) this.finalSubmit = true;
    });
  }

  generate(){
    this.form = {...this.form, ...this.requestForm};
    this.form.requisitionLines.forEach((el)=>{
      el.quantity = el.originalQuantity;
    })
    console.log(this.form);
    
    this.generating = true;
    this.commonService('/api/m-rfqs/generate')
    .create(this.form)
    .then(res => {
      console.log('Quotation generated. response: %O', res);
      const count = res.length === 1 ? ` #${res[0].documentNo}` : '(s)';
      this.$message.success(`Quotations${count} has been created successfully.`);
      this.finalSubmit = false;
      this.submit = false;
    })
    .catch(err => {
      console.log('Failed generating quotation(s). %O', err);
      this.$message.error('Failed generating quotation(s)');
    })
    .finally(() => {
      this.generating = false;
    })
    
  }
}
