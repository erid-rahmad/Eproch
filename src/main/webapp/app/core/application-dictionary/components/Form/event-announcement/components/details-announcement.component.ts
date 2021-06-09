import { Component,  Mixins, Vue } from "vue-property-decorator";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";
const baseApiUrl = 'api/m-bidding-invitations';
const baseBiddingInvitationApiUrl = 'api/m-bidding-invitations';

const DetailsAnnouncementProps = Vue.extend({
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
export default class DetailsAnnouncement extends Mixins(DetailsAnnouncementProps,ScheduleEventMixin) {

  private vendorJoin: any = [];
  private vendorNotJoin: any = [];
  private vendorNoResponse: any = [];
  private vendorDownload: any = [];
  private invitationData: any = {};


  created() {
    this.retrivedata(this.moreinfo.id);
  }

  onMainFormUpdatedtes(mainForm: any) {
    console.log(mainForm)
    this.commonService(baseBiddingInvitationApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `biddingId.equals=${mainForm.biddingId}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        const data = res.data as any[];
        if (data.length) {
          this.invitationData = {...this.invitationData, ...data[0]};
          console.log("data", this.invitationData)
        }
        console.log("data1", data)
      })
      .finally(() => this.retrivedata(this.invitationData.announcementId))
  }

  retrivedata(announcementId: number) {
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${announcementId}`,
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
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${announcementId}`,
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

    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${announcementId}`,
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

    this.commonService('/api/m-bidding-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `announcementId.equals=${announcementId}`,
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
}
