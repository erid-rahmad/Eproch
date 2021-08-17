import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import axios from 'axios';
import { ElForm } from 'element-ui/types/form';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../../DynamicWindow/dynamic-window.service';

const NegotiationLineProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class BiddingNegotiationLineConversation extends mixins(AccessLevelMixin, NegotiationLineProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  negotiationChatApi = '/api/m-bidding-negotiation-chats';
  negotiationLineApi = '/api/m-bidding-negotiation-lines';
  negoPriceLineApi = '/api/m-bid-nego-price-lines';
  negoPriceApi = '/api/m-bid-nego-prices';
  line: any = {};
  chatForm: any = {text:"", publishToEmail:false};

  chatHistory: any[] = [
    {buyerText: 'Memulai proses perundingan hasil evaluasi.'},
    {vendorText: 'Kami telah merevisi beberapa dokumen. Harap diperiksa kembali.'}
  ];
  showChatForm = false;
  showDetail = false;
  submitting = false;
  isPercentage = false;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  private fileList: any[] = [];
  private fileList2: any[] = [];

  evaluationMethodCriteria: any = {};
  passfail = [{
    value: 'pass',
    label: 'Pass'
  }, {
    value: 'fail',
    label: 'Fail'
  },];

  answers: Map<number, any> = new Map();
  attachmentHandler: Map<number, any> = new Map();

  loading = false;
  loadingDetails = true;

  chatFormValidationSchema = {
    text: {
      required: true,
      message: 'Text is required.'
    }
  };

  get isVendor(){
    return AccountStoreModule.isVendor;
  }

  created() {
    console.log(this.data);
    this.line = {...this.data};
    this.line.prequalificationId = 2146553;
    //this.refreshChat();
  }

  refreshChat(){
    this.loading = true;
    this.commonService(this.negotiationChatApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `negotiationLineId.equals=${this.line.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.chatHistory = res.data;
    }).finally(()=>{this.loading = false});
  }

  openChatForm(){
    this.showChatForm = true;
  }

  clearForm(){
    this.chatForm.text = "";
    this.chatForm.publishToEmail = false;
    this.showChatForm = false;
    this.fileList = [];
  }

  onUploadChange(file: any) {
    this.fileList = [file];
  }

  onUploadChangeN(file: any) {
    this.fileList2 = [file];
  }

  handlePreview(file) {
    console.log(file);
    window.open(file.response?file.response.downloadUri:file.url, '_blank');
  }

  handleRemove(files, fileList) {
    this.fileList = [];
    this.chatForm.attachmentId = null;
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
      this.chatForm.attachmentId = response.attachment.id;
      this.fileList = [file];
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

  submitForm(){
    (<ElForm>this.$refs.chatForm).validate(passed => {
      if(passed){
        this.chatForm.biddingId = this.line.biddingId;
        this.chatForm.vendorId = this.line.vendorId;
        this.chatForm.negotiationLineId = this.line.id;
        this.chatForm.adOrganizationId = this.line.adOrganizationId;
        this.chatForm.active = true;
        if(this.isVendor) this.chatForm.vendorText = this.chatForm.text;
        else this.chatForm.buyerText = this.chatForm.text;

        this.submitting = true;

        this.commonService(this.negotiationChatApi).create(this.chatForm).then(
          (res)=>{
            console.log(res);
            this.$message.success("Conversation submitted.");
            this.clearForm();

            this.refreshChat();
          }
        ).catch((error)=>{
          this.$message.error("Failed to submit conversation.");
        }).finally(()=>{
          this.submitting = false;
        });
      }
    });
  }

  declineNegotiation(){
    this.submitting=true;
    this.line.negotiationStatus = 'disagreed';

    this.commonService(`${this.negotiationLineApi}/finalize/${this.line.id}`).create(this.line).then(
      (res)=>{
        console.log(res);
        this.$message.success("Negotiation agreed.");
        this.showDetail=false;
        this.refreshChat();
      }
    ).catch((error)=>{
      this.$message.error("Failed to submit negotiation.");
    }).finally(()=>{
      this.submitting = false;
    });
  }

  submitNegotiation(){
    this.submitting=true;
    if(this.isVendor){
      this.line.negotiationStatus = 'agreed';

      this.commonService(`${this.negotiationLineApi}/finalize/${this.line.id}`).create(this.line).then(
        (res)=>{
          console.log(res);
          this.$message.success("Negotiation agreed.");
          this.showDetail=false;
          this.refreshChat();
        }
      ).catch((error)=>{
        this.$message.error("Failed to submit negotiation.");
      }).finally(()=>{
        this.submitting = false;
      });
    }
  }

  viewEvalDetail(){
    this.showDetail = true;
    this.retrieveCriteria(2111651);
  }

  closeEvalDetail(){
    this.showDetail = false;
    this.answers.clear();
    this.attachmentHandler.clear();
    this.evaluationMethodCriteria=[];
  }

  retrieveCriteria(methodId: number){
    this.loadingDetails = true;
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
        this.retrieveProposalData(2147253);
      });
    })
  }

  private retrieveProposalData(submissionId: number) {
    const baseApiUrl = "/api/m-prequalification-evals";
    this.commonService("/api/m-prequalification-criteria").retrieve({
      criteriaQuery: [
        'active.equals=true',
        `prequalificationId.equals=${this.line.prequalificationId}`
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    }).then((res)=>{
      console.log(res.data);
      res.data.forEach(element => {
        let q = this.answers.get(element.biddingSubCriteriaLineId)
        if(q) {
          q.requirement = element.requirement;
        }
      });
    })
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
          try {
            const item = this.answers.get(proposal.biddingSubCriteriaLineId);
            item.answer = proposal.answer;
            item.passFail = proposal.passFail;
            item.documentStatus=proposal.documentStatus;
            item.documentAction=proposal.documentAction;
            item.answerId=proposal.id;
            item.documentEvaluation = proposal.documentEvaluation;
          }catch (e) {}
        };
        this.retrieveAttachment(2147253);
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
      .finally(()=>this.loadingDetails=false);
  }

  downloadAttachment(row){
    let downloadUrl:string = row.downloadUrl;
    if(!downloadUrl.startsWith('https')){
      downloadUrl = downloadUrl.replace('http','https');
    }
    window.open(downloadUrl, '_blank');
  }

  truncateDecimals = function (number, digits) {
    var multiplier = Math.pow(10, digits),
        adjustedNum = number * multiplier,
        truncatedNum = Math[adjustedNum < 0 ? 'ceil' : 'floor'](adjustedNum);

    return truncatedNum / multiplier;
  };
}