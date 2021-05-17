import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import PrequalificationForm from './prequalification-form.vue';


const VendorScoringProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {
      }
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

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}
  processing = false;
  criteriaPA: boolean = false;
  public criteriaOptions: any = {};
  public subCriteriaOptions: any = {};
  bidding: Record<string, any> = {};
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private num = 1958806;
  private line: any = {};
  private evaluationMethod: any = {};
  private value = '';
  private index: boolean = false;
  private evaluationMethodLine: any = {};
  private evaluationMethodCriteria: any = {};
  private biddingsubcriteria: any = {};
  private pickrow: any = {};
  private mainForm: any = {};
  private vendorScoring: any = {};

  evaluationList: any[] =[];
  evaluationTypes: any[] =[];

  get readOnly() {
    return this.bidding.biddingStatus === 'P';
  }

  @Watch('value')
  getLineVendorscoring() {
    this.mainForm.evaluationMethodId = this.value;
    this.mainForm.biddingId = this.bidding.id;
    this.mainForm.adOrganizationId = this.bidding.adOrganizationId;
    this.mainForm.active = true;
    this.getEvaluationMethodLine();
  }

  created() {
    this.bidding = { ...this.data };
    this.bidding.step = BiddingStep.SCORING;
    this.retrieveEvaluationList();
    this.retrieveEvaluationTypes();
    this.getVendorScoring();    
    this.getEvaluationMethod();
  }

  private getEvaluationMethod() {
    this.commonService('/api/c-evaluation-methods')
      .retrieve({
        criteriaQuery: [],
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
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `evaluationMethodId.equals=${this.value}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodLine = res.data;
        let arrayform = [];
        this.evaluationMethodLine.forEach(element => {
          console.log("this element", element);
          let a: any = {};
          a.evaluationMethodLineId = element.id;
          a.adOrganizationId = element.adOrganizationId;
          arrayform.push(a);
        });
        this.mainForm.vendorScoringLineDTOList = arrayform;
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
          this.vendorScoring = element
        });
        if (this.vendorScoring.evaluationMethodName.length) {
          this.index = true;
        }
        console.log("this.vendorScoring", this.vendorScoring);
      });
  }

  //=======================================================================

  private pushVendorScoring(data) {
    this.commonService('/api/m-vendor-scorings')
      .create(data)
      .then(res => {
        this.getVendorScoring();
      })    
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
    // this.reload();
    // console.log("this index", this.index);
    // console.log("this vendor scoring", this.vendorScoring.evaluationMethodName, this.vendorScoring.evaluationMethodName.length);
    this.pushVendorScoring(this.mainForm);    
  }

  closecriteriaPA() {
    this.criteriaPA = false;
    console.log("this close");
    this.pickrow = null;
  }

  printEvaluation(value: string) {
    return this.evaluationList.find(item => item.value === value)?.name || value;
  }

  printEvaluationType(value: string) {
    return this.evaluationTypes.find(item => item.value === value)?.name || value;
  }

  private retrieveEvaluationList() {
    this.commonService(null)
      .retrieveReferenceLists('evaluationList')
      .then(res => this.evaluationList = res)
      .catch(_err => console.warn('Failed getting the evaluation list'));
  }

  private retrieveEvaluationTypes() {
    this.commonService(null)
      .retrieveReferenceLists('evaluationType')
      .then(res => this.evaluationTypes = res)
      .catch(_err => console.warn('Failed getting the evaluation type list'));
  }

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
