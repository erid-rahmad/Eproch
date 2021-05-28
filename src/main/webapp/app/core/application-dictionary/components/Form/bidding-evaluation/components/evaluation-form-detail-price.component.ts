import Vue from 'vue';
import {Inject, Mixins, Watch} from "vue-property-decorator";
import Component from "vue-class-component";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";

const baseApiProposal = 'api/m-proposal-prices';
const baseApiProposalLine = 'api/m-proposal-price-lines';
const baseApiEvalResultLine='api/m-bidding-eval-result-lines';

const DetailPriceProp = Vue.extend({
  props: {
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
    this.evaluationResultLine=this.evaluationFormProp.evaluationResultLine;
    console.log("masuk",this.evaluationFormProp)
    this.biddingSubmission=this.evaluationFormProp.biddingSubmission;
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
      })
  }
  close() {
    this.$emit("close");
  }

  save(){
      this.commonService(baseApiEvalResultLine)
        .create(this.evaluationResultLine)
        .then(res => {
          this.evaluationFormProp.SelectVendorScoringLine = res.data;
          this.$message.success('create ResultLine ');
        })
        .catch(_err => this.$message.error('fail create record'));
    }
}
