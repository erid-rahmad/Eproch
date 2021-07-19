import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";
import { AccountStoreModule } from '@/shared/config/store/account-store';
import Schema from "async-validator";
import AccountService from "@/account/account.service";
import {create} from "domain";


const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';
const baseApiProposalAdministration = 'api/m-proposal-administrations';
const baseApiProposalTechnical = 'api/m-proposal-technicals';
const baseApiProposalTechnicalFile = 'api/m-proposal-administration-files';

export const proposalNameMap: Map<string, string> = new Map([
  ['A', 'Administration'],
  ['P', 'Price'],
  ['T', 'Technical'],
]);

const ProposalFormProps = Vue.extend({
  props: {
    data: Object,
    loading: Boolean,
    submissionId: Number
  }
});

@Component
export default class ProposalForm extends Mixins(AccessLevelMixin, ProposalFormProps)  {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  evaluationMethodCriteria: any[] = [];
  requirements: Map<number, string> = new Map();
  attachmentHandler: Map<number, string> = new Map();
  private formData:any={};
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private action: string = "/api/c-attachments/upload";
  private file: any = {};
  private subCriteria:any={};
  loading:boolean=false
  disabled:boolean=false;
  readOnlyCheklist:boolean=false;

  answers: Map<number, any> = new Map();
  attachmentFormVisible = false;

  validationSchema: any = {};
  validationSchemaAttachment: any = {};

  get isVendor() {
    this.disabled=false;
    return AccountStoreModule.isVendor;
  }

  get isAdministration() {
    return this.data.evaluationMethodLineName === 'A';
  }

  get isTechnical() {
    return this.data.evaluationMethodLineName === 'T';
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.disabled=false;
    this.readOnlyCheklist=false;
    this.$emit('setReadOnly',false);
    this.answers.clear();
    this.requirements.clear();
    this.retrieveVendorScoringCriteria(data.id, data.evaluationMethodLineId);

  }

  created() {
       if (this.isVendor) {
      this.validationSchema = {
        answer: {
          type: 'string',
          required: true,
          message: 'Answer is required'
        },
      };
      this.validationSchemaAttachment = {
        attachmentName: {
          type: 'string',
          required: true,
          message: 'attachment is required'

        },
      };
    }

    this.retrieveVendorScoringCriteria(this.data.id, this.data.evaluationMethodLineId);

  }

  /**
   * First, we need to get the predefined requirements from m_vendor_scoring_criteria.
   * Then, populate the form with the questions from c_evaluation_method_criteria.
   * Finally, map the existing answers from m_proposal_xxx to each question, if any.
   * @param vendorScoringLineId
   * @param evaluationMethodLineId
   */
  private retrieveVendorScoringCriteria(vendorScoringLineId: number, evaluationMethodLineId: number) {
    this.loading=true;
    this.commonService(baseApiVendorScoringCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorScoringLineId.equals=${vendorScoringLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          this.requirements.set(criteria.biddingSubCriteriaLineId, criteria.requirement);
        });

        this.retrieveEvaluationMethodCriteria(evaluationMethodLineId);
      })
  }

  private retrieveEvaluationMethodCriteria(evaluationMethodLineId: number) {
    this.commonService(baseApiEvalMethodCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `evaluationMethodLineId.equals=${evaluationMethodLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodCriteria = res.data.map((evalMethodCriteria: any) => {
          evalMethodCriteria.evalMethodSubCriteriaList.forEach((evalMethodSubCriteria: any) => {
            evalMethodSubCriteria.biddingSubCriteriaDTO.forEach((biddingSubCriteria: any) => {
              biddingSubCriteria.attachmentId=null;
              biddingSubCriteria.attachmentName=null;
              biddingSubCriteria.attachmentUrl=null;
              this.attachmentHandler.set(biddingSubCriteria.id,biddingSubCriteria);

              biddingSubCriteria.criteriaLineDTO.forEach((subCriteriaLine: any) => {
                subCriteriaLine.requirement = this.requirements.get(subCriteriaLine.id)
                subCriteriaLine.answer = null;
                subCriteriaLine.documentEvaluation = false;

                // List of answers will be submitted later.
                this.answers.set(subCriteriaLine.id, subCriteriaLine);
              });
            });
          });

          return evalMethodCriteria;

        });



        this.retrieveProposalData(this.submissionId);
      })
  }

  private retrieveProposalData(submissionId: number) {
    const baseApiUrl = this.isAdministration ? baseApiProposalAdministration : baseApiProposalTechnical;
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `biddingSubmissionId.equals=${submissionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        for (const proposal of res.data) {
          if(proposal.documentStatus==='SMT'){
            this.disabled=true;
            this.isVendor ?this.$emit('setReadOnly',true):null;
          }
          if(proposal.documentAction==='SMT'){
            this.readOnlyCheklist=true;
            !this.isVendor ? this.$emit('setReadOnly',true):null;
          }
          try {
            const item = this.answers.get(proposal.biddingSubCriteriaLineId);
            item.answer = proposal.answer;
            item.answerId=proposal.id
            item.documentEvaluation = proposal.documentEvaluation;
          }catch (e) {}
        };
        this.retrieveAttachment(this.submissionId);
      })
  }

  //

  private fileList :any =[];

  get attachmentName() {
    return this.formData.attachmentName;
  }

  get hasAttachment() {
    return this.formData.attachmentId;
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

  handlePreview() {
    window.open(this.formData.attachmentUrl, '_blank');
  }

  handleDownload(url) {
    window.open(url, '_blank');
  }
  cancelAttachment(biddingSubCriteria) {
    const att = this.attachmentHandler.get(biddingSubCriteria.id);
    // @ts-ignore
    att.attachmentId = null;
    // @ts-ignore
    att.attachmentName = null;
    // @ts-ignore
    att.attachmentUrl = null;
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

  changeCode(code :String){
    if(code==="T"){
      return "Technic"
    }
    if(code==="A"){
      return "Administrasi"
    }
    if(code==="P"){
      return "Price"
    }
  }

  onUploadSuccess(response, file, fileList) {
    this.formData.attachment = response.attachment;
    this.formData.attachmentId = response.attachment.id;
    this.formData.attachmentName = response.attachment.name;
    this.formData.attachmentUrl = response.downloadUri;
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

      const input = this.attachmentHandler.get(this.subCriteria.id);

      // @ts-ignore
      input.attachmentId = this.formData.attachment.id;
      // @ts-ignore
      input.attachmentName = this.formData.attachment.fileName;
      // @ts-ignore
      input.attachmentUrl = this.formData.attachment.downloadUrl;

    this.retrieveAttachment(this.submissionId);
    this.attachmentFormVisible = false;
  }

  OpenAttachmentForm(row){
    this.subCriteria=null;
    this.subCriteria=row;
    this.attachmentFormVisible = true;
    this.fileList=[];
  }
  //

  save(status) {
    const data: any[] = [];
    this.answers.forEach(answer => {
      const {
        name,
        score,
        adOrganizationName,
        biddingSubCriteriaId,
        biddingSubCriteriaName,
        uid,
        ...proposal
      } = answer;
      proposal.id=answer.answerId;
      if(status==='SMT') {
        proposal.documentStatus = status;
      }else if(status==='SMT2'){
        proposal.documentAction = 'SMT';
      }
      proposal.biddingSubmissionId = this.submissionId;
      proposal.biddingSubCriteriaLineId = answer.id;
      data.push(proposal);
    })

    // Validate each record.
    const validator = new Schema(this.validationSchema);
    let valid = true;

    for (const proposal of data) {
      validator.validate(proposal, (errors: any) => {
        if (errors) {
          valid = false;
          this.$message.error(errors[0].message);
        }
      });

      if (!valid) {
        break;
      }
    }

    const validatorA = new Schema(this.validationSchemaAttachment);
    // let validA = true;

    for (const proposal of this.attachmentHandler.values()) {

      validatorA.validate(proposal, (errors: any) => {
        if (errors) {
          valid = false;
          this.$message.error(errors[0].message);
        }
      });

      if (!valid) {
        break;
      }
    }

    if (!valid) {
      return;
    }

    this.$emit('update:loading', true);

    const baseApiUrl = this.isAdministration ? baseApiProposalAdministration : baseApiProposalTechnical
    const evaluationName = proposalNameMap.get(this.data.evaluationMethodLineName);
    this.commonService(baseApiUrl + '/requirements')
      .create(data)
      .then(_res => {
        this.$message.success(`${evaluationName} proposal has been saved successfully`);
        this.PushProposalTechnicalFile();
        this.retrieveProposalData(this.submissionId);
      })
      .catch(err => {
        console.error('Failed to save the proposal. %O', err);
        this.$message.error(`Failed to save the ${evaluationName} proposal`);
      })
      .finally(() => this.$emit('update:loading', false));


  }

  PushProposalTechnicalFile(){
    for(const attachment of this.attachmentHandler.values()){

      const attachmentFile = {
        adOrganizationId:1,
        biddingSubmissionId:this.submissionId,
        // @ts-ignore
        biddingSubCriteriaId:attachment.id,
        // @ts-ignore
        cAttachmentId:attachment.attachmentId,
        // @ts-ignore
        id:attachment.technicalfileId,

      }
      // console.log("this attachmentFile",attachmentFile)
      try {
        this.commonService(baseApiProposalTechnicalFile)
          .create(attachmentFile)
          .then(_res =>{})
          .catch(err => {
            console.error('Failed',err);
            this.$message.error(`Failed upload Attachment `);
          })
          .finally(() => this.$emit('update:loading', false));
      }catch (e) {
      }
    }
  }

  retrieveAttachment(submissionId){
    this.commonService(baseApiProposalTechnicalFile)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `biddingSubmissionId.equals=${submissionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        for (const proposal of res.data) {
          try {
            const item = this.attachmentHandler.get(proposal.biddingSubCriteriaId);
            // @ts-ignore
            item.attachmentId = proposal.cAttachmentId;
            // @ts-ignore
            item.attachmentName = proposal.cattachmentName;
            // @ts-ignore
            item.attachmentUrl = proposal.attachmentUrl;
            // @ts-ignore
            item.technicalfileId=proposal.id;
          }catch (e) {}
        }
      })
      .catch(err => {
        console.error('Failed',err);
        this.$message.error(`Failed reload attachment `);
      })
      .finally(()=>this.loading=false);
  }
}
