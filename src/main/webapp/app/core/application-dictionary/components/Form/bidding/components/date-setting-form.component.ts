import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const DateSettingFormProps = Vue.extend({
  props: {
    editMode: Boolean,
    scheduleId: Number,
    title: String,
    visible: Boolean
  }
});

@Component
export default class DateSettingForm extends Mixins(AccessLevelMixin, DateSettingFormProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  loading = false;
  processing = false;

  eventStatuses: any[] = [];

  formData: Record<string, any> = {
    biddingScheduleId: null,
    dateRange: [],
    startDate: null,
    endDate: null,
    status: null
  };

  validationSchema = {
    dateRange: {
      required: true,
      message: 'Schedule is required'
    },
    status: {
      required: true,
      message: 'Status is required'
    }
  };

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dialogTitle() {
    return this.title ? `${this.title} Date Setting` : '';
  }

  onClose() {
    this.clearForm();
    this.$emit('update:visible', false);
  }

  onDateRangeChanged(value: Date[]) {
    if (value == null) {
      return;
    }

    let status = 'N';
    const now = Date.now();
    const start = value[0].getTime();
    const end = value[1].getTime();

    if (now >= start) {
      if (now < end) {
        status = 'P';
      } else {
        status = 'F';
      }
    }

    this.$set(this.formData, 'status', status);
    this.formData.startDate = value[0].toISOString();
    this.formData.endDate = value[1].toISOString();
  }

  onOpen() {
    this.retrieveDateSet(this.scheduleId);
  }

  created() {
    this.retrieveEventStatuses();
  }

  clearForm() {
    this.formData = {
      biddingScheduleId: null,
      dateRange: [],
      startDate: null,
      endDate: null,
      status: null
    };
    (<ElForm>this.$refs.dateSettingForm).resetFields();
  }

  private retrieveDateSet(scheduleId: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService('api/m-prequalification-date-sets')
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
          const schedule = res.data[0];

          schedule.dateRange = [
            new Date(schedule.startDate),
            new Date(schedule.endDate)
          ];

          this.formData = schedule;
        } else {
          this.formData.biddingScheduleId = scheduleId;
        }

        this.formData.adOrganizationId = AccountStoreModule.organizationInfo.id;
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

  retrieveEventStatuses() {
    this.commonService(null)
      .retrieveReferenceLists('eventStatus')
      .then(list => {
        this.eventStatuses = list;
      })
      .catch(err => {
        console.error('Failed getting statuses. %O', err);
        this.$message.error('Failed to get event statuses');
      })
  }

  save() {
    (<ElForm>this.$refs.dateSettingForm).validate(passed => {
      if (passed) {
        this.processing = true;
        const { dateRange, ...record } = this.formData;
        const service = this.commonService('api/m-prequalification-date-sets');
        let promise: Promise<any> = service[this.formData.id ? 'update' : 'create'](record);

        promise.then(res => {
          this.onClose();
          this.$emit('saved', res);
        })
        .catch(err => {
          this.$emit('error', err);
        })
        .finally(() => this.processing = false);
      }
    });
  }
}