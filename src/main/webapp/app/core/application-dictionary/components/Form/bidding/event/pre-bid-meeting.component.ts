import { Component, Inject, Mixins } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';

@Component
export default class PreBidMeeting extends Mixins(AccessLevelMixin) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  attachment = null;
  biddingScheduleId: number = null;
  biddingSchedule: any = {};
  dateSet: any = {};

  gutterSize: number = 24;

  loading: boolean = false;
  uploadAccept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  uploadEndpoint = '/api/c-attachments/upload';

  preBidMeeting = {
    attachment: null,
    attachmentId: null,
    biddingId: null,
    biddingScheduleId: null,
    startDate: null,
    endDate: null
  };

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadRemove(file, fileList) {
    console.log(file, fileList);
  }

  onUploadSuccess(response, file, fileList) {
      this.preBidMeeting.attachment = response.attachment;
      this.preBidMeeting.attachmentId = response.attachment.id;
  }

  created() {
    const query = this.$route.query;
    console.log('url query:', query);

    this.biddingScheduleId = parseInt(query.biddingScheduleId as string);
    this.retrieveBiddingSchedule(this.biddingScheduleId);
  }

  private retrieveBiddingSchedule(id: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService('/api/m-bidding-schedules')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `id.equals=${id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.biddingSchedule = res.data[0];
          console.log('schedule:', this.biddingSchedule);
          this.retrieveDateSet(this.biddingSchedule.id);
        }
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get bidding schedules. %O', err);
        this.$message.error('Failed to get schedule details');
        resolve(false);
      })
      .finally(() => {
        this.loading = false;
      });
    });
  }

  private retrieveDateSet(biddingScheduleId: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService('api/m-prequalification-date-sets')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingScheduleId.equals=${biddingScheduleId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.dateSet = res.data[0];
          console.log('dateSet:', this.dateSet);
        }
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get schedule date set. %O', err);
        this.$message.error('Failed to get schedule details');
        resolve(false);
      })
      .finally(() => {
        this.loading = false;
      });
    });
  }
}