import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElForm } from 'element-ui/types/form';

const baseApiContract = 'api/m-contracts';
const baseApiVendorEvaluation = 'api/m-vendor-evaluations';

const VendorEvaluationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },

    loading: Boolean
  }
});

@Component({
  components: {
    AdInputList,
    AdInputLookup
  }
})
export default class VendorEvaluationDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  // Mockup Aggrement data.
  aggreements = new Map([
    ['13334', {
      documentNo: '13334',
      evaluationType: 'Vendor Otomotif',
      evaluationPeriod: 'Yearly',
      evaluationDate: new Date('2021-03-31')
    }]
  ]);

  marks= {
    0: '0 %',
    50: {
      style: {
        color: '#1989FA'
      },
      label: this.$createElement('strong', '50%')
    },
    100: '100 %',
  }

  evaluation: any = {};
  lines: any[] = [];
  validationSchema = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  async onContractIdChanged(id: number) {
    if (!id) {
        this.evaluation.evaluationTypeId = null;
      this.$set(this.evaluation, 'evaluationTypeName', null);
      this.$set(this.evaluation, 'contractEvaluationPeriod', null);
      this.lines = [];
    } else {
      const contract = await this.retrieveContract(id);
      if (contract) {
        this.evaluation.evaluationTypeId = contract.vendorEvaluationId;
        this.$set(this.evaluation, 'evaluationTypeName', contract.vendorEvaluationName);
        this.$set(this.evaluation, 'contractEvaluationPeriod', contract.evaluationPeriod);

        this.retrieveLines(contract.vendorEvaluationId);
      }
    }
  }

  onRateChanged(score: number) {
    console.log(score);
    const totalRates = this.lines
      .map((line: any): number => line.score || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.evaluation, 'score', totalRates / this.lines.length);
  }

  created() {
    this.evaluation = {...this.data};
  }

  async retrieveContract(id: number) {
    return this.commonService(baseApiContract)
      .find(id)
      .then(res => res);
  }

  retrieveLines(evaluationId: string) {
    this.commonService('/api/c-vendor-evaluation-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorEvaluationId.equals=${evaluationId}`
        ])
      })
      .then(res => this.lines = res.data);
  }

  save() {
    (<ElForm>this.$refs.mainForm).validate(passed => {
      if (passed) {
        const newRecord = !this.evaluation.id;
        
        this.$emit('update:loading', true);
        this.commonService(baseApiVendorEvaluation)
          [newRecord ? 'create' : 'update'](this.evaluation)
          .then(res => {
            this.$message.success('Vendor Evaluation has been saved successfully');
            this.evaluation = {...this.evaluation, ...res};
          })
          .catch(err => {
            console.error('Failed to save vendor evaluation', err);
            this.$message.error('Failed to save Vendor Evaluation');
          })
          .finally(() => this.$emit('update:loading', false));
      }
    })
  }
}
