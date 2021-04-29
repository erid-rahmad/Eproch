import AlertMixin from '@/shared/alert/alert.mixin';
import {  mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import { Component, Inject } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import moment from 'moment';

@Component
export default class Catalog extends mixins(Vue2Filters.mixin, AccessLevelMixin,AlertMixin, ContextVariableAccessor) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;


  index: boolean = true;
  private biddingInvitationsGridData: any = {};

  mounted() {
    this.biddingInvitations();
    
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

  formattime(date) {
    console.log("format");    
    return moment(String(date)).format('MM-DD-YYYY hh:mm');
  }

}
