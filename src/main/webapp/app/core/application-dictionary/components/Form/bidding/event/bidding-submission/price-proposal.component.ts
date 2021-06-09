import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import SubitemEditor from "@/core/application-dictionary/components/Form/bidding/components/subitem-editor.vue";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { formatDuration, intervalToDuration } from 'date-fns';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import Schema from 'async-validator';
import { ElForm } from 'element-ui/types/form';
import AccountService from "@/account/account.service";

const baseApiLines = 'api/m-bidding-lines';
const baseApiSubmission = 'api/m-bidding-submissions';
const baseApiProposal = 'api/m-proposal-prices';
const baseApiProposalLine = 'api/m-proposal-price-lines';

const PriceProposalProp = Vue.extend({
  props: {
    /**
     * Whether Administration, Technical, or Price proposal data.
     */
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },

    disabled: Boolean,
    loading: Boolean,

    schedule: {
      type: Object,
      default: () => {
        return {};
      }
    },

    submissionId: Number
  }
})

@Component({
  components: {
    SubitemEditor
  }
})
export default class PriceProposal extends Mixins(AccessLevelMixin, PriceProposalProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  private timerId;
  private intervalId;
  private currentDate = new Date();

  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private action: string = "/api/c-attachments/upload"
  private limit: number = 1;
  private formData:any={};
  private file: any = {};
  attachmentFormVisible = false;

  private lineCache: Map<number, any> = new Map();

  loading: boolean = false;
  loadingLines: boolean = false;
  savingSubitem: boolean = false;

  mainForm: Record<string, any> = {
    proposalPriceLines: []
  };

  validationRulesHeader: any = {
    proposedPrice: {
      required: true,
      message: 'Proposed Price is required'
    }
  };

  validationRulesLine: any = {
    proposedPrice: {
      required: true,
      message: 'Item\'s Proposed Price is required'
    },
    deliveryDate: {
      required: true,
      message: 'Item\'s Delivery Date is required'
    }
  }

  lineErrors: Record<string, string> = {};

  biddingStatuses: any[] = [];
  vendorSelectionOptions: any[] = [];

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records',
    maxHeight: 150,
    height: 150
  };

  selectedItemIndex = 0;
  selectedItem = {};

  columnSpacing = 32;
  subItemEditorVisible = false;

  // upload
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

  handlePreview() {
    window.open(this.mainForm.attachmentUrl, '_blank');
  }

  get hasAttachment() {
    return !!this.mainForm.attachmentName;
  }

  cancelAttachment() {
    this.mainForm.attachmentName=null;
    this.formData = null;
    this.formData = [];

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

  onUploadSuccess(response, file, fileList) {
    console.log("this response",response)
    this.formData.attachment = response.attachment;
    this.formData.attachmentId = response.attachment.id;
    this.formData.attachmentName = response.attachment.fileName;
    this.formData.attachmentUrl = response.attachment.downloadUrl;
    this.file = file;
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }
  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
  }

  printFileName(attachment: any) {
    return attachment?.fileName;
  }

  saveAttachment() {
    this.mainForm.attachmentUrl=this.formData.downloadUri;
    this.mainForm.attachmentId=this.formData.attachment.id;
    this.mainForm.attachmentName= this.formData.attachmentName;
    this.attachmentFormVisible = false;
  }

  //upload

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get timeRemaining() {
    if (!this.schedule.actualEndDate) {
      return '';
    }

    if (this.currentDate >= new Date(this.schedule.actualEndDate)) {
      return 'Event has been ended';
    }

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(this.schedule.actualEndDate)
    });

    return formatDuration(duration, {
      format: ['days', 'hours', 'minutes']
    });
  }

  onProposedPriceChange(row: any, index: number, value: number = 0) {
    row.totalPriceSubmission = value * row.quantity;
    this.mainForm.proposalPriceLines.splice(index, 1, row);
    this.calculateTotalAmount();
  }

  onSubItemError() {
    this.savingSubitem = false;
  }

  onSubItemClosed() {
    (<any>this.$refs.subitemEditor).reset();
  }

  onSubItemSaved({itemIndex, subItem}) {
    const line = {...this.mainForm.proposalPriceLines[itemIndex]};
    line.proposedPrice = subItem.totalAmount;
    line.subItem = subItem;

    this.$set(this.mainForm.proposalPriceLines, itemIndex, line);
    this.onProposedPriceChange(line, itemIndex, line.proposedPrice);
    this.savingSubitem = false;
    this.subItemEditorVisible = false;
  }

  created() {
    this.timerId = setTimeout(() => {
      this.intervalId = setInterval(this.updateCurrentDate, 60000);
      this.updateCurrentDate();

      if (this.currentDate >= new Date(this.schedule.actualEndDate)) {
        clearInterval(this.intervalId);
        clearInterval(this.timerId);
      }
    }, (60 - this.currentDate.getSeconds()) * 1000);

    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => this.biddingStatuses = res);

    this.commonService(null)
      .retrieveReferenceLists('mVendorSelection')
      .then(res => {
        this.vendorSelectionOptions = res;
      });

    Promise.allSettled([
      this.retrieveSubmission(this.submissionId),
      this.retrieveBiddingLines(this.schedule.biddingId)
    ])
    .then(() => this.retrieveProposal(this.submissionId));
  }

  closeSubitemEditor() {
    this.subItemEditorVisible = false;
  }

  editSubItem(row: any, index: number) {
    this.selectedItem = row;
    this.selectedItemIndex = index;
    this.subItemEditorVisible = true;
  }

  getError(field: string, index: number) {
    const prop = `${field}_${index}`;
    return this.lineErrors[prop];
  }

  hasError(field: string, index: number) {
    const prop = `${field}_${index}`;
    return this.lineErrors.hasOwnProperty(prop);
  }

  saveSubitemEditor() {
    this.savingSubitem = true;
    (<any>this.$refs.subitemEditor).save();
  }

  private calculateTotalAmount() {
    const grandTotal = (this.mainForm.proposalPriceLines as any[])
      .map((line: any): number => line.totalPriceSubmission || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.mainForm, 'proposedPrice', grandTotal);
  }

  private updateCurrentDate() {
    this.currentDate = new Date();
  }

  private retrieveSubmission(submissionId: number) {
    return new Promise((resolve, reject) => {
      this.loading = true;
      this.commonService(baseApiSubmission)
        .find(submissionId)
        .then(res => {
          this.mainForm = {...this.mainForm, ...res};
          resolve(true);
        })
        .catch(err => {
          console.log('Failed to get submission', err);
          this.$message.error('Failed to get submission details');
          reject(false);
        })
        .finally(() => this.loading = false);
    });
  }

  private retrieveBiddingLines(biddingId: number) {
    return new Promise((resolve, reject) => {
      this.loadingLines = true;
      this.commonService(baseApiLines)
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
          this.$set(this.mainForm, 'proposalPriceLines', res.data.map((line: any) => {
            const item = (({
              // Destructure the MBiddingLineDTO object.
              id,
              adOrganizationId,
              deliveryDate,
              ceilingPrice,
              productName,
              document,
              quantity,
              totalCeilingPrice,
              uomName,
              remark,
              subItem
            }) => ({
              // Reconstruct to MProposalPriceLineDTO structure.
              adOrganizationId,
              biddingLineId: id,
              reqDeliveryDate: deliveryDate,
              ceilingPrice,
              totalCeilingPrice,
              productName,
              document,
              quantity,
              uomName,
              remark,
              subItem,
              proposedPrice: null,
              deliveryDate: null
            }))(line);

            this.lineCache.set(item.biddingLineId, item);
            return item;
          }));

          resolve(true);
        })
        .catch(err => {
          console.log('Failed to get bidding lines. %O', err);
          this.$message.error('Failed to get bidding lines');
          reject(false);
        })
        .finally(() => this.loadingLines = false);
    });
  }

  private retrieveProposal(submissionId: number) {
    this.commonService(baseApiProposal)
      .retrieve({
        criteriaQuery: [
          `biddingSubmissionId.equals=${submissionId}`
        ]
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm = { ...this.mainForm, ...res.data[0] };
          this.retrieveProposedLines(this.mainForm.id);
        }
      });
  }

  private retrieveProposedLines(proposalId: number) {
    this.commonService(baseApiProposalLine)
      .retrieve({
        criteriaQuery: [
          `proposalPriceId.equals=${proposalId}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        console.log("this res",res.data);

        (res.data as any[]).forEach(line => {
          console.log("this line",line);

          const item = this.lineCache.get(line.biddingLineId);
          item.document=line.document;
          item.id=line.id;
          item.deliveryDate = line.deliveryDate;
          item.proposedPrice = line.proposedPrice;
          item.totalPriceSubmission = item.quantity * line.proposedPrice;
          console.log("this item",item);
        })
      })
  }
  // attachmentId=this.formData.attachmentId,
  save() {
    this.$emit('update:loading', true);
    let valid = true;
    const data = (({
      id,
      adOrganizationId,
      ceilingPrice,
      proposedPrice,

      proposalPriceLines
    }) => ({
      id,
      adOrganizationId,
      biddingSubmissionId: this.submissionId,
      attachmentId:this.mainForm.attachmentId,
      ceilingPrice,
      proposedPrice,
      proposalPriceLines
    }))(this.mainForm);

    // Validate the header.
    (<ElForm>this.$refs.mainForm).validate(passed => {
      if (passed) {
        // Validate each line.
        valid = this.validateLines(this.mainForm.proposalPriceLines);
      }

      if (valid) {

        this.commonService(`${baseApiProposal}/form`)
          .create(data)
          .then(_res => {
            this.$message.success('Price proposal has been saved successfully');
          })
          .catch(err => {
            console.error('Failed to save the proposal. %O', err);
            this.$message.error(`Failed saving the price proposal`);
          })
          .finally(() => this.$emit('update:loading', false));
      }
    });
  }

  private validateLines(lines: any[]) {
    const lineValidator = new Schema(this.validationRulesLine);
    let valid = true;
    let idx = 0;

    for (const line of lines) {
      lineValidator.validate(line, (errors: any) => {
        if (errors) {
          valid = false;
          for (const error of errors) {
            const item = {...this.mainForm.proposalPriceLines[idx]};
            item[`${error.field}Error`] = true;
            item[`${error.field}Message`] = error.message;
            this.mainForm.proposalPriceLines.splice(idx, 1, item);
          }
          this.$message.error(errors[0].message);
        } else {
          const item = {...this.mainForm.proposalPriceLines[idx]};
          item.proposedPriceError = false;
          item.proposedPriceMessage = null;
          item.deliveryDateError = false;
          item.deliveryDateMessage = null;
          this.mainForm.proposalPriceLines.splice(idx, 1, item);
        }
      });

      ++idx;

      if (!valid) {
        break;
      }
    }

    return valid;
  }
}
