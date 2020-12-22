import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import MatchPo from './match-po.vue';
import { ElForm } from 'element-ui/types/form';

const EVerificationUpdateProps = Vue.extend({
  props: {
    formUpdate: {
      type: Object,
      default: () => {}
    },
    docStatus: {
      type: Array,
      default: () => []
    },
  }
})

@Component({
  components: {
    MatchPo
  }
})
export default class EVerificationUpdate extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, EVerificationUpdateProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 470,
    height: 370
  };
  rules = {
    taxInvoice: [
      { min: 15, message: 'Length must be 13 digits' }
    ]
  }

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  private totalItems = 0;

  private baseApiUrlEVerification = "/api/m-verifications";
  private baseApiUrlEVerificationLine = "/api/m-verification-lines";
  private baseApiUrlCurrency = "/api/c-currencies";
  private baseApiUrlTaxInvoice = "/api/c-tax-invoices";
  public gridData: Array<any> = [];
  public removedLines: Array<any> = [];
  enofaList: any[] = [];
  lastTaxInvoice: string = "";
  statTaxInvoice: boolean = false;
  isDraft: boolean = false;

  private processing = false;
  private fullscreenLoading: boolean = false;

  public currencyOptions: any[] = [];

  public dialogMatchPoVisible: boolean = false;
  private totalAmount: number = null;

  public matchPo = {};
  public modeFilterMatchPo: any = {};
  private filterQuery: string = "";

  header: Record<string, any> = {};

  retrieveEnofa() {
    this.dynamicWindowService(this.baseApiUrlTaxInvoice)
      .retrieve({
        criteriaQuery: [`vendorId.equals=${this.header.vendorId}`],
        paginationQuery: {
          sort: ['id,asc'],
          page: 0,
          size: 1000
        }
      })
      .then(res => this.enofaList = res.data);
  }

  eVerification = {
    form: {},
    line: [],
    remove: []
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created() {
    this.header = Object.assign({}, this.header, this.formUpdate, {
      vendorId: accountStore.userDetails.cVendorId,
      vendorName: accountStore.userDetails.cVendorName
    });

    this.retrieveCurrencies();
    this.retrieveEnofa();

    if (!this.header.verificationStatus || this.header.verificationStatus == "DRF") {
      this.$set(this.header, 'verificationStatus', 'DRF');
      this.isDraft = true;
    } else {
      this.isDraft = false;
    }

    if (this.header.id) {
      this.filterQuery = `verificationId.equals=${this.header.id}`;
      this.retrieveEVerificationLine();
    }
  }

  changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  // =====================================
  // Pagination
  changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    this.transition();
  }

  loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition(): void {
    if (this.header.id) {
      this.filterQuery = `verificationId.equals=${this.header.id}`;
      this.retrieveEVerificationLine();
    }
  }
  // =====================================

  public displayMatchPo(id){
    let width, titleModal;
    if(id == 1){
      width = "90%";
      titleModal = "Receipt Order";
    }else{
      width = "40%";
      titleModal = "Add By Receipt No";
    }
    this.modeFilterMatchPo = {
      mode: id,
      width: width,
      titleModal: titleModal,
      filterByReceiptNo: ''
    };
    this.dialogMatchPoVisible = true;
  }

  public closeEVerificationUpdate() {
    this.$emit('close-e-verification-update');
  }

  updateEVerification() {
    if (this.gridData.length) {
      (this.$refs.eVerificationUpdate as ElForm).validate((passed, errors) => {
        if (passed) {
          this.fullscreenLoading = true;
          if(this.header.taxable){
            this.checkVerification(this.header.taxInvoice);
          } else {
            this.statTaxInvoice = true;
          }
          this.submit();
        } else {
          return false;
        }
      })

    } else {
      this.$message({
        message: 'Please add at least an invoice line',
        type: 'error'
      })
    }
  }

  private notifWarning(message: string){
    this.$notify({
      title: 'Warning',
      message: message,
      type: 'warning',
      duration: 3000
    });
  }

  private retrieveEVerificationLine(): void {
    if ( ! this.baseApiUrlEVerificationLine) {
      return;
    }

    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrlEVerificationLine)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data.map((item: any) => {
          item.totalAmount = item.totalLines + item.taxAmount;
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.processing = false;
      });
  }

  private retrieveCurrencies() {
    this.dynamicWindowService(this.baseApiUrlCurrency)
    .retrieve()
    .then(res => {
        let currencyList = res.data.map(item => {
            return{
                key: item.id,
                value: item.code
            };
        });
        this.currencyOptions = currencyList;

    });
  }

  addAllLines(data: any[]) {
    const lines = this.transformMatchPo(data);

    for (const line of lines) {
      const lineExist = (this.gridData.some((vLine: any) => vLine.id === line.id));

      if (!lineExist) {
        this.gridData.push(line);
      }
    }
    this.calculateLines();
    this.dialogMatchPoVisible = false;
  }

  addSelectedLines() {
    const lines = this.transformMatchPo((<any>this.$refs.matchPo)?.selectedLines || []);

    for (const line of lines) {
      const lineExist = (this.gridData.some((vLine: any) => vLine.id === line.id));

      if (!lineExist) {
        this.gridData.push(line);
      }
    }
    this.calculateLines();
    this.dialogMatchPoVisible = false;
  }

  private calculateLines() {
    let totalLines = 0;
    let foreignTotalLines = 0;
    let taxAmount = 0;
    let foreignTaxAmount = 0;
    let totalAmount = 0;
    let foreignTotalAmount = 0;

    for (const row of this.gridData) {
      totalLines += row.totalLines;
      taxAmount += row.taxAmount;
      totalAmount += row.totalAmount;
      foreignTotalLines += row.foreignTotalAmount;
      foreignTaxAmount += row.foreignTaxAmount;
      foreignTotalAmount += foreignTotalLines + foreignTaxAmount;
    }

    const firstRow = this.gridData[0];

    this.header = Object.assign({}, this.header, {
      cTaxCategoryId: firstRow?.cTaxCategoryId,
      cTaxId: firstRow?.cTaxId,
      currencyId: firstRow?.cCurrencyId,
      matchPoCurrencyId: firstRow?.cCurrencyId,
      taxable: firstRow?.taxable,
      totalLines: totalLines,
      taxAmount: taxAmount,
      grandTotal: totalAmount,
      foreignTaxAmount: foreignTaxAmount,
      foreignGrandTotal: foreignTotalAmount
    });
  }

  onMatchPoOpen() {
    if (this.modeFilterMatchPo.mode === 1)
      (<any>this.$refs.matchPo)?.preloadData();
  }

  removeRow(row: any, index: number) {
    this.gridData.splice(index, 1);

    if (this.header.id) {
      this.removedLines.push(row);
    }

    this.header = Object.assign({}, this.header, {
      totalLines: this.header.totalLines - row.totalLines,
      taxAmount: this.header.taxAmount - row.taxAmount,
      grandTotal: this.header.grandTotal - row.totalAmount
    });
  }

  checkTaxInvoice(value: string) {
    const data = parseInt(value);
    let length = this.enofaList.length;


    for (let i = 0; i < length; i++) {
      const startNo = parseInt(this.enofaList[i].startNo);
      const endNo = parseInt(this.enofaList[i].endNo);

      if (data >= startNo && data <= endNo) {
        return true;
      }
    }

    return false;
  }

  validateTaxInvoiceSlot(taxInvoice: string) {
    const queryId = this.header.id ? [`id.notEquals=${this.header.id}`] : [];

    return new Promise((resolve, reject) => {
      this.dynamicWindowService(this.baseApiUrlEVerification)
        .retrieve({
          criteriaQuery: [
            `vendorId.equals=${this.header.vendorId}`,
            `taxInvoice.equals=${taxInvoice}`,
            'verificationStatus.in=DRF',
            'verificationStatus.in=SMT',
            'verificationStatus.in=APV',
            ...queryId
          ],
          paginationQuery: {
            page: 0,
            size: 1,
            sort: ['id']
          }
        })
        .then(res => {
          const list = res.data;

          if (list.length) {
            reject(`Tax Invoice is already used in verification no.: ${list[0].verificationNo}`);
          } else if (this.checkTaxInvoice(taxInvoice)) {
            resolve(true);
          } else {
            reject('There is no entry available for the specified Tax Invoice');
          }
        });
    });
  }

  async checkVerification(taxInvoice: string) {
    try {
      await this.validateTaxInvoiceSlot(taxInvoice.replace(/[-.]+/g, ''));
      this.statTaxInvoice = true;
    } catch (error) {
      this.statTaxInvoice = false;
      this.$message({
        message: error,
        type: 'error'
      });
    }
  }

  submit() {
    if(this.header.taxable){
      this.header.taxInvoice = this.header.taxInvoice.replace(/[-.]+/g, '');
    }
    this.header.picId = accountStore.userDetails.id;
    let lineCount = 0;

    for (const line of this.gridData) {
      line.lineNo = ++lineCount;
    }

    const data = {
      form: this.header,
      line: this.gridData,
      remove: this.removedLines
    }

    if (this.statTaxInvoice) {
      if (this.header.id != null) {
        this.dynamicWindowService(this.baseApiUrlEVerification + "/submit")
          .update(data)
          .then(() => {

            this.$notify({
              title: 'Success',
              dangerouslyUseHTMLString: true,
              message: 'E-Verification form updated.',
              type: 'success'
            });

            this.closeEVerificationUpdate();

          }).catch(error => {
            this.$notify({
              title: 'Error',
              message: error,
              type: 'error',
            });
          }).finally(() => {
            this.fullscreenLoading = false;
          });

      } else {
        this.dynamicWindowService(this.baseApiUrlEVerification + "/submit")
          .create(data)
          .then(() => {

            this.$notify({
              title: 'Success',
              message: 'E-Verification form submitted.',
              type: 'success'
            });

            this.closeEVerificationUpdate();

          }).catch(error => {
            this.$notify({
              title: 'Error',
              message: error,
              type: 'error',
            });
          }).finally(() => {
            this.fullscreenLoading = false;
          });

      }
    } else {
      this.fullscreenLoading = false;
    }
  }

  private transformMatchPo(lines: any[]) {
    return lines.map(line => {
      const {
        cConversionRate,
        dateAccount,
        mMatchType,
        openAmount,
        openForeignAmount,
        openQty,
        poDate,
        ...data
      } = line;

      data.conversionRate = line.cConversionRate;
      data.vendorId = line.cVendorId;
      return data;
    });
  }

  formatDocumentStatus(value: string) {
    return this.docStatus.find(status => status.key === value)?.value;
  }

  dateStatus(value: string){
    let date = "";
    if(value == "SMT"){
      date = this.header.dateSubmit;
    } else if(value == "RJC") {
      date = this.header.dateReject;
    } else if(value == "APV") {
      date = this.header.dateApprove;
    } else if(value == "CNL") {
      date = this.header.lastModifiedDate;
    }
    return date;
  }

}
