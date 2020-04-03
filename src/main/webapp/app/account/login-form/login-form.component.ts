import axios from 'axios';
import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import AccountService from '@/account/account.service';
import { Form as ElForm, Input } from 'element-ui'
import { DialogEventBus } from '@/core/event/dialog-event-bus';
import { ElInput } from 'element-ui/types/input';

@Component({
  watch: {
    $route() {
      this.visible = false
    }
  }
})
export default class LoginForm extends Vue {
  @Inject('accountService')
  private accountService: () => AccountService
  public visible = false
  public authenticationError = null
  private passwordType = 'password'
  private loading = false
  private capsTooltip = false

  public loginForm = {
    username: '',
    password: '',
    rememberMe: false
  }

  mounted() {
    DialogEventBus.$on('show-login', () => {
      this.visible = true
      this.focusField();
    })
    DialogEventBus.$on('hide-login', () => this.visible = false)
  }

  private focusField() {
    this.$nextTick(() => {
      if (this.loginForm.username === '') {
        (<ElInput>this.$refs.username).focus()
      } else if (this.loginForm.password === '') {
        (<ElInput>this.$refs.password).focus()
      }
    })
  }

  private checkCapslock(e: KeyboardEvent) {
    const { key } = e
    this.capsTooltip = key !== null && key.length === 1 && (key >= 'A' && key <= 'Z')
  }

  private showPwd() {
    if (this.passwordType === 'password') {
      this.passwordType = ''
    } else {
      this.passwordType = 'password'
    }
    this.$nextTick(() => {
      (this.$refs.password as Input).focus()
    })
  }

  public handleLogin() {
    (this.$refs.loginForm as ElForm).validate((valid: boolean) => {
      if (valid) {
        this.loading = true
        this.doLogin();
      } else {
        return false
      }
    })
  }

  private doLogin(): void {
    axios
      .post('api/authenticate', this.loginForm)
      .then(result => {
        const bearerToken = result.headers.authorization
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length)
          if (this.loginForm.rememberMe) {
            localStorage.setItem('jhi-authenticationToken', jwt)
          } else {
            sessionStorage.setItem('jhi-authenticationToken', jwt)
          }
        }
        this.authenticationError = false
        this.visible = false
        this.accountService().retrieveAccount()
      })
      .catch(() => {
        this.authenticationError = true
      })
  }
}
