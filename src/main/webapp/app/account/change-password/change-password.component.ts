import { maxLength, minLength, required } from 'vuelidate/lib/validators';
import axios from 'axios';
import { mapGetters } from 'vuex';
import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

const validations = {
  resetPassword: {
    currentPassword: {
      required
    },
    newPassword: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254)
    },
    confirmPassword: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254)
    }
  }
};

@Component({
  validations,
  computed: {
    ...mapGetters({
      account: 'accountStore/account'
    })
  }
})
export default class ChangePassword extends Vue {
  success: string = null;
  error: string = null;
  doNotMatch: string = null;
  resetPassword: any = {
    currentPassword: null,
    newPassword: null,
    confirmPassword: null
  };

  public changePassword(): void {
    if (this.resetPassword.newPassword !== this.resetPassword.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      axios
        .post('api/account/change-password', {
          currentPassword: this.resetPassword.currentPassword,
          newPassword: this.resetPassword.newPassword
        })
        .then(() => {
          this.success = 'OK';
          this.error = null;
        })
        .catch(() => {
          this.success = null;
          this.error = 'ERROR';
        });
    }
  }

  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }
}
