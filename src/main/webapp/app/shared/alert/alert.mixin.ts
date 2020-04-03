import { Component, Inject, Vue } from 'vue-property-decorator';
import AlertService from '@/shared/alert/alert.service';
import { AlertStoreModule as alertStore } from '@/shared/config/store/alert-store';

@Component
export default class AlertMixin extends Vue {
  @Inject('alertService') protected alertService: () => AlertService;

  public dismissCountDown: number = alertStore.dismissCountDown;
  public dismissSecs: number = alertStore.dismissTimeout;
  public alertType: string = alertStore.type;
  public alertMessage: any = alertStore.message;

  public getAlertFromStore() {
    this.dismissCountDown = alertStore.dismissCountDown;
    this.dismissSecs = alertStore.dismissTimeout;
    this.alertType = alertStore.type;
    this.alertMessage = alertStore.message;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }
}
