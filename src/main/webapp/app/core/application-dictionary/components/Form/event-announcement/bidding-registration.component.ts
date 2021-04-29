import { Component, Inject,Watch } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import {  mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import moment from 'moment';
import axios from 'axios';


@Component
export default class BiddingRegistration extends mixins(Vue2Filters.mixin, AccessLevelMixin,AlertMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;



  private biddingInvitationsGridData: any = {};
  private selecrow: any = {};
  reasonPA = false;
  private reason = '';
  showemaildetail = false;
  thisemail: any = {};
  private info: any = {};
  private pickdetailemail: any = {}; 

  mounted() {
    this.biddingInvitations();
    this.getAnnouncment();
    this.getemail();
    
  }
  help() {
    console.log("this info",this.info);    
  }

  @Watch('pickdetailemail')
  updateemail(pick: any) {
    console.log(pick);
    this.getemail();   
  }

  private biddingInvitations() {
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'

        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.biddingInvitationsGridData = res.data;
        console.log("announcmentGridData",this.biddingInvitationsGridData);

      });
  }

  getemail() {
    axios
      .get(`/api/m-bidding-invitations/${this.pickdetailemail.id}`)
      .then(response => (this.info = response.data));
    console.log("this info",this.info);    
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

  
  minat(row) {
    console.log(row);
    this.selecrow = row;
    this.selecrow.invitationStatus = "Terdaftar";
    this.UpdatebiddingInvitation();    
  }

  tidakminat(row) {
    console.log(row);
    this.selecrow = row;
    this.reasonPA = true;
  }

  tidakminatAction() {
    this.selecrow.invitationStatus = "Tidak Berminat";
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


}
