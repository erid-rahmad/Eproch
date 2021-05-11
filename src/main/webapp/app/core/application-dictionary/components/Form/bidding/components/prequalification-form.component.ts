import {Component, Vue, Watch, Inject} from "vue-property-decorator";
import {Mixins} from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';

const PrequalificationFormProps = Vue.extend({
  props: {
    // readOnly: Boolean,
    pickrow: {
      type: Object,
      default: () => {
      }
    },

  }
});

@Component
export default class PrequalificationForm extends Mixins(AccessLevelMixin, PrequalificationFormProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationMethodCriteria: any = {};
  private MVendorScoringNestedDTO: any = {};
  private vendorScoringCriteria: any = {};
  private input: boolean = true;

  mounted() {
    console.log("this.pickrow", this.pickrow);
    this.getEvaluationMethodCriteria(this.pickrow.evaluationMethodLineId);
  }

  @Watch('pickrow')
  updatedata() {
    console.log("this.pickrow", this.pickrow);
    this.getEvaluationMethodCriteria(this.pickrow.evaluationMethodLineId);
  }

  getanswer(id) {
    const found = this.vendorScoringCriteria.find(element => element.biddingSubCriteriaLineId === id);
    return found.requirement;
  }

  setpushVendorScoringAnswer() {
    console.log("this sent");
    this.emptyhandler();
    this.MVendorScoringNestedDTO.evaluationMethodCriteriaNested = this.evaluationMethodCriteria;
    this.MVendorScoringNestedDTO.evaluationMethodLineId = this.pickrow.id;
    this.pushVendorScoringAnswer(this.MVendorScoringNestedDTO);
    this.$emit("closecriteriaPA");
  }

  emptyhandler() {
    console.log("this empty handler", this.evaluationMethodCriteria);
    this.evaluationMethodCriteria.forEach(element => {
      element.evalMethodSubCriteriaList.forEach(element => {
        element.biddingSubCriteriaDTO.forEach(element => {
          element.criteriaLineDTO.forEach(element => {
            console.log("this element", element);
            if (element.requirement === null) {
              console.log("this handler null");
              this.$notify.error({
                title: 'Error',
                message: 'Answer Required'
              });
            }
          });
        });
      });
    });

  }

  private getEvaluationMethodCriteria(lineId) {
    this.commonService('/api/c-evaluation-method-criteria')
      .retrieve({
        criteriaQuery: [
          `evaluationMethodLineId.equals=${lineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodCriteria = res.data;
        console.log("this.evaluationMethodCriteria", this.evaluationMethodCriteria);
      });
    this.getVendorScoringCriteria(this.pickrow.id);
  }

  private getVendorScoringCriteria(lineId) {
    this.commonService('/api/m-vendor-scoring-criteria')
      .retrieve({
        criteriaQuery: [
          `vendorScoringLineId.equals=${lineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorScoringCriteria = res.data;
        console.log("this vendoring criteria data", this.vendorScoringCriteria);

        console.log("this.vendorScoringCriteria", res.data.length);
        if (res.data.length) {
          this.input = false;
        } else {
          this.input = true;
        }
      });
  }

  private pushVendorScoringAnswer(data) {
    this.commonService('/api/m-vendor-scoring-criteria-answer')
      .create(data)
      .then(res => {
        this.pickrow = null;
      });
  }


}
