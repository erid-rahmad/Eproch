import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorEvaluationDetail from './detail.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiVendorEvaluation = 'api/m-vendor-evaluations';

@Component({
  components: {
    VendorEvaluationDetail
  }
})
export default class VendorEvaluation extends mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  index = true;
  selectedRow: any = {};
  selectedDocumentAction: any = {};
  showDocumentActionConfirm = false;

  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }

  vendorEvaluations = [];

  get defaultDocumentAction() {
    return this.selectedRow.documentAction || 'DRF';
  }

  get documentApproved() {
    return this.selectedRow.processed && this.selectedRow.approved || false;
  }

  get documentTypeId() {
    return this.selectedRow.documentTypeId;
  }

  get isDraft() {
    return ! this.selectedRow.id || this.selectedRow.documentStatus === 'DRF';
  }

  onDocumentActionChanged(action: any) {
    this.selectedDocumentAction = action;
    this.showDocumentActionConfirm = true;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onSaveClicked() {
    (<any>this.$refs.evaluationForm).save();
  }

  onSubmitClicked() {
    (<any>this.$refs.evaluationForm).submit();
  }

  created() {
    this.retrieveEvaluations();
  }

  mounted() {
    this.setRow(this.vendorEvaluations[0]);
  }

  closeDetail() {
    this.index = true;
    this.retrieveEvaluations();
  }

  printStatus(status: string) {
    return this.documentStatuses[status];
  }

  private retrieveEvaluations() {
      this.loading = true;
      this.commonService(baseApiVendorEvaluation)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true'
          ])
        })
        .then(res => {
          this.vendorEvaluations = res.data;
        })
        .catch(err => {
          console.log('Failed to get vendor evaluations. %O', err);
          this.$message.error('Failed to get vendor evaluations');
        })
        .finally(() => this.loading = false);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetail(row: any) {
    if ( ! row) {
      this.selectedRow = {
        evaluationDate: null,
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        contractId: null,
        reviewerUserId: null,
      };
    } else {
      this.selectedRow = row;
    }

    this.index = false;
  }
}
