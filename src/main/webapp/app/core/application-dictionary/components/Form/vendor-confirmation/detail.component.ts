import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { ElUpload } from 'element-ui/types/upload';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

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

@Component
export default class VendorConfirmationDetail extends mixins(AccessLevelMixin, VendorConfirmationDetailProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  contractFormValidationSchema = {
    confirmationNo: {
      required: true,
      message: 'Confirmation No. is required'
    }
  };
  
  columnSpacing = 24;
  showDetail = false;
  showConfirmationForm = false;
  confirmPublish = false;
  showPoForm = false;
  showHistory = false;

  mainForm:any = {};

  contract:any = {};
/*
    confirmationNo: "",
    contractStartDate: '2021-05-28',
    contractEndDate: '2021-05-28',
    contractDetail: null
  };
*/

  confirmations = []
/*
    {
      vendorName: 'Supplier 3',
      amount: 29310000000,
      quantity: 180,
      documentStatus: 'A',
      lines: [
        {
          item: 'Honda 2015',
          quantity: 50,
          unitPrice: 238000000,
          totalLine: 11900000000
        },
        {
          item: 'Honda Civic 2017',
          quantity: 30,
          unitPrice: 439000000,
          totalLine: 13710000000
        },
        {
          item: 'Honda 2020',
          quantity: 100,
          unitPrice: 45000000,
          totalLine: 4500000000
        }
      ]
    }
  ];
*/

  history = [
    {
      contractNo: '112001',
      lastModifiedDate: '2021-03-31',
      status: 'Accepted',
      reason: 'Sudah sesuai'
    },
    {
      contractNo: '112001',
      lastModifiedDate: '2021-03-28',
      status: 'Need Revision',
      reason: 'Mohon konfirmasi'
    }
  ];

  selectedConfirmation: any = {};
  vendorConfirmation: any[] = [];

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

  refreshLine(){
    this.commonService('/api/m-vendor-confirmation-lines').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `vendorConfirmationId.equals=${this.mainForm.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{this.confirmations = res.data});
  }

  formatConfirmationStatus(value: string) {
    return this.vendorConfirmation.find(status => status.key === value)?.value;
  }

  beforeDestroy() {
    console.log('before destroy component detail');
  }

  viewDetail(row: any) {
    this.selectedConfirmation = row;
    this.commonService('/api/m-bidding-lines').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${this.mainForm.biddingId}`
        ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{
      let quantity = 0;
      console.log(res.data);
      res.data.forEach(element => {
        quantity += element.quantity;
      });
      this.selectedConfirmation.lines = res.data;
      this.selectedConfirmation.quantity = quantity;
      this.showDetail = true;
    });
  }

  viewHistory(row: any) {
    this.selectedConfirmation = row;
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
    }).then(res=>{this.history = res.data});
    this.showHistory = true;
  }

  openConfirmationForm(_row: any) {
    this.selectedConfirmation = _row;
    this.commonService('/api/m-vendor-confirmation-contracts').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `vendorConfirmationLineId.equals=${_row.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{this.contract = 
      res.data[0]?res.data[0]:{
        confirmationNo: Date.now(),
        contractStartDate: '2021-05-28',
        contractEndDate: '2021-05-28',
        contractDetail: null,
        adOrganizationId: this.mainForm.adOrganizationId,
        vendorConfirmationLineId: _row.id
      }
      
      console.log(this.contract);
    });
    this.showConfirmationForm = true;
  }

  generatePo(_row: any) {
    this.showPoForm = true;
  }

  onUploadChange(file: any) {
    this.file = file;
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  handleRemove(files, fileList) {
    this.file = {};
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
      this.contract.attachmentId = response.attachment.id;
      this.file = file;
      //(this.$refs.company as ElForm).clearValidate('file');
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
      return !isExt;
    }

  }
  saveAsDraft(){
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

  publish(){
    if(this.contract.id){
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

  showConfirmPublish(){
    (<ElForm>this.$refs.contractForm).validate(passed => {
      if (passed) {
        this.confirmPublish = true;
      }
    });
  }

  resetForm() {
    this.contract = {};
    (<ElUpload>this.$refs.contractFile).clearFiles();
    this.showConfirmationForm = false;
  }
}
