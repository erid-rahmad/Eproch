import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'

const LoginProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    login: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class LoginDetails extends LoginProps {
  columnSpacing = 32;
  rules = {
    username: {
      min: 3,
      pattern: '^[_.@A-Za-z0-9-]*$'
    },
    email: {
      type: 'email'
    },
    password: {
      min: 4,
      max: 100
    }
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate)
  }

  validate(formIndex) {
    if (formIndex === 0) {

      let nextPassed = true;
      if (this.login.password !== this.login.passwordConfirmation) {
        nextPassed = false;
        this.$notify({
          title: 'Error',
          dangerouslyUseHTMLString: true,
          message: "<strong>Please check,</strong> The password and its confirmation do not match!",
          type: 'error',
          duration: 3000
        });
      }else{
        (this.$refs.login as ElForm).validate((passed, errors) => {
          if (passed) {
            registrationStore.setLoginDetails(this.login);
          }
          this.eventBus.$emit('step-validated', { passed, errors })
        })
      }
      
    }
  }
}
