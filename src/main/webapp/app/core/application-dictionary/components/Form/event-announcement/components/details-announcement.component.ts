import { Component, Vue,Inject,Mixins } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import moment from 'moment';

const baseApiUrl = 'api/m-bidding-invitations';

const BiddingInvitationResponseProps = Vue.extend({
  props: {
    page: {
      type: Number,
      default: () => {
        return 0;
      }
    },
    moreinfo: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class BiddingInvitationResponse extends Mixins(AccessLevelMixin, BiddingInvitationResponseProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private vendorJoin: any = {};
  private vendorNotJoin: any = {};
  private vendorNoResponse: any = {};
  private vendorDownload: any = {};

  created() {
    this.biddingInvitations();
    this.biddingInvitationsjoin();
    this.biddingInvitationsNoResponse();
    this.biddingInvitationsDownload();
  }

  private biddingInvitationsjoin() {
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=R`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorJoin = res.data;
      });
  }

  private biddingInvitations() {
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=N`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorNotJoin = res.data;
      });
  }

  private biddingInvitationsNoResponse() {
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.equals=U`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorNoResponse = res.data;
      });
  }

  private biddingInvitationsDownload() {
    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${this.moreinfo.id}`,
          `invitationStatus.in=D`,
          `invitationStatus.in=R`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorDownload = res.data;
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
