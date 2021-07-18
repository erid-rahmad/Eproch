import Vue from 'vue';
import {Inject, Mixins, Watch} from "vue-property-decorator";
import Component from "vue-class-component";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";

const baseApiProposal = 'api/m-proposal-prices';
const baseApiProposalLine = 'api/m-proposal-price-lines';
const baseApiEvalResultLine='api/m-bidding-eval-result-lines';

const DetailPriceProp = Vue.extend({
  props: {
    title:String,
    evaluationFormProp: {
      type: Object,
      default: () => {
        return {};
      }
    },

  }
});

@Component
export default class DetailPrice extends Mixins( DetailPriceProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService



  menuPrice:boolean=false;
  isLoading:boolean=false;
  readOnly: Boolean=false;

  options= [{
    value: 'Pass',
    label: 'Pass'
   }, {
    value: 'Fail',
    label: 'Fail'
  }];

  private proposalPrice:any={};
  private proposalPriceLine:any=[];
  private evaluationResultLine:any={};
  private biddingSubmission:any={};
  created(){

    this.retrieveProposal(this.evaluationFormProp.biddingSubmission.id);
    this.retrieveEvalResultLine( this.evaluationFormProp.evaluationMethodLineId, this.evaluationFormProp.biddingEvalResultId)
    this.biddingSubmission=this.evaluationFormProp.biddingSubmission;

    // console.log("bidding submission",this.biddingSubmission)
  }

  retrieveEvalResultLine(evaluationMethodLineId:number,biddingEvalResultId:number){
    this.commonService(baseApiEvalResultLine)
      .retrieve({
        criteriaQuery: [
          `evaluationMethodLineId.equals=${evaluationMethodLineId}`,
          `biddingEvalResultId.equals=${biddingEvalResultId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        const data = res.data as any[];
        let result:any={};
        if (data.length) {
          result = {...result, ...data[0]};
            this.evaluationResultLine = result;
            if (this.evaluationResultLine.documentStatus === 'SMT') {
              this.readOnly = true;
              this.$emit('event', true);
            }
          }
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
          this.proposalPrice = { ...this.proposalPrice, ...res.data[0] };
          this.retrieveProposedLines(this.proposalPrice.id);

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
          size: 10,
          sort: ['id']
        }
      })
      .then(res => {
        this.proposalPriceLine=res.data;
        // console.log("this proposal line",this.proposalPriceLine);
      })
  }

  downloadAttachment(){
    window.open(this.proposalPrice.attachmentUrl, '_blank');
  }


  close() {
    this.$emit("close");
  }

  something() { this.$emit('event', 10);}


  save(){

    const data={
      biddingEvalResultId:this.evaluationFormProp.biddingEvalResultId,
      evaluationMethodLineId:this.evaluationFormProp.evaluationMethodLineId,
      adOrganizationId:1,
      active:true,
      id:this.evaluationResultLine.id,
      score:this.evaluationResultLine.score,
      status:this.evaluationResultLine.status,
      documentStatus:this.evaluationResultLine.documentStatus,
    }
      this.commonService(baseApiEvalResultLine)
        .create(data)
        .then(res => {
          // this.evaluationFormProp.SelectVendorScoringLine = res.data;
          this.$message.success('create ResultLine ');
          this.retrieveEvalResultLine( this.evaluationFormProp.evaluationMethodLineId, this.evaluationFormProp.biddingEvalResultId);

        })
        .catch(_err => this.$message.error('fail create record'));
    }

  saveSubmit(){
    this.evaluationResultLine.documentStatus='SMT';
    this.save();
    this.readOnly=true;
  }
}
