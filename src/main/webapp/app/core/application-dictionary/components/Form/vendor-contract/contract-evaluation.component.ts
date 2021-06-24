import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { RawLocation } from 'vue-router';

const baseApiVendorEvaluation = 'api/m-vendor-evaluations';

const ContractEvaluationProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class ContractEvaluation extends Mixins(AccessLevelMixin, ContractEvaluationProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;

  evaluations: any[] = [];
  selectRow:any={};
  vendorEvaluationDetail:boolean=false;

  get formSettings() {
    return settings.form;
  }

  created() {
    this.retrieveEvaluations(this.data.id);
  }

  private retrieveEvaluations(contractId: number) {
      this.loading = true;
      this.commonService(baseApiVendorEvaluation)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `contractId.equals=${contractId}`
          ])
        })
        .then(res => {
          this.evaluations = res.data;
        })
        .catch(err => {
          console.log('Failed to get vendor evaluations. %O', err);
          this.$message.error('Failed to get vendor evaluations');
        })
        .finally(() => this.loading = false);
  }

  viewDetails(row: any) {
    // const location: RawLocation = {
    //   path: '/supplier-management/vendor-evaluations',
    // };
    //
    // location.query = {
    //   t: '' + Date.now(),
    //   id: row.id
    // };
    //
    // this.$router.push(location);
    this.selectRow=row;
    this.vendorEvaluationDetail=true;


  }
}
