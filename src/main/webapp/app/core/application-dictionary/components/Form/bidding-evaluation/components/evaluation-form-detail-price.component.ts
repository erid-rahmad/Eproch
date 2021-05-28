import Vue from 'vue';
import {Inject, Mixins, Watch} from "vue-property-decorator";
import Component from "vue-class-component";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";

const baseApiProposal = 'api/m-proposal-prices';
const baseApiProposalLine = 'api/m-proposal-price-lines';

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

  ///////////


  data() {
    return {
      options: [{
        value: 'Pass',
        label: 'Pass'
      }, {
        value: 'Fail',
        label: 'Fail'
      }],
      value: ''
    }
  }
    "biddingLineList"= [
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T03:30:16.503651Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-06-17T03:30:16.503651Z",
        "id": 1958866,
        "lineNo": null,
        "quantity": 10.00,
        "ceilingPrice": 150000000.00,
        "ceilingPrice1": 150000000.00,
        "totalCeilingPrice": 1500000000.00,
        "deliveryDate": "2021-06-17T03:30:16.503651Z",
        "remark": null,
        "uid": "debd79d6-2094-457e-906e-aa5aa5744e71",
        "active": true,
        "subItemId": null,
        "subItem": null,
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "costCenterId": 1956751,
        "costCenterName": "Marketing",
        "productId": 1950015,
        "productName": "BTN FIXEDASSET1",
        "uomId": 46751,
        "uomName": "Each",
        "doc": "checked",
        "delivery": "2021-06-17T03:30:16.503651Z",
        "totalpricesubmision":1500000000.00
      },

    ];
  ///////////

  private proposalPrice:any={};
  private proposalPriceLine:any={};
  created(){
    this.retrieveProposal(this.evaluationFormProp.biddingSubmission.id);
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
          console.log("proposal price",this.proposalPrice)
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
        console.log("line",this.proposalPriceLine);

      })

  }
  close() {
    this.$emit("close");
  }
}
