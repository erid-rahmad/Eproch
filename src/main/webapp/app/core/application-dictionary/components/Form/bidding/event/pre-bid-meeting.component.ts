import { Component, Inject, Mixins } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiSchedule = 'api/m-bidding-schedules';
const baseApiDateSet = 'api/m-prequalification-date-sets';
const baseApiPreBidMeeting = 'api/m-pre-bid-meetings';
const baseApiPreBidMeetingAttachment = 'api/m-pre-bid-meeting-attachments'
const baseApiAttachment = 'api/c-attachments';

@Component
export default class PreBidMeeting extends Mixins(AccessLevelMixin) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  biddingScheduleId: number = null;
  mainForm: any = {};
  uploadedFiles: any[] = [];

  gutterSize: number = 24;

  loading: boolean = false;
  loadingAttachments: boolean = false;
  uploadAccept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .pdf";
  uploadEndpoint = '/api/c-attachments/upload';

  preBidMeeting = {
    id: null,
    adOrganizationId: AccountStoreModule.organizationInfo.id,
    biddingScheduleId: null,
  };

  attachments = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file. %O', err);
  }

  onFilePreview(file: any) {
    console.log('preview file. %O', file);
    window.open(file.url, '_blank');
  }

  async beforeFileRemove(file: any, fileList: any[]) {
    try {
      await this.commonService(baseApiPreBidMeetingAttachment)
        .delete(file.id);
      
      this.$message.success('File has been successfully removed');
      return true;
    } catch (err) {
      this.$message.error(`Failed to remove file ${file.name}`);
      return false;
    }
  }

  onFileRemove(file: any) {
    const index = this.uploadedFiles.findIndex(item => item.id === file.id);
    this.uploadedFiles.splice(index, 1);
  }

  onUploadSuccess(response: any) {
    const attachment = response.attachment;
    
    const data = {
      id: null,
      cAttachmentId: attachment.id,
      preBidMeetingId: this.preBidMeeting.id,
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      name: attachment.fileName,
      url: response.downloadUrl
    };

    this.createPreBidMeetingAttachment(data);
    this.uploadedFiles.push(data);
  }

  created() {
    const query = this.$route.query;

    this.biddingScheduleId = parseInt(query.biddingScheduleId as string);
    this.retrieveBiddingSchedule(this.biddingScheduleId);
    this.retrievePreBidMeeting(this.biddingScheduleId);
  }

  viewParticipants() {
    
  }

  private retrievePreBidMeeting(scheduleId: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService(baseApiPreBidMeeting)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingScheduleId.equals=${scheduleId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.preBidMeeting = res.data[0];
          this.retrieveAttachments(this.preBidMeeting.id);
        } else {
          this.preBidMeeting.biddingScheduleId = scheduleId;
          this.createPreBidMeeting(this.preBidMeeting);
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

  private createPreBidMeeting(data: any) {
    this.loading = true;
    this.commonService(baseApiPreBidMeeting)
      .create(data)
      .then(res => this.preBidMeeting = res.data)
      .catch(_err => this.$message.error('Failed to save Pre-bid Meeting record'))
      .finally(() => this.loading = false);
  }

  private createPreBidMeetingAttachment(data: any) {
    this.loadingAttachments = true;
    this.commonService(baseApiPreBidMeetingAttachment)
      .create(data)
      .then(_res => {
        data.id = _res.id;
        this.$message.success('File has been successfully uploaded')
      })
      .catch(_err => this.$message.error('Failed to save Pre-bid Meeting attachment'))
      .finally(() => this.loadingAttachments = false);
  }

  private retrieveAttachments(preBidMeetingId: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loadingAttachments = true;
      this.commonService(baseApiPreBidMeetingAttachment)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `preBidMeetingId.equals=${preBidMeetingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 50,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          const list = res.data as any[];
          this.uploadedFiles = list.map(item => ({
            id: item.id,
            attachmentId: item.cAttachmentId,
            name: item.cAttachmentName,
            url: item.cAttachmentUrl
          }));
        }

        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get bidding schedules. %O', err);
        this.$message.error('Failed to get schedule details');
        resolve(false);
      })
      .finally(() => {
        this.loadingAttachments = false;
      });
    });
  }

  private retrieveBiddingSchedule(id: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService(baseApiSchedule)
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
          this.mainForm = res.data[0];
          this.retrieveDateSet(this.mainForm.id);
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
      this.commonService(baseApiDateSet)
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
          const dateSet = res.data[0];
          this.$set(this.mainForm, 'startDate', dateSet.startDate);
          this.$set(this.mainForm, 'endDate', dateSet.endDate);
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