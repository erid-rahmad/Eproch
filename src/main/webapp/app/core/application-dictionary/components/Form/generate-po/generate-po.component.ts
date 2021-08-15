import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

@Component
export default class GeneratePo extends Vue {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  rowGutter = 24;
  generating = false;
  loadingPr = false;
  loadingPrLines = false;

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
  paymentTermOptions: any[] = [];

  filter = {
    requisitionNo: null,
    vendorId: null
  };

  form = {
    adOrganizationId: accountStore.organizationInfo.id,
    costCenterId: null,
    currencyId: null,
    datePromised: new Date(),
    documentTypeId: null,
    paymentTermId: null,
    tax: false,
    warehouseId: null,
    mRfqLine: []
  }

  mainFormValidationSchema = {
    datePromised: {
      required: true,
      message: 'Date Ordered is required'
    },
    paymentTermId: {
      required: true,
      message: 'Payment Term is required'
    },
    currencyId: {
      required: true,
      message: 'Currency is required'
    },
    warehouseId: {
      required: true,
      message: 'Warehouse is required'
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

  created() {
    this.retrieveDropDownOptions('/api/c-vendors', 'vendorOptions');
    this.retrieveDropDownOptions('/api/c-warehouses', 'warehouseOptions');
    this.retrieveDropDownOptions('/api/c-currencies', 'currencyOptions');
    this.retrieveDropDownOptions('/api/c-payment-terms', 'paymentTermOptions');
  }

  mounted() {
    (<ElInput>this.$refs.requisitionNo).focus();
  }

  retrievePurchaseRequisitionLines(requisitionNo?: number): void {
    this.loadingPrLines = true;
    this.mainTable.clearSelection();

    const prNo = requisitionNo || this.filter.requisitionNo;
    const filterQuery = [
      'active.equals=true',
      'quantityBalance.greaterThan=0',
      'quotationMethod.contains=P'
    ];

    if (!!prNo) {
      filterQuery.push(`quotationNo.contains=${prNo}`);
    }

    console.log("this filter",filterQuery)
    this.commonService('/api/m-rfq-lines')
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
          line.quantityOrdered = line.quantityBalance;
          line.quantityMax = line.quantityBalance;
          line.quantityBalance = 0;
          line.orderAmount = line.quantityOrdered * line.unitPrice;
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
    row.quantityBalance = row.quantityMax - value;
    row.orderAmount = value * row.unitPrice;

    // this.$set(this.gridData, index, line);
  }

  public onSelectionChanged(selection: any) {
    this.form.mRfqLine = selection;
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
    this.retrievePurchaseRequisitionLines(this.filter.requisitionNo);
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

  generatePurchaseOrder() {
    if (! this.form.mRfqLine.length) {
      this.$message.error('Please select one or more lines');
      return;
    }

    (<ElForm>this.$refs.mainForm).validate((passed, error) => {
      if (passed) {
        console.log("this form",this.form)
        this.generating = true;
        this.commonService('/api/m-purchase-orders/generate')
          .create(this.form)
          .then(res => {
            console.log('PO generated. response: %O', res);
            const count = res.length === 1 ? ` #${res[0].documentNo}` : '(s)';
            this.$message.success(`Purchase Order${count} has been created successfully.`);
            this.gridData=null;
          })
          .catch(err => {
            console.log('Failed generating purchase order(s). %O', err);
            this.$message.error('Failed generating purchase order(s)');
          })
          .finally(() => {
            this.generating = false;
          })
      }
    });
  }

}
