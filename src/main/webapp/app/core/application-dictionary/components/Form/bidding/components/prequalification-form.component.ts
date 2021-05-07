import { Component, Vue, Watch ,Inject} from "vue-property-decorator";
import { Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';

const PrequalificationFormProps = Vue.extend({
  props: {
    // readOnly: Boolean,
    mainForm: {
      type: Object,
      default: () => {}
    },
    pickrow: {
      type: Object,
      default: () => {}
    },

  }
});

@Component
export default class PrequalificationForm extends Mixins(AccessLevelMixin, PrequalificationFormProps)  {


  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationMethodCriteria: any = {};


  mounted() {
    console.log("this.pickrow", this.pickrow);
    console.log("this.mainForm",this.mainForm);
    this.getEvaluationMethodCriteria(this.pickrow.id);
    
  }

  @Watch('pickrow')
  updatedata() {
    console.log("this.pickrow",this.pickrow);    
    this.getEvaluationMethodCriteria(this.pickrow.id);    
  }
  
  // @Watch('evaluationMethodCriteria',{deep:true})
  // updatedataevaluationMethodCriteria(value) {
  //   console.log("change",value);   
  //   }
    
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
  }

  private pushVendorScoringAnswer(data) {
    this.commonService('/api/m-vendor-scoring-criteria-answer')
      .create(data);
  }

  testing() {    
    console.log("this evaluation method", this.evaluationMethodCriteria); 

  }

  setpushVendorScoringAnswer() {    
    console.log("this evaluation method", this.evaluationMethodCriteria);
    this.pushVendorScoringAnswer(this.evaluationMethodCriteria);

  }

  testing2(row) {
    console.log("this row",row);   
  }


}