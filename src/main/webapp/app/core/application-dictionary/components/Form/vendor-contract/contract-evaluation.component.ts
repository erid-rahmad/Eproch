import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { RawLocation } from 'vue-router';
import VendorEvaluationDetail from '../vendor-evaluation/detail.vue';
import { ElTable } from 'element-ui/types/table';

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

@Component({
  components:{
    VendorEvaluationDetail
  }
})
export default class ContractEvaluation extends Mixins(AccessLevelMixin, ContractEvaluationProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;

  evaluations: any[] = [];
  selectRow: any = {};
  vendorEvaluationDetail: boolean = false;
  
  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }

  get formSettings() {
    return settings.form;
  }

  get defaultDocumentAction() {
    return this.selectRow.documentAction || 'DRF';
  }

  get documentApproved() {
    return this.selectRow.processed && this.selectRow.approved || false;
  }

  get documentTypeId() {
    return this.selectRow.documentTypeId;
  }

  get isDraft() {
    return !this.selectRow.id || this.selectRow.documentStatus === 'DRF';
  }

  onDocumentActionChanged(action: any) {

  }

  onCurrentRowChanged(row: any) {
    this.selectRow = row;
  }

  onSaveClicked() {
    (<any>this.$refs.evaluationForm).save();
  }

  onSubmitClicked() {
    (<any>this.$refs.evaluationForm).submit();
  }

  created() {
    this.retrieveEvaluations(this.data.id);
  }

  refresh() {
    this.retrieveEvaluations(this.data.id);
    this.vendorEvaluationDetail = false;
  }

  mounted() {
    this.setRow(this.evaluations[0]);
  }

  closeDetail() {
    this.vendorEvaluationDetail = false;
    this.retrieveEvaluations(this.data.id);
  }

  printStatus(status: string) {
    return this.documentStatuses[status];
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.evaluations).setCurrentRow(record);
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
    if ( ! row) {
      this.selectRow = {
        evaluationDate: null,
        adOrganizationId: this.data.adOrganizationId,
        contractId: this.data.id,
        vendorId: this.data.vendorId,
        reviewerUserId: null,
      };
    } else {
      this.selectRow = row;
    }
    this.vendorEvaluationDetail = true;
  }
}
