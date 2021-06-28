import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { ElForm } from 'element-ui/types/form';
import { ElUpload } from 'element-ui/types/upload';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const baseApiConfirmationLine = 'api/m-vendor-confirmation-lines';
const baseApiConfirmationContract = 'api/m-vendor-confirmation-contracts';
const baseApiContract = 'api/m-contracts';
const baseApiContractDocument = 'api/m-contract-documents/';

const VendorConfirmationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component({
  components: {
    AdInputList,
    AdInputLookup
  }
})
export default class VendorConfirmationDetail extends mixins(AccessLevelMixin, VendorConfirmationDetailProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};


  private fileList: any[] = [];

  contractParameterFormVisible: boolean = false;
  generatingContract: boolean = false;

  contractFormValidationSchema = {
    contractDetail: {
      required: true,
      message: 'Confirmation Detail is required'
    }
  };

  columnSpacing = 24;
  showDetail = false;
  showConfirmationForm = false;
  confirmPublish = false;
  showPoForm = false;
  showHistory = false;
  loading = false;
  contractLoading = true;

  poNumber: string = "";

  mainForm: any = {};

  contract: any = {};

  /**
   * This is used to generate MContract.
   */
  contractParameter: any = {};

  confirmations = []

  history = [];

  selectedConfirmation: any = {};
  vendorConfirmation: any[] = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get formSettings() {
    return settings.form;
  }



  created() {
    console.log('component detail created');
    this.mainForm = {...this.data};

    this.commonService(null)
      .retrieveReferenceLists('vendorConfirmation')
      .then(res => {
        this.vendorConfirmation = res.map(item => ({ key: item.value, value: item.name }));
      });

    this.refreshLine();
  }

  generateContract() {
    this.generatingContract = true;
    this.commonService(`${baseApiContract}/generate-from-vc`)
      .create(this.contractParameter)
      .then(res => this.$message.success(`Contract ${res.documentNo} has been generated successfully`))
      .catch(err => {
        console.error('Failed to generate contract', err);
        this.$message.error('Failed to generate contract');
        this.contractParameterFormVisible = false;
      })
      .finally(() => this.generatingContract = false);
  }

  refreshLine() {
    this.commonService(baseApiConfirmationLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorConfirmationId.equals=${this.mainForm.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.confirmations = res.data;
      });
  }

  formatConfirmationStatus(value: string) {
    return this.vendorConfirmation.find(status => status.key === value)?.value;
  }

  retrieveLastConfirmationContract(lineId: number): Promise<any> {
    return new Promise(resolve => {
      this.commonService(baseApiConfirmationContract)
        .retrieve({
          criteriaQuery: [
            'active.equals=true',
            `vendorConfirmationLineId.equals=${lineId}`
          ],
          paginationQuery: {
            page: 0,
            size: 1,
            sort: ['id,desc']
          }
        })
        .then(res => {
          if (res.data.length) {
            resolve(res.data[0]);
          } else {
            resolve({});
          }
        })
        .catch(err => {
          console.warn('Failed to get line contract', err);
          resolve({});
        });
    });
  }

  viewDetail(row: any) {
    this.loading = true;
    this.selectedConfirmation = row;
    this.commonService('/api/m-bid-nego-prices').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${this.mainForm.biddingId}`,
        `negotiationLineId.equals=${row.negoLineId}`
        ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{
      this.commonService('/api/m-bid-nego-price-lines').retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `bidNegoPriceId.equals=${res.data[0].id}`
          ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      }).then((res)=>{
        let quantity = 0;
        let amount = 0;
        console.log(res.data);
        res.data.forEach(element => {
          quantity += element.quantity;
          amount += element.totalNegotiationPrice;
        });
        this.selectedConfirmation.lines = res.data;
        this.selectedConfirmation.quantity = quantity;
        this.selectedConfirmation.amount = amount;
        this.showDetail = true;
      }).catch((err)=>{
        console.log(err);
        this.$message.error("Unable to load detail.")
      }).finally(()=>{
        this.loading = false;
      });
    }).catch((err)=>{
      console.log(err);
      this.$message.error("Unable to load detail.")
      this.loading = false;
    });
  }

  viewHistory(row: any) {
    this.selectedConfirmation = row;
    this.history = [];
    this.commonService('/api/m-vendor-confirmation-responses').retrieve({
      criteriaQuery: this.updateCriteria([
      'active.equals=true',
      `vendorConfirmationLineId.equals=${row.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{this.history = res.data}).catch((err)=>{
      console.log(err);
      this.$message.error("Unable to load history.")
    })

    this.showHistory = true;
  }

  openConfirmationForm(_row: any) {
    this.selectedConfirmation = _row;
    this.contractLoading = true;
    this.commonService(baseApiConfirmationContract)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorConfirmationLineId.equals=${_row.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.contract =
          res.data[0] ? res.data[0] : {
            confirmationNo: Date.now(),
            contractStartDate: null,
            contractEndDate: null,
            contractDetail: null,
            adOrganizationId: this.mainForm.adOrganizationId,
            vendorConfirmationLineId: _row.id
          }

        if (res.data[0]) {
          this.fileList.push({ "name": this.contract.attachment.fileName, "url": this.contract.downloadUrl });
          this.contractLoading = false;
        } else {
          this.commonService(baseApiContract).retrieve({
            criteriaQuery: this.updateCriteria([
              `vendorId.equals=${_row.vendorId}`,
              `biddingId.equals=${this.mainForm.biddingId}`
            ]),
            paginationQuery: {
              page: 0,
              size: 10,
              sort: ['id']
            }
          }).then((res)=>{
            if( ((<any[]>res.data).length) ){
              this.contract.contractStartDate = res.data[0].startDate;
              this.contract.contractEndDate = res.data[0].expirationDate;
              this.commonService(baseApiContractDocument).retrieve({
                criteriaQuery: this.updateCriteria([
                  `contractId.equals=${res.data[0].id}`
                ]),
                paginationQuery: {
                  page: 0,
                  size: 10,
                  sort: ['id']
                }
              }).then((res)=>{
                if( ((<any[]>res.data).length) ){
                  this.contract.attachmentId = res.data[0].attachmentId;
                  this.fileList.push({ "name": res.data[0].attachmentName, "url": res.data[0].attachmentUrl });
                }
                this.contractLoading = false;
              })
            } else this.contractLoading = false;
          })
        }
        console.log(this.contract);
      });
    this.showConfirmationForm = true;
  }

  generatePo(row: any) {
    this.selectedConfirmation = row;
    this.commonService('/api/m-bid-nego-prices').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${this.mainForm.biddingId}`,
        `negotiationLineId.equals=${row.negoLineId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res => {
      this.commonService('/api/m-bid-nego-price-lines').retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `bidNegoPriceId.equals=${res.data[0].id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      }).then((res) => {
        let amount = 0;
        console.log(res.data);
        res.data.forEach(element => {
          amount += element.totalNegotiationPrice;
        });

        let lines = res.data;

        let poBody: any = {
          active: true,
          adOrganizationId: row.adOrganizationId,
          costCenterId: this.mainForm.costCenterId,
          currencyId: this.mainForm.currencyId,
          // datePromised: "2021-06-01",
          // dateRequired: "2021-06-01",
          // dateTrx: `${today.getFullYear()}-${((today.getMonth()+1)<10?'0':'')+(today.getMonth()+1)}-${(today.getDate()<10?'0':'')+(today.getDate())}`,
          description: "PO for bidding " + this.mainForm.biddingTitle,
          // documentTypeId: 874953,
          grandTotal: amount,
          // paymentTermId: 1951601,
          vendorId: row.vendorId,
          warehouseId: this.mainForm.warehouseId,
          biddingId: this.mainForm.biddingId,

          poLines: lines.map(line => ({
            active: true,
            dateTrx: poBody.dateTrx,
            orderAmount: line.totalNegotiationPrice,
            quantity: line.quantity,
            unitPrice: line.priceNegotiation,
            adOrganizationId: poBody.adOrganizationId,
            productId: line.productId,
            warehouseId: poBody.warehouseId,
            costCenterId: poBody.costCenterId,
            uomId: line.uomId,
            vendorId: this.selectedConfirmation.vendorId,
            dateRequired: poBody.dateRequired,
            datePromised: poBody.datePromised
          }))
        };

        this.commonService('/api/m-purchase-orders/generate-from-vc').create(poBody).then(res => {
          this.poNumber = res.documentNo;
          this.showPoForm = true;
        }).catch(error => {
          this.$message.error("Unable to generate PO.");
        });
      });
    });
  }

  onUploadChange(file: any) {
    this.file = file;
    this.fileList = [file];
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  handleRemove(files, fileList) {
    this.file = {};
    this.fileList = [];
    this.contract.attachment = null;
    this.contract.attachmentId = null;
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
    this.$notify({
      title: 'Error',
      message: "Failed uploading a file",
      type: 'error',
      duration: 3000
    });
  }

  onUploadSuccess(response: any, file) {
      console.log('File uploaded successfully ', response);
      this.contract.attachment = response.attachment;
      this.contract.attachmentId = response.attachment.id;
      this.file = file;
      this.fileList = [file];
      (this.$refs.contractForm as ElForm).clearValidate('attachment');
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 file(s) are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      this.$notify({
          title: 'Warning',
          message: "File size must be less than 2Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt2M;
    }

    // File type restriction
    const name = file.name ? file.name : '';
    const ext = name
      ? name.substr(name.lastIndexOf('.') + 1, name.length)
      : true;
    const isExt = this.accept.indexOf(ext) < 0;
    if (isExt) {
      this.$notify({
        title: 'Warning',
        message: "Please upload the correct format type",
        type: 'warning',
        duration: 3000
      });
      return false;
    }

  }

  async openContractParameter(row: any) {
    const lineContract = await this.retrieveLastConfirmationContract(row.id);
    const fileName = lineContract.attachmentName;
    console.log("this file name",fileName);
    console.log("main",this.mainForm);
    // const strippedName = fileName.substring(fileName.indexOf('_') + 1, fileName.lastIndexOf('.'));
    // const contractName: string = `C${row.vendorConfirmationBiddingNo}-${strippedName}`;

    this.contractParameter = {
      adOrganizationId: row.adOrganizationId,
      biddingId: row.vendorConfirmationBiddingId,
      // name: contractName.length > 50 ? contractName.substring(0, 49) : contractName,
      name:this.mainForm.biddingTitle,
      costCenterId: row.vendorConfirmationCostCenterId,
      picUserId: row.vendorConfirmationPicId,
      vendorId: row.vendorId,
      startDate: lineContract.contractStartDate,
      expirationDate: lineContract.contractEndDate,
      vendorEvaluationId: null,
      evaluationPeriod: null,
      negoLineId:row.negoLineId
    };

    console.log('contract param:', this.contractParameter);
    this.contractParameterFormVisible = true;
  }

  saveAsDraft() {
    (<ElForm>this.$refs.contractForm).validate(passed => {
      if (passed) {
        if (!this.contract.attachmentId) {
          this.$notify({
            title: 'Warning',
            message: "Please upload contract file.",
            type: 'warning',
            duration: 3000
          });
        } else {
          if (this.contract.id) {
            this.commonService('/api/m-vendor-confirmation-contracts')
              .update(this.contract)
              .then(_res => {
                this.$message.success('Contract has been saved!');

                this.resetForm();
                this.refreshLine();
              }).catch(err => {
                console.error('Failed to save the contract.', err);
                this.$message.error(`Failed saving the contract`);
              });
          } else {
            this.commonService('/api/m-vendor-confirmation-contracts')
              .create(this.contract)
              .then(_res => {
                this.$message.success('Contract has been saved!');

                this.resetForm();
                this.refreshLine();
              }).catch(err => {
                console.error('Failed to save the contract.', err);
                this.$message.error(`Failed saving the contract`);
              });
          }
        }
      }
    });
  }

  publish() {
    if (this.contract.id) {
      this.commonService(`/api/m-vendor-confirmation-contracts/publish/${this.contract.id}`)
        .create(this.contract)
        .then(_res => {
          this.$message.success('Contract has been published!');

          this.resetForm();

          this.confirmPublish = false;
          this.refreshLine();
        }).catch(err => {
          console.error('Failed to publish the contract.', err);
          this.$message.error(`Failed saving the contract`);
        });
    } else {
      this.commonService('/api/m-vendor-confirmation-contracts')
        .create(this.contract)
        .then(res => {
          console.log(res);
          this.contract = res;
          this.publish();
        }).catch(err => {
          console.error('Failed to save the contract.', err);
          this.$message.error(`Failed saving the contract`);
        });
    }
  }

  showConfirmPublish() {
    (<ElForm>this.$refs.contractForm).validate(passed => {
      if (passed) {
        if (!this.contract.attachmentId) {
          this.$notify({
            title: 'Warning',
            message: "Please upload contract file.",
            type: 'warning',
            duration: 3000
          });
        } else {
          this.confirmPublish = true;
        }
      }
    });
  }

  resetForm() {
    this.contract = {};
    (<ElUpload>this.$refs.contractFile).clearFiles();
    this.fileList = [];
    this.showConfirmationForm = false;
  }
}
