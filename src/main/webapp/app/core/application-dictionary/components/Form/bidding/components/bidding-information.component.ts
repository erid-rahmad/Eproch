import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import SubitemEditor from './subitem-editor.vue';

const BiddingInformationProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
});

@Component({
  components: {
    SubitemEditor
  }
})
export default class BiddingInformation extends Mixins(AccessLevelMixin, BiddingInformationProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private updated = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 150,
    height: 150
  };
  rules = {}

  fullscreenLoading = false;
  loading = false;
  loadingReferenceNo = false;
  loadingLines = false;
  loadingProjectInfo = false;
  savingSubitem = false;

  public costCenterOptions: any[] = [];
  public picOptions: any[] = [];
  public biddingTypeOptions: any[] = [];
  public vendorSelectionOptions: any[] = [];
  public eventTypeOptions: any[] = [];
  public uomOptions: any[] = [];

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";

  private projectInformation: any = {
    adOrganizationId: null,
    name: null,
    attachment: null,
    attachmentId: null
  };

  selectedItemIndex = 0;
  selectedItem = {};

  columnSpacing = 32;
  subItemEditorVisible = false;
  projectFormVisible:boolean = false;

  dialogTitle = "";
  dialogContent = null;
  dialogContentSubItem = null;
  dialogWidth = "";
  dialogCloseOnClick = true;

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";

  bidding: Record<string, any> = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  @Watch('data')
  onDataChanged(data: any) {
    console.log('bidding info data changed:', data);
  }

  onCeilingPriceChange(row: any, value: number = 0) {
    console.log('Bidding line: %O, ceiling price: %s', row, value);
    row.totalCeilingPrice = value * row.quantity;
    this.calculateTotalAmount();
  }

  onSubItemError() {
    this.savingSubitem = false;
  }

  onSubItemClosed() {
    (<any>this.$refs.subitemEditor).reset();
  }

  onSubItemSaved({itemIndex, subItem}) {
    const line = {...this.bidding.biddingLines[itemIndex]};
    line.ceilingPrice = subItem.totalAmount;
    line.subItem = subItem;

    this.$set(this.bidding.biddingLines, itemIndex, line);
    this.onCeilingPriceChange(line, line.ceilingPrice);
    this.savingSubitem = false;
    this.subItemEditorVisible = false;
  }

  created() {
    this.bidding.step = BiddingStep.INFO;

    if (this.editMode) {
      this.bidding = {...this.data};
      this.$set(this.bidding, 'referenceNo', this.bidding.requisitionName);
      this.retrieveEventTypes(this.bidding.biddingTypeId);
      this.retrieveBiddingLines(this.bidding.id);
      this.retrieveProjectInformations(this.bidding.id);
    } else {
      this.bidding.documentAction = 'APV';
      this.bidding.documentStatus = 'SMT';
      this.bidding.projectInformations = [];
    }

    this.retrieveCostCenter();
    this.retrieveBiddingType();
    this.retrieveReferenceLists('mVendorSelection');
    this.retrievePicBidding();
    this.retrieveUom();
  }

  private calculateTotalAmount() {
    const grandTotal = (this.bidding.biddingLines as any[])
      .map((line: any): number => line.totalCeilingPrice || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.bidding, 'ceilingPrice', grandTotal);
    this.$set(this.bidding, 'estimatedPrice', grandTotal);
  }

  closeSubitemEditor() {
    this.subItemEditorVisible = false;
  }

  saveSubitemEditor() {
    this.savingSubitem = true;
    (<any>this.$refs.subitemEditor).save();
  }

  private retrieveReferenceLists(code: string) {
    if (code !== 'mVendorSelection') {
      return;
    }

    this.commonService('/api/ad-reference-lists')
    .retrieve({
      criteriaQuery: this.updateCriteria([
        `active.equals=true`,
        `adReferenceValue.equals=${code}`
      ]),
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['name']
      }
    })
    .then(res => {
      this.vendorSelectionOptions = res.data.map((item: any) =>
        ({
            key: item.value,
            value: item.name
        })
      );
    });
  }

  private retrieveCostCenter() {
    this.commonService('/api/c-cost-centers')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.costCenterOptions = res.data;
      });
  }

  private retrieveBiddingType() {
    this.commonService('/api/c-bidding-types')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.biddingTypeOptions = res.data;
      });
  }

  private retrievePicBidding() {
    this.commonService('/api/ad-users')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'employee.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['user_id']
        }
      })
      .then(res => {
        this.picOptions = res.data;
      });
  }

  retrieveEventTypes(biddingTypeId: number) {
    this.commonService('/api/c-event-types')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingTypeId.equals=${biddingTypeId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.eventTypeOptions = res.data;
      });
  }

  private retrieveUom() {
    this.commonService('/api/c-unit-of-measures')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.uomOptions = res.data;
      });
  }

  private retrieveBiddingLines(biddingId: number) {
    this.loadingLines = true;
    this.commonService('/api/m-bidding-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['lineNo', 'id']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'biddingLines', res.data);
      })
      .catch(err => {
        console.log('Failed to get bidding lines. %O', err);
        this.$message.error('Failed to get bidding lines');
      })
      .finally(() => {
        this.loadingLines = false;
      })
  }

  private retrieveProjectInformations(biddingId: number) {
    this.loadingProjectInfo = true;
    this.commonService('/api/m-project-informations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'projectInformations', res.data);
      })
      .catch(err => {
        console.log('Failed to get project informations. %O', err);
        this.$message.error('Failed to get project informations');
      })
      .finally(() => {
        this.loadingProjectInfo = false;
      })
  }

  retrieveReferencedData(referenceNo?: string) {
    if (referenceNo) {
      this.loadingReferenceNo = true;

      this.commonService('/api/m-requisitions')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            'processed.equals=true',
            'approved.equals=true',
            `documentNo.equals=${referenceNo}`
          ]),
          paginationQuery: {
            page: 0,
            size: 1,
            sort: ['id']
          }
        })
        .then(res => {
          const data = res.data as any[];

          if (data.length) {
            const document = data[0];
            this.$set(this.bidding, 'adOrganizationId', document.adOrganizationId);
            this.$set(this.bidding, 'adOrganizationName', document.adOrganizationName);
            this.$set(this.bidding, 'currencyName', document.currencyName);
            this.$set(this.bidding, 'currencyId', document.currencyId);
            this.$set(this.bidding, 'costCenterId', document.costCenterId);
            this.$set(this.bidding, 'requisitionId', document.id);
            this.$set(this.bidding, 'referenceTypeName', document.documentTypeName);
            this.$set(this.bidding, 'referenceTypeId', document.documentTypeId);

            this.retreiveReferenceLines(document.id);
          } else {
            this.$message.warning('No document found for the given reference no.');
          }
        })
        .catch(err => {
          console.error('Failed to get reference. %O', err);
          this.$message.error(err.detail || err.message);
        })
        .finally(() => {
          this.loadingReferenceNo = false;
        });
    }
  }

  private retreiveReferenceLines(referenceId: number): void {
    this.commonService('/api/m-requisition-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `requisitionId.equals=${referenceId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['lineNo', 'id']
        }
      })
      .then(res => {
        const lines = (res.data as any[]).map(prLine => {
          return {
            adOrganizationId: prLine.adOrganizationId,
            costCenterId: this.bidding.costCenterId,
            productId: prLine.productId,
            productName: prLine.productName,
            subItemId: null,
            quantity: prLine.quantity,
            uomId: prLine.uomId,
            uomName: prLine.uomName,
            ceilingPrice: prLine.unitPrice,
            totalCeilingPrice: prLine.requisitionAmount,
            deliveryDate: prLine.datePromised,
            remark: prLine.remark
          };
        });

        this.$set(this.bidding, 'biddingLines', lines);
        this.calculateTotalAmount();
      })
      .catch(err => {
        console.error('Failed to get bidding lines. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  editSubItem(row: any, index: number) {
    this.selectedItem = row;
    this.selectedItemIndex = index;
    this.subItemEditorVisible = true;
  }

  removeProject(index: number) {
    this.bidding.projectInformations.splice(index, 1);
  }

  saveProject() {
    (this.$refs.projectInformation as ElForm).validate((passed, errors) => {
      if (passed) {
        this.projectInformation.adOrganizationId = this.adOrganizationId;

        console.log('Project Info:', this.projectInformation);
        this.bidding.projectInformations.push({...this.projectInformation});
        this.projectFormVisible = false;
        this.projectInformation = {
          adOrganizationId: null,
          name: null,
          attachment: null,
          attachmentId: null
        };
      } else {
        console.log(errors);
      }
    });
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  downloadAttachment(row){
    window.open(`/api/c-attachments/download/${row.attachment.id}-${row.attachment.fileName}`, '_blank');
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response, file, fileList) {
      this.projectInformation.attachment = response.attachment;
      this.projectInformation.attachmentId = response.attachment.id;
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 files are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      this.$notify({
          title: 'Warning',
          message: "files with a size less than 5Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt5M;
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

  /**
   * Invoked before proceeding to the next step.
   */
  save() {
    (this.$refs.biddingInformation as ElForm).validate((passed, errors) => {
      if (passed) {
        let service = this.commonService('/api/m-biddings/save-form');

        service[this.editMode ? 'update' : 'create'](this.bidding)
          .then(res => {
            if (! this.editMode) {
              this.bidding.id = res.id;
              this.$set(this.bidding, 'documentNo', res.documentNo);
            }

            this.$emit('saved', {
              data: res
            });
          })
          .catch(err => {
            console.log('Failed to save bidding info. %O', err);
            this.$message.error('Failed to save ')
            this.$emit('error', errors)
          });
          
        
      } else {
        this.$emit('error', errors);
      }
    });
  }
}
