import {Component, Inject, Watch} from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import moment from 'moment';
import axios from 'axios';
import {AccountStoreModule as accountStore} from '@/shared/config/store/account-store';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import RegistDetail from "./regist-detail.vue";

const prequalificationRegistrationAPI ="api/m-prequal-registrations"
const ProjectInformationsAPI ="api/m-project-informations"

@Component({
  components:{
    RegistDetail
  }
})
export default class PrequalificationRegistration extends mixins(Vue2Filters.mixin, AccessLevelMixin, AlertMixin, ContextVariableAccessor) {

  reasonPA = false;
  acceptPA = false;
  accbutton = false;
  answerbutton = true;
  showemaildetail = false;
  thisemail: any = {};

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;
  @Inject('accountService')
  private accountService: () => AccountService;
  private preqRegistGridData: any = [];
  private selecrow: any = {};
  private reason = '';
  private info: any = {};
  private pickdetailemail: any = {};
  private bidding: any = {};
  private loading:boolean=false;
  private loadingProjectInfo:boolean=false;
  private loadingEmailDetail:boolean=false;
  private download: Map<number, string> = new Map();

  private vendorId = '';
  get isVendor() {
    return accountStore.isVendor;
  }

  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }

  created() {
    if (this.isVendor == true) {
      this.vendorId = accountStore.userDetails.cVendorId;
    }
    this.biddingInvitations();
    // this.getemail();
  }

  @Watch('pickdetailemail')
  updateemail(pick: any) {
    this.getemail();
  }

  getemail() {
    this.loadingEmailDetail=true;
    axios
      .get(`/api/m-prequal-registrations/${this.pickdetailemail.id}`)
      .then(response => (this.info = response.data))
      .finally(()=>this.loadingEmailDetail=false);
  }

  detail(row) {
    this.selecrow = row;
    this.acceptPA = true;
    if (row.registrationStatus === "D") {
      this.accbutton = true;
    }
    if (row.registrationStatus === "N") {
      this.acceptPA = false;
    }
  }

  closeDetail(){
    this.acceptPA = false;
    this.biddingInvitations();
  }

  minatAction() {
    this.selecrow.registrationStatus = "R";
    this.UpdatebiddingInvitation();
    this.acceptPA = false;
  }

  downloadAttachment(row) {
    window.open(`/api/c-attachments/download/${row.attachment.id}-${row.attachment.fileName}`, '_blank');
    if (this.selecrow.registrationStatus==="U") {
      this.selecrow.registrationStatus = "D";
      this.download.set(row.id, "yes");
      this.accbutton = true;
      for (const download_ of this.download.values()) {
        if (download_ === "no") {
          this.selecrow.registrationStatus = "U";
          this.accbutton = false;
        }
      }
      this.UpdatebiddingInvitation();
    }
  }

  printFileName(attachment: any) {
    return attachment?.fileName;
  }

  tidakminat(row) {

    this.selecrow = row;
    this.reasonPA = true;
    if (row.registrationStatus === "R") {
      this.reasonPA = false;
    }
  }

  tidakminatAction() {
    this.selecrow.registrationStatus = "N";
    this.selecrow.reason = this.reason;
    this.UpdatebiddingInvitation();
    this.reasonPA = false;
  }

  viewemail(row) {
    this.showemaildetail = true;
    this.pickdetailemail = row;
  }
  getStatus(row){
    if(row.registrationStatus==="R"){
      return "Registered"
    }
    else if(row.registrationStatus==="U" || row.registrationStatus==="D" ){
      return "Not Registered"
    }
    else if(row.registrationStatus==="N"){
      return "Not Interested"
    }
  }

  private biddingInvitations() {
    this.loading=true;
    this.commonService(prequalificationRegistrationAPI)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          // 'active.equals=true',
          `vendorId.equals=${this.vendorId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id,desc']
        }
      })
      .then(res => {
        this.preqRegistGridData = res.data;
//         console.log("preqRegistGridData",this.preqRegistGridData)
      })
      .finally(()=>this.loading=false);
  }

  private UpdatebiddingInvitation() {
    this.commonService(prequalificationRegistrationAPI)
      .update(this.selecrow)
      .then(res => {
      });
  }
}
