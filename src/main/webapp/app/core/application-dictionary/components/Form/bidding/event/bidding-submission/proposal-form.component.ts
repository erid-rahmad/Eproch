import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';

const ProposalFormProps = Vue.extend({
  props: {
    data: Object
  }
});

@Component
export default class ProposalForm extends Mixins(AccessLevelMixin, ProposalFormProps)  {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  evaluationMethodCriteria: any[] = [];
  requirements: Map<number, string> = new Map();

  answers: any[] = [];

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  created() {
    this.retrieveVendorScoringCriteria(this.data.id, this.data.evaluationMethodLineId);
  }

  retrieveVendorScoringCriteria(vendorScoringLineId: number, evaluationMethodLineId: number) {
    this.commonService(baseApiVendorScoringCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorScoringLineId.equals=${vendorScoringLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          this.requirements.set(criteria.biddingSubCriteriaLineId, criteria.requirement);
        });

        this.retrieveEvaluationMethodCriteria(evaluationMethodLineId);
      })
  }

  retrieveEvaluationMethodCriteria(evaluationMethodLineId: number) {
    this.commonService(baseApiEvalMethodCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `evaluationMethodLineId.equals=${evaluationMethodLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodCriteria = res.data.map((evalMethodCriteria: any) => {
          evalMethodCriteria.evalMethodSubCriteriaList.forEach((evalMethodSubCriteria: any) => {
            evalMethodSubCriteria.biddingSubCriteriaDTO.forEach((biddingSubCriteria: any) => {
              biddingSubCriteria.criteriaLineDTO.forEach((subCriteriaLine: any) => {
                subCriteriaLine.requirement = this.requirements.get(subCriteriaLine.id)
                subCriteriaLine.answer = null;
                subCriteriaLine.documentChecked = false;
                this.answers.push(subCriteriaLine);
              });
            });
          });

          return evalMethodCriteria;
        });
      })
  }

  save() {
    console.log('Saving proposal. data: %O', this.answers);
  }
}