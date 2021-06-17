import {Component, Inject, Watch} from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import moment from 'moment';
import axios from 'axios';
import {AccountStoreModule as accountStore} from '@/shared/config/store/account-store';

const biddingInvitationsAPI ="api/m-bidding-invitations"
const ProjectInformationsAPI ="api/m-project-informations"

@Component
export default class BiddingRegistration extends mixins(Vue2Filters.mixin, AccessLevelMixin, AlertMixin, ContextVariableAccessor) {

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
  private biddingInvitationsGridData: any = [];
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
      .get(`/api/m-bidding-invitations/${this.pickdetailemail.id}`)
      .then(response => (this.info = response.data))
      .finally(()=>this.loadingEmailDetail=false);
  }

  minat(row) {
    this.accbutton = false;
    this.retrieveProjectInformations(row.biddingId);
    this.selecrow = null;
    this.selecrow = row;
    this.acceptPA = true;
    if (row.invitationStatus === "D") {
      this.accbutton = true;
    }
    if (row.invitationStatus === "N") {
      this.acceptPA = false;
    }
  }

  minatAction() {
    this.selecrow.invitationStatus = "R";
    this.UpdatebiddingInvitation();
    this.acceptPA = false;
  }

  downloadAttachment(row) {
    window.open(`/api/c-attachments/download/${row.attachment.id}-${row.attachment.fileName}`, '_blank');
    if (this.selecrow.invitationStatus==="U") {
      this.selecrow.invitationStatus = "D";
      this.download.set(row.id, "yes");
      this.accbutton = true;
      for (const download_ of this.download.values()) {
        if (download_ === "no") {
          this.selecrow.invitationStatus = "U";
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
    if (row.invitationStatus === "R") {
      this.reasonPA = false;
    }
  }

  tidakminatAction() {
    this.selecrow.invitationStatus = "N";
    this.selecrow.reason = this.reason;
    this.UpdatebiddingInvitation();
    this.reasonPA = false;
  }

  viewemail(row) {
    this.showemaildetail = true;
    this.pickdetailemail = row;
  }
  getStatus(row){
    if(row.invitationStatus==="R"){
      return "Registered"
    }
    else if(row.invitationStatus==="U" || row.invitationStatus==="D" ){
      return "Not Registered"
    }
    else if(row.invitationStatus==="N"){
      return "Not Interested"
    }
  }

  private biddingInvitations() {
    this.loading=true;
    this.commonService(biddingInvitationsAPI)
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
        this.biddingInvitationsGridData = res.data;
//         console.log("biddingInvitationsGridData",this.biddingInvitationsGridData)
      })
      .finally(()=>this.loading=false);
  }

  private UpdatebiddingInvitation() {
    this.commonService(biddingInvitationsAPI)
      .update(this.selecrow)
      .then(res => {
      });
  }

  private retrieveProjectInformations(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingProjectInfo = true;
      this.commonService(ProjectInformationsAPI)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `biddingId.equals=${biddingId}`
          ]),
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id,desc']
          }
        })
        .then(res => {
          this.$set(this.bidding, 'projectInformations', res.data);
          this.$set(this.bidding, 'removedProjectInformations', []);
          resolve(true);
          this.download.clear();
          res.data.forEach(res=>{
            this.download.set(res.id,"no")
          });
        })
        .catch(err => {
          console.log('Failed to get project informations. %O', err);
          this.$message.error('Failed to get project informations');
          reject(false);
        })
        .finally(() => {
          this.loadingProjectInfo = false;
        });
    });
  }
}
