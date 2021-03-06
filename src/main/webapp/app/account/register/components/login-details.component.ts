import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service'
import { Inject } from 'vue-property-decorator'

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

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  nextPassed: boolean;
  columnSpacing = 32;
  rules = {
    login: {
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

  private retrieveLoginExist(username, email) {
    this.dynamicWindowService(`/api/users/registration-check`)
    .retrieve({
      criteriaQuery: [`login=${username}&email=${email}`]
    })
    .then(
      res => {
        console.log(res);
        if(!res.data){
          this.$notify({
            title: 'Error',
            dangerouslyUseHTMLString: true,
            message: "<strong>Login name or Email already registered!</strong> Please choose another one.",
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
      },
      err => {
        console.log(`Error : ${err}`);
      }
    )
    .catch(
      error => {
        console.log(error.response);
        console.log(`Error Catch : ${error}`);
      }
    );
  }

  validate(formIndex) {
    if (formIndex === 0) {
      if (this.login.password !== this.login.passwordConfirmation) {
        this.$notify({
          title: 'Error',
          dangerouslyUseHTMLString: true,
          message: "<strong>Please check,</strong> The password and its confirmation do not match!",
          type: 'error',
          duration: 3000
        });
      }else{
        this.retrieveLoginExist(this.login.login, this.login.email);
      }
      
    }
  }
}
