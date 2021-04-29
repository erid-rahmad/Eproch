import { Component, Vue,Inject,Mixins } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import moment from 'moment';

const EventAnnouncement = Vue.extend({
  props: {
    page: {
      type: Number,
      default: () => {
        return 0;
      }
    },
    moreinfo: {
      type: Object,
      default: () => {}
    }
  }
});

@Component
export default class DetailsAnnouncementForm extends Mixins(AccessLevelMixin, EventAnnouncement) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  
  private vendorJoin: any = {};
  private vendorNotJoin: any = {};
  private vendorNoResponse: any = {};  

  mounted() {
    console.log("this more info", this.moreinfo);
    this.biddingInvitations();
    this.biddingInvitationsjoin();
    this.biddingInvitationsNoResponse();
  }

  private biddingInvitationsjoin() {
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=Terdaftar`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorJoin = res.data;
        console.log("vendorJoin",this.vendorJoin);
      });
  }

  private biddingInvitations() {
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=Tidak Berminat`          
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorNotJoin = res.data;
        console.log("vendornotjoin",this.vendorNotJoin);
      });
  }

  private biddingInvitationsNoResponse() {
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=Belum Terdaftar` 
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorNoResponse = res.data;
        console.log("vendorjoin",this.vendorNoResponse);
      });
  }

  formattime(date) {
    console.log("format");    
    return moment(String(date)).format('MM-DD-YYYY hh:mm');
  }

  back() {
    this.$emit("back");
  }
}