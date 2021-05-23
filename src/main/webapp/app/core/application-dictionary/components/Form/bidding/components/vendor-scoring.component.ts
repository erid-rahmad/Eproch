import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import { Component, Inject, Mixins } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import PrequalificationForm from './prequalification-form.vue';
import { ElForm } from 'element-ui/types/form';

const baseApiVendorScoring = 'api/m-vendor-scorings';
const baseApiVendorScoringLine = 'api/m-vendor-scoring-lines';
const baseApiEvaluationMethod = 'api/c-evaluation-methods';
const baseApiEvaluationMethodLine = 'api/c-evaluation-method-lines';

const VendorScoringProp = Vue.extend({
  props: {
    editMode: Boolean,

    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

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

  requirementsUpdated: boolean = false;
  savingRequirements: boolean = false;
  criteriaPA: boolean = false;

  bidding: Record<string, any> = {};

  mainForm: any = {};

  validationSchema = {
    evaluationMethodId: {
      required: true,
      message: 'Evaluation Method is required'
    }
  };
  
  evaluationMethods: any[] = [];
  evaluationMethodLines: any[] = [];
  evaluationList: any[] = [];
  evaluationTypes: any[] = [];
  
  selectedRow: any = {};

  get readOnly() {
    return this.bidding.biddingStatus === 'P';
  }

  onEvaluationMethodChange(value: number) {
    this.$emit('change');
    this.retrieveEvaluationMethodLines(value);
  }

  created() {
    this.bidding = { ...this.data };
    this.bidding.step = BiddingStep.SCORING;
    this.retrieveEvaluationMethods();
    this.retrieveEvaluationList();
    this.retrieveEvaluationTypes();
    this.retrieveVendorScoring(this.bidding.id);
  }

  private retrieveEvaluationMethods() {
    this.commonService(baseApiEvaluationMethod)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethods = res.data;
      });
  }

  private retrieveEvaluationMethodLines(evaluationMethodId: number) {
    this.commonService(baseApiEvaluationMethodLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `evaluationMethodId.equals=${evaluationMethodId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodLines = res.data;
        
        if (this.mainForm.id) {
          this.retrieveVendorScoringLine(this.mainForm.id);
        }
      });
  }

  private retrieveVendorScoring(biddingId: number) {
    this.commonService(baseApiVendorScoring)
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${biddingId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm = res.data[0];
          const evaluationMethodId = this.mainForm.evaluationMethodId;
          this.$set(this.mainForm, 'evaluationMethodId', evaluationMethodId);
          this.retrieveEvaluationMethodLines(evaluationMethodId);
        }
      });
  }

  private retrieveVendorScoringLine(vendorScoringId: number) {
    this.commonService(baseApiVendorScoringLine)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorScoringId.equals=${vendorScoringId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodLines = res.data.map(line => {
          const item = this.evaluationMethodLines.find(evaluationMethodLine => evaluationMethodLine.id === line.evaluationMethodLineId);
          item.evaluationMethodLineId = item.id;
          item.id = line.id;
          item.vendorScoringId = vendorScoringId;
          return item;
        });
      });
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

  /**
   * Invoked before proceeding to the next step.
   */
  save(changeStep: boolean) {
    (<ElForm>this.$refs.mainForm).validate(passed => {
      if (passed) {
        this.mainForm.adOrganizationId = this.bidding.adOrganizationId;
        this.mainForm.biddingId = this.bidding.id;
        this.mainForm.vendorScoringLineDTOList = this.evaluationMethodLines.map(line => {
          return {
            adOrganizationId: line.adOrganizationId,
            evaluationMethodLineId: line.id,
          };
        });

        this.commonService(baseApiVendorScoring)
          [this.mainForm.id ? 'update' : 'create'](this.mainForm)
          .then(res => {
            this.evaluationMethodLines = this.evaluationMethodLines.map(line => {
              const item = res.vendorScoringLineDTOList.find(savedLine => savedLine.evaluationMethodLineId === line.id);
              line.id = item.id;
              line.evaluationMethodLineId = item.evaluationMethodLineId;
              return line;
            });

            this.$message.success('Vendor Scoring has been saved successfully');
            this.$emit('saved', {
              data: res,
              changeStep
            });
          })
          .catch(err => {
            console.log('Failed to save bidding schedule. %O', err);
            this.$message.error('Failed to save bidding schedule');
          });
      }
    });
    
  }

  saveRequirements() {
    (<any>this.$refs.prequalificationForm).save();
  }

  editRequirement(row) {
    this.selectedRow = row;
    this.criteriaPA = true;
  }

  closeCriteriaPA() {
    this.criteriaPA = false;
    this.selectedRow = {};
  }

  printEvaluation(value: string) {
    return this.evaluationList.find(item => item.value === value)?.name || value;
  }

  printEvaluationType(value: string) {
    return this.evaluationTypes.find(item => item.value === value)?.name || value;
  }
}
