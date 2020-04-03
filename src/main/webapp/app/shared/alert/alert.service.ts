import { Store } from 'vuex';
import { AlertStoreModule as alertStore } from '@/shared/config/store/alert-store'

export default class JhiAlertService {
  private store: Store<{}>;

  constructor(store: Store<{}>) {
    this.store = store;
    alertStore.init();
  }

  public showAlert(alertMessage: any, alertType = 'info') {
    alertStore.setType(alertType)
    alertStore.setMessage(alertMessage)
  }

  public countDownChanged(dismissCountDown: number) {
    alertStore.setCountDown(dismissCountDown)
  }
}
