import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import {Component, Inject, Mixins, Vue, Watch} from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AdInputList from "@/shared/components/AdInput/ad-input-list.vue";
import AdInputLookup from "@/shared/components/AdInput/ad-input-lookup.vue";
import settings from "@/settings";
import HtmlEditor from "@/shared/components/HtmlEditor/index.vue";

const baseApiTask = 'api/c-tasks';
const baseApiContractTask = 'api/m-contract-tasks-save';
const baseApiContractTaskR = 'api/m-contract-tasks';
const baseApiContractTaskNegotiation = 'api/m-contract-task-negotiations';
const baseApiContractTaskReview = 'api/m-contract-task-reviewers';
const baseApiContractDocument = 'api/m-contract-clause-documents';


const ContractInfoProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})
@Component({
  components: {
    AdInputList,
    AdInputLookup,
    HtmlEditor
  }
})
export default class TaskComponent extends Mixins(AccessLevelMixin, ContractInfoProps) {

  index: number = 0;
  indexsNego: boolean = false;
  dialogVisibleView: boolean = false;
  dialogVisibleViewNego: boolean = false;
  AccRole:boolean=false;

  // @ts-ignoreFview
  loading: boolean = false;
  ContactTasks: any = []
  ContractDoc: any = []
  pickTask: any = {}
  mainForm: any = {}
  options = [{
    value: 'partial',
    label: 'Partial'
  }, {
    value: 'serial',
    label: 'Serial'
  }, {
    value: 'costume',
    label: 'Costume'
  },]
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  get formSettings() {
    return settings.form;
  }

  @Watch('indexs')
  button() {
    this.$emit('button', true);
  }

  getdisable(chat) {
    if (chat) {
      return true;
    } else {
      return false
    }

  }

  view(row) {
    this.mainForm = {
      contractTask: {
        id: null,
        name: '',
        contractDocument: null,
        documentStatus: '',
        documentAction: '',
        dueDate: '',
        active: true,
        adOrganizationId: this.data.adOrganizationId,
        contractId: '',
        taskId: ''
      },
      reviewers: [{
        id: '',
        picId: '',
        active: true,
        contractTaskId: '',
        adOrganizationId: this.data.adOrganizationId
      },],
      massage: [],
      massageLast:''
    }
    this.index = 1;
    this.pickTask = row
    this.mainForm.contractTask.taskId = row.id;
    this.mainForm.contractTask.contractId = this.data.id;
    this.retrieveContractDocument()
    this.retrieveContractTask(row.id, this.data.id)
    this.dialogVisibleView = true;
  }

  viewNego(row) {
    this.view(row)
    this.index = 2;

  }

  created() {
    this.retrieveTask();
  }

  Accept() {
    const accept = this.mainForm.massage[this.mainForm.massage.length - 1]
    console.log("this accept", accept);
    this.mainForm.contractTask.contractDocument = accept.contractDocument;
    this.save()
  }

  @Watch('this.mainForm.massage')
  lastDocument() {
    const accept = this.mainForm.massage[this.mainForm.massage.length - 1];
    this.mainForm.massageLast=accept
    return accept;
  }

  ViewContract(view){
    this.mainForm.massageLast=view
  }

  addChat() {
    const chat = {
      id: null,
      description: '',
      contractDocument: '',
      active: true,
      adOrganizationId: this.data.adOrganizationId,
      contractTaskId: '',
      cAttachmentId: '',
    }
    this.mainForm.massage.push(chat);
    this.lastDocument()
  }

  deleteChat(row) {
    console.log("this row index", row)
    this.mainForm.massage.splice(row.$index, 1)
  }

  EditContract(row) {
    const chat = {
      id: null,
      description: '',
      contractDocument: row.contractDocument,
      active: true,
      adOrganizationId: this.data.adOrganizationId,
      contractTaskId: '',
      cAttachmentId: '',
    }
    this.mainForm.massage.push(chat);
    this.lastDocument()
  }

  addPic() {
    const pic = {
      id: '',
      picId: '',
      contractTaskId: '',
      adOrganizationId: this.data.adOrganizationId,
      active: true,
    }
    this.mainForm.reviewers.push(pic)
  }

  retrieveTask() {
    // this.loading = true;
    this.commonService(baseApiTask)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ])
      })
      .then(res => {
        this.ContactTasks = res.data;
      })
      .catch(err => {
        console.log('Failed to get vendor evaluations. %O', err);
        this.$message.error('Failed to get vendor evaluations');
      })
      .finally(() => {
        this.loading = false
      });
  }

  retrieveContractDocument() {
    this.loading = true;
    this.commonService(baseApiContractDocument)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractId.equals=${this.mainForm.contractTask.contractId}`,
        ])
      })
      .then(res => {
        this.ContractDoc = res.data;
        console.log("conc", this.ContractDoc)
      })
      .catch(err => {
        console.log('Failed to retrieveContractTask. %O', err);
        this.$message.error('Failed to retrieveContractTask');
      })
      .finally(() => {
        this.loading = false;
      });
  }

  retrieveContractTask(taskId, contractId) {
    this.commonService(baseApiContractTaskR)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `taskId.equals=${taskId}`,
          `contractId.equals=${contractId}`,
        ])
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm.contractTask = res.data[0]
          this.loading = false;
          this.retrieveContractTaskReview(res.data[0].id)
          this.retrieveContractTaskNegotiation(res.data[0].id)
        }
      })
      .catch(err => {
        console.log('Failed to retrieveContractTask. %O', err);
        this.$message.error('Failed to retrieveContractTask');

      })
      .finally(() => {
      });
  }

  retrieveContractTaskReview(contractTaskId) {
    this.loading = true;
    this.commonService(baseApiContractTaskReview)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractTaskId.equals=${contractTaskId}`,
        ])
      })
      .then(res => {
        this.mainForm.reviewers = res.data
        this.loading = false;
      })
      .catch(err => {
        console.log('Failed to retrieveContractTask. %O', err);
        this.$message.error('Failed to retrieveContractTask');
      })
      .finally(() => {
        this.loading = false;
      });
  }

  retrieveContractTaskNegotiation(contractTaskId) {
    this.commonService(baseApiContractTaskNegotiation)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractTaskId.equals=${contractTaskId}`,
        ])
      })
      .then(res => {
        this.mainForm.massage = res.data;
        const data =this.lastDocument()
        if (this.isVendor) {
          if (data.createdBy.toLowerCase() === this.vendorInfo.name.toLowerCase()) {
            this.AccRole = true;
          }
        }
        !this.isVendor && data.createdBy ==="admin" ? this.AccRole=true:null;
      })
      .catch(err => {
        console.log('Failed to retrieveContractTask. %O', err);
        this.$message.error('Failed to retrieveContractTask');

      })
      .finally(() => {
      });
  }

  save() {
    this.commonService(baseApiContractTask)
      .create(this.mainForm)
      .then(res => {
        this.retrieveContractTask(this.mainForm.contractTask.taskId, this.data.id)
      })
      .catch(err => {
        console.error('Failed to save the contract', err);
        this.$message.error('Failed to save the contract');
      })
      .finally(() => this.loading = false);

  }
  publish() {
  }


}
