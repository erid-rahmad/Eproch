import { Component,  Mixins, Vue } from "vue-property-decorator";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";

const baseApiUrl = 'api/m-prequal-registrations';
const baseBiddingInvitationApiUrl = 'api/m-prequal-registrations';

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

  retrivedata(id: number) {
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${id}`,
          `registrationStatus.equals=R`
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
          `prequalificationId.equals=${id}`,
          `registrationStatus.equals=N`
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
          `prequalificationId.equals=${id}`,
          `registrationStatus.equals=U`
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

    this.commonService('/api/m-prequal-registrations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${id}`,
          `registrationStatus.in=D`,
          `registrationStatus.in=R`
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
