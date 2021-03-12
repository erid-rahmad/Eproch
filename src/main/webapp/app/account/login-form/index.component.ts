import TranslationService from '@/locale/translation.service';
import axios from 'axios';
import { Form as ElForm, Input } from 'element-ui';
import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import { Route } from 'vue-router';
import { Dictionary } from 'vue-router/types/router';
import AccountService from '../account.service';

@Component
export default class Login extends Vue {
  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('translationService')
  private translationService: () => TranslationService;

  invitation = { code: null };
  loginForm = { username: '', password: '', rememberMe: false };

  loginRules = {
    username: {
      message: 'Username is required',
      type: 'string',
      required: true,
    },
    password: {
      message: 'Password is required',
      type: 'string',
      required: true,
    },
  };

  loading = false;
  showInvitationCodeDialog = false;
  capsTooltip = false;
  private redirect?: string;
  private otherQuery: Dictionary<string> = {};

  @Watch('$route', { immediate: true })
  onRouteChange(route: Route) {
    // TODO: remove the "as Dictionary<string>" hack after v4 release for vue-router
    // See https://github.com/vuejs/vue-router/pull/2050 for details
    const query = route.query as Dictionary<string>;
    if (query) {
      this.redirect = query.redirect;
      this.otherQuery = this.getOtherQuery(query);
    }
  }

  created() {
    this.translationService().refreshTranslation('en');
  }

  mounted() {
    if (this.loginForm.username === '') {
      (this.$refs.username as Input).focus();
    } else if (this.loginForm.password === '') {
      (this.$refs.password as Input).focus();
    }
  }

  checkCapslock(e: KeyboardEvent) {
    const { key } = e;
    this.capsTooltip = key?.length === 1 && key >= 'A' && key <= 'Z';
  }

  handleLogin() {
    (this.$refs.loginForm as ElForm).validate((valid: boolean) => {
      if (valid) {
        axios
          .post('api/authenticate', this.loginForm)
          .then(result => {
            const bearerToken = result.headers.authorization;
            if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
              const jwt = bearerToken.slice(7, bearerToken.length);
              if (this.loginForm.rememberMe) {
                localStorage.setItem('jhi-authenticationToken', jwt);
              } else {
                sessionStorage.setItem('jhi-authenticationToken', jwt);
              }
            }
            this.accountService().retrieveAccount();
          })
          .catch(() => {
            this.$message.error({
              message: 'Login failed',
            });
          })
          .finally(() => {
            this.loading = false;
          });
      }
    });
  }

  // TODO Add invitation code in phase 2.
  verifyInvitationCode() {
    setTimeout(() => {
      this.$message.error({
        message: 'Invalid code'
      });
    }, 1000);
  }

  private getOtherQuery(query: Dictionary<string>) {
    return Object.keys(query).reduce((acc, cur) => {
      if (cur !== 'redirect') {
        acc[cur] = query[cur];
      }
      return acc;
    }, {} as Dictionary<string>);
  }
}
