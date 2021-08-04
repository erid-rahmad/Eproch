import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccountService from "@/account/account.service";
import { AccountStoreModule } from "@/shared/config/store/account-store";
import Schema from "async-validator";

const Props = Vue.extend({
  props: {
    data: Object,
    submissionId: Number
  }
});

@Component
export default class SubmissionForm extends Mixins(AccessLevelMixin, Props) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  evaluationMethodCriteria: any[] = [];
  attachmentFormVisible: boolean = false;

  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private action: string = "/api/c-attachments/upload";
  private file: any = {};
  private fileList :any =[];
  disabled:boolean=false;
  proposalStatus='';
  readOnlyChecklist:boolean=false;

  private formData:any={};
  loading = false;

  validationSchema: any = {};
  validationSchemaAttachment: any = {};

  answers: Map<number, any> = new Map();
  attachmentHandler: Map<number, any> = new Map();

  // selected rows
  subCriteria: any;

  get isVendor() {
    this.disabled=false;
    return AccountStoreModule.isVendor;
  }

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
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

    this.retrieveCriteria(this.data.preqMethodId);//, this.data.evaluationMethodLineId);
  }

  retrieveCriteria(methodId: number){
    this.loading = true;
    this.commonService("/api/c-prequal-method-lines").retrieve({
      criteriaQuery: [
        'active.equals=true',
        `prequalMethodId.equals=${methodId}`
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    }).then((res)=>{
      this.evaluationMethodCriteria = res.data;
      Promise.allSettled(
        this.evaluationMethodCriteria.map((row)=>{
          return new Promise<boolean>((resolve,reject)=>{
            this.commonService("/api/c-prequal-method-criteria").retrieve({
              criteriaQuery: [
                'active.equals=true',
                `prequalMethodLineId.equals=${row.id}`
              ],
              paginationQuery: {
                page: 0,
                size: 1000,
                sort:['id']
              }
            }).then((res)=>{
              row.criteria = res.data;
              Promise.allSettled(row.criteria.map((row)=>{
                return new Promise<boolean>((resolve,reject)=>{
                  this.commonService("/api/c-prequal-method-sub-criteria").retrieve({
                    criteriaQuery: [
                      'active.equals=true',
                      `prequalMethodCriteriaId.equals=${row.id}`
                    ],
                    paginationQuery: {
                      page: 0,
                      size: 1000,
                      sort:['id']
                    }
                  }).then((res)=>{
                    row.subCriteria = res.data;
                    Promise.allSettled(row.subCriteria.map((row)=>{
                      return new Promise<boolean>((resolve,reject)=>{
                        this.commonService("/api/c-bidding-sub-criteria-lines").retrieve({
                          criteriaQuery: [
                            'active.equals=true',
                            `biddingSubCriteriaId.equals=${row.biddingSubCriteriaId}`
                          ],
                          paginationQuery: {
                            page: 0,
                            size: 1000,
                            sort:['id']
                          }
                        }).then((res)=>{
                          row.questions = res.data;
                          resolve(true);
                        }).catch((err)=>{
                          console.log(err);
                          reject(false);
                        })
                      })
                    })).then((res)=>{
                      resolve(true);
                    })
                  }).catch((err)=>{
                    console.log(err);
                    reject(false);
                  })
                });
              })).then((res)=>{
                resolve(true);
              });
            }).catch((err)=>{
              console.log(err);
              reject(false);
            });
          })
        })
      ).then((res)=>{
        console.log(this.evaluationMethodCriteria);
        this.evaluationMethodCriteria.forEach((method)=>{
          method.criteria.forEach((criteria) => {
            criteria.subCriteria.forEach((subCriteria) => {
              subCriteria.attachmentId=null;
              subCriteria.attachmentName=null;
              subCriteria.attachmentUrl=null;
              this.attachmentHandler.set(subCriteria.biddingSubCriteriaId,subCriteria);

              subCriteria.questions.forEach((question) => {
                //question.requirement = this.requirements.get(subCriteriaLine.id)
                question.answer = null;
                question.documentEvaluation = false;

                // List of answers will be submitted later.
                this.answers.set(question.id, question);
              });
            });
          });
        });
        console.log(this.attachmentHandler);
        this.retrieveProposalData(this.data.id);
      });
    })
  }

  private retrieveProposalData(submissionId: number) {
    const baseApiUrl = "/api/m-prequalification-evals";
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `prequalificationSubmissionId.equals=${submissionId}`
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
            this.proposalStatus='Vendor Submitted';
            this.isVendor ?this.$emit('setReadOnly',true):null;
          }
          if(proposal.documentAction==='SMT'){
            this.readOnlyChecklist=true;
            this.proposalStatus='Buyer Submitted';
            !this.isVendor ? this.$emit('setReadOnly',true):null;
          }
          if(proposal.documentStatus!=='SMT'){
            this.readOnlyChecklist=true;
            this.proposalStatus=this.isVendor?'Drafted':'Waiting for Vendor’s response';
            !this.isVendor ? this.$emit('setReadOnly',true):null;

          }
          try {
            const item = this.answers.get(proposal.biddingSubCriteriaLineId);
            item.answer = proposal.answer;
            item.documentStatus=proposal.documentStatus;
            item.documentAction=proposal.documentAction;
            item.answerId=proposal.id;
            item.documentEvaluation = proposal.documentEvaluation;
          }catch (e) {}
        };
        this.retrieveAttachment(this.data.id);
      })
  }

  retrieveAttachment(submissionId){
    this.commonService("/api/m-prequalification-eval-files")
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `prequalificationSubmissionId.equals=${submissionId}`
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
            item.attachmentId = proposal.attachmentId;
            item.attachmentName = proposal.attachmentName;
            item.attachmentUrl = proposal.attachmentUrl;
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

  openAttachmentForm(subCriteria: any) {
    this.subCriteria=null;
    this.subCriteria=subCriteria;
    this.attachmentFormVisible = true;
    this.fileList=[];
  }

  handlePreview() {
    
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

  handleDownload(url) {
    window.open(url, '_blank');
  }

  cancelAttachment(biddingSubCriteria: any) {
    this.loading = true;

    const att = this.attachmentHandler.get(biddingSubCriteria.biddingSubCriteriaId);
    att.attachmentId = null;
    att.attachmentName = null;
    att.attachmentUrl = null;

    this.loading = false;
  }

  saveAttachment(){
    const input = this.attachmentHandler.get(this.subCriteria.biddingSubCriteriaId);

    input.attachmentId = this.formData.attachment.id;
    input.attachmentName = this.formData.attachment.fileName;
    input.attachmentUrl = this.formData.attachment.downloadUrl;

    // this.retrieveAttachment(this.data.id);
    this.attachmentFormVisible = false;
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

  save(status?: string){
    console.log(this.evaluationMethodCriteria);
    console.log(this.answers, this.attachmentHandler);

    const data: any[] = [];
    this.answers.forEach(answer => {
      const {
        name,
        score,
        adOrganizationName,
        biddingSubCriteriaId,
        biddingSubCriteriaName,
        ...proposal
      } = answer;
      proposal.id=answer.answerId;
      if(status) {
        if (status === 'SMT') {
          proposal.documentStatus = status;
          this.data.documentStatus = status;
          this.data.dateSubmit = new Date();

          this.commonService("/api/m-prequalification-submissions")
          .update(this.data)
          .then((res)=>{})
          .catch(err => {
            console.error('Failed',err);
            this.$message.error(`Failed updating header submit date.`);
          });
        } else if (status === 'SMT2') {
          proposal.documentAction = 'SMT';
        }
      }
      else {
        proposal.documentStatus=answer.documentStatus||'DRF';
        proposal.documentAction=answer.documentAction||'SMT';
      }

      proposal.prequalificationSubmissionId = this.data.id;
      proposal.biddingSubCriteriaLineId = answer.id;
      data.push(proposal);
    })

    console.log(data);

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

    this.loading = true;

    const baseApiUrl = "/api/m-prequalification-evals"
    const evaluationName = 'Prequalification'
    this.commonService(baseApiUrl + '/requirements')
      .create(data)
      .then(_res => {
        this.$message.success(`${evaluationName} proposal has been saved successfully`);
        this.PushProposalTechnicalFile();
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
        prequalificationSubmissionId:this.data.id,
        biddingSubCriteriaId:attachment.biddingSubCriteriaId,
        attachmentId:attachment.attachmentId,
        id:attachment.technicalfileId,
        active:true,
        uid:attachment.uid
      }
      console.log("this attachmentFile",attachmentFile)
      try {
        this.commonService("/api/m-prequalification-eval-files")
          [attachmentFile.id?'update':'create'](attachmentFile)
          .then(_res =>{})
          .catch(err => {
            console.error('Failed',err);
            this.$message.error(`Failed upload Attachment `);
          })
          .finally(() => {
            this.loading = false;
          });
      }catch (e) {
      }
    }
    
    this.retrieveProposalData(this.data.id);
  }
}