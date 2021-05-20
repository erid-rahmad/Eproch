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
  private biddingInvitationsGridData: any = {};
  private selecrow: any = {};
  private reason = '';
  private info: any = {};
  private pickdetailemail: any = {};
  private bidding: any = {};

  private vendorId = '';

  get isVendor() {
    return accountStore.isVendor;
  }

  public get settingsAccount(): any {
    return accountStore.account;
  }

  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }

  mounted() {
    if (this.isVendor == true) {
      this.vendorId = accountStore.userDetails.cVendorId;
    }
    this.biddingInvitations();
    this.getAnnouncment();
    this.getemail();
  }

  help() {
    console.log("this info", this.info);
  }

  @Watch('pickdetailemail')
  updateemail(pick: any) {
    console.log(pick);
    this.getemail();
  }

  getemail() {
    axios
      .get(`/api/m-bidding-invitations/${this.pickdetailemail.id}`)
      .then(response => (this.info = response.data));
    console.log("this info", this.info);
  }

  minat(row) {
    console.log(row);
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
    this.selecrow.invitationStatus = "D";
    this.UpdatebiddingInvitation();
  }

  printFileName(attachment: any) {
    return attachment?.fileName;
  }

  tidakminat(row) {
    console.log(row);
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
    console.log(row);
    this.showemaildetail = true;
    this.pickdetailemail = row;
  }

  formattime(date) {
    console.log("format");
    return moment(String(date)).format('MM-DD-YYYY hh:mm');
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
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          // 'active.equals=true',
          `vendorId.equals=${this.vendorId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.biddingInvitationsGridData = res.data;
        console.log("announcmentGridData", this.biddingInvitationsGridData);

      });
  }

  private getAnnouncment() {
    this.commonService(`/api/m-bidding-invitations/`)
      .find(1972751)
      .then(res => {
        this.thisemail = res.data;
      });
  }

  private UpdatebiddingInvitation() {
    this.commonService('/api/m-bidding-invitations')
      .update(this.selecrow)
      .then(res => {
        // this.biddingInvitationsGridData = res.data;
        // console.log("announcmentGridData",this.biddingInvitationsGridData);

      });
  }

  private retrieveProjectInformations(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      // this.loadingProjectInfo = true;
      this.commonService('/api/m-project-informations')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `biddingId.equals=${biddingId}`
          ]),
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id']
          }
        })
        .then(res => {
          this.$set(this.bidding, 'projectInformations', res.data);
          this.$set(this.bidding, 'removedProjectInformations', []);
          resolve(true);
        })
        .catch(err => {
          console.log('Failed to get project informations. %O', err);
          this.$message.error('Failed to get project informations');
          reject(false);
        })
        .finally(() => {
          // this.loadingProjectInfo = false;
        });
    });
  }


}
