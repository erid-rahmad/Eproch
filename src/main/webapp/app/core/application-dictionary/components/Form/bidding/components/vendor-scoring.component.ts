import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';

import { Component, Inject,Watch } from "vue-property-decorator";
import { Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import PrequalificationForm from './prequalification-form.vue';
import { watch } from 'fs';

const VendorScoringProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
})

// export enum SCORINGDATA {
//   INFO,
// }

@Component({
  components: {
    PrequalificationForm
  }
})
export default class VendorScoring extends Mixins(AccessLevelMixin, VendorScoringProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}


  processing = false;
  criteriaPA:boolean = false;

  public criteriaOptions: any = {};
  public subCriteriaOptions: any = {};

  private num = 1958806;
  bidding: Record<string, any> = {};
  private line: any = {};
  private evaluationMethod: any = {};
  private value = '';
  private index: boolean = false;
  private evaluationMethodLine: any = {};
  private evaluationMethodCriteria: any = {};
  private biddingsubcriteria: any = {};
  private pickrow: any = {};
  private mainForm: any = {};
  private arrayform = [];
  private vendorScoring: any = {};




  get readOnly() {
    return this.bidding.biddingStatus === 'In Progress';
  }

  created() {
    this.bidding = { ...this.data };
    console.log("this bidding",this.bidding);

    this.bidding.step = BiddingStep.SCORING;

    this.getEvaluationMethod();

    this.getVendorScoring();


  }

  @Watch('value')
  getLineVendorscoring() {
    this.mainForm.evaluationMethodId = this.value;
    this.mainForm.biddingId = this.bidding.id;
    this.mainForm.adOrganizationId = this.bidding.adOrganizationId;
    this.mainForm.active = true;
    this.getEvaluationMethodLine()

    }


  private getEvaluationMethod() {
    this.commonService('/api/c-evaluation-methods')
      .retrieve({
        criteriaQuery: [

        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethod = res.data;
        console.log("this.evaluationMethod", this.evaluationMethod);
      });
  }

  private getEvaluationMethodLine() {
    this.commonService('api/c-evaluation-method-lines')
      .retrieve({
        criteriaQuery: [
          `evaluationMethodId.equals=${this.value}`

        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodLine = res.data;
        this.evaluationMethodLine.forEach(element => {
          console.log("this element", element);
          let a :any= {};
          a.evaluationMethodLineId = element.id;
          a.adOrganizationId = element.adOrganizationId;
          this.arrayform.push(a);
        });
        this.mainForm.vendorScoringLineDTOList = this.arrayform;
        console.log("this.EvaluationMethodLine", this.evaluationMethodLine);
      });
  }

  private getVendorScoring() {
    this.commonService('/api/m-vendor-scorings')
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${this.bidding.id}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {       
        res.data.forEach(element => {
          this.vendorScoring=element
        });
        if (this.vendorScoring.evaluationMethodName.length) {
          return this.index = true;
        }
        console.log("this.vendorScoring", this.vendorScoring);
      });
  }

  private pushVendorScoring(data) {
    this.commonService('/api/m-vendor-scorings')
      .create(data);
  }

  addScoring(row) {
    this.pickrow = row;
    this.mainForm = this.mainForm;
    this.criteriaPA = true;
  }

  closeCriteriaPA() {
    this.criteriaPA = false;
    this.pickrow = null;
  }

  cekmainform() {
    console.log("this main", this.mainForm);
    // this.pushVendorScoring(this.mainForm);
    console.log("this index", this.index);
    console.log("this vendor scoring", this.vendorScoring.evaluationMethodName, this.vendorScoring.evaluationMethodName.length);
   
  }

  //=======================================================================

  validate() {
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if(passed){
        //this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
