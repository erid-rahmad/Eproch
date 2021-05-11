import AccountService from '@/account/account.service';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import ScheduleEventMixin from '@/core/application-dictionary/mixins/ScheduleEventMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins } from "vue-property-decorator";

const baseApiPreBidMeeting = 'api/m-pre-bid-meetings';
const baseApiPreBidMeetingAttachment = 'api/m-pre-bid-meeting-attachments'

@Component
export default class PreBidMeeting extends Mixins(ScheduleEventMixin) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  gutterSize: number = 24;
  loadingAttachments: boolean = false;

  uploadAccept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .pdf";
  uploadEndpoint = '/api/c-attachments/upload';

  uploadedFiles: any[] = [];

  preBidMeeting = {
    id: null,
    adOrganizationId: AccountStoreModule.organizationInfo.id,
    biddingScheduleId: null,
  };

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
    console.log('preBidMeeting created. scheduleId:', this.biddingScheduleId);
    const query = this.$route.query;

    const biddingScheduleId = parseInt(query.biddingScheduleId as string);
    this.retrievePreBidMeeting(biddingScheduleId);
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
}