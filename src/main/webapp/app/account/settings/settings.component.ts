import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import axios from 'axios';
import { EMAIL_ALREADY_USED_TYPE } from '@/constants';
import { Vue, Component, Inject } from 'vue-property-decorator';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

const validations = {
  settingsAccount: {
    firstName: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50)
    },
    lastName: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50)
    },
    email: {
      required,
      email,
      minLength: minLength(5),
      maxLength: maxLength(254)
    }
  }
};

@Component({
  validations
})
export default class Settings extends Vue {
  public success: string = null;
  public error: string = null;
  public errorEmailExists: string = null;
  public languages: any = translationStore.languages || [];

  public save(): void {
    this.error = null;
    this.errorEmailExists = null;
    axios
      .post('api/account', this.settingsAccount)
      .then(() => {
        this.error = null;
        this.success = 'OK';
        this.errorEmailExists = null;
      })
      .catch(error => {
        this.success = null;
        this.error = 'ERROR';
        if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.errorEmailExists = 'ERROR';
          this.error = null;
        }
      });
  }

  public get settingsAccount(): any {
    return accountStore.account;
  }

  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }
}
