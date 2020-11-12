import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'
import VueRouter, { Route } from 'vue-router'
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { UserStoreModule as userStore } from '@/shared/config/store/user-store'
import { PermissionStoreModule as permissionStore } from '@/shared/config/store/permission-store'
// import i18n from '@/lang' // Internationalization
import settings from './settings'
import { DialogEventBus } from './core/event/dialog-event-bus'
import AccountService from './account/account.service'
import VueI18n from 'vue-i18n'

NProgress.configure({ showSpinner: false });

const whiteList = ['/',
  '/auth-redirect',
  '/redirect',
  '/forbidden',
  '/not-found',
  '/login',
  '/register',
  '/account/activate',
  '/account/reset/request',
  '/account/reset/finish'
];

export default class RouterValidation {

  private router: VueRouter;
  private accountService: AccountService;
  private i18n: VueI18n;

  constructor(router: VueRouter, i18n: VueI18n, accountService: AccountService) {
    this.router = router;
    this.i18n = i18n;
    this.accountService = accountService;
  }

  /**
   * runBeforeEachHook
   */
  public async runBeforeEachHook(to: Route, from: Route, next: any) {
    // Start progress bar
    NProgress.start();

    // Determine whether the user has logged in
    if (accountStore.authenticated) {
      if (to.path === '/login') {
        // If is logged in, redirect to the home page
        next({ path: '/' });
        NProgress.done();
      } else {
        // Check whether the user has obtained his permission roles
        if (accountStore.authorities.size === 0) {
          console.log('User authorities are not set. Getting account details.');
          try {
            // Note: roles must be a object array! such as: ['admin'] or ['developer', 'editor']
            const roles = accountStore.authorities;
            // Generate accessible routes map based on role
            await permissionStore.generateRoutes(roles);
            // Dynamically add accessible routes
            router.addRoutes(permissionStore.dynamicRoutes);
            // Hack: ensure addRoutes is complete
            // Set the replace: true, so the navigation will not leave a history record
            next({ ...to, replace: true });
          } catch (err) {
            sessionStorage.setItem('requested-url', to.fullPath);
            // Remove token and redirect to login page
            localStorage.removeItem('jhi-authenticationToken');
            sessionStorage.removeItem('jhi-authenticationToken');
            await accountStore.logout();
            Message.error(err || 'Has Error');
            next(false);
            NProgress.done();
            DialogEventBus.$emit('show-login');
          }
        } else {
          console.log('User is authenticated and has authorities.');
          next();
        }
      }
    } else {
      // Has no token
      if (whiteList.indexOf(to.path) !== -1) {
        // In the free login whitelist, go directly
        console.log(`${to.path} is in white list.`);
        next();
      } else {
        // Other pages that do not have permission to access are redirected to the login page.
        sessionStorage.setItem('requested-url', to.fullPath);
        console.log('Forbidden!');
        next(`/forbidden`);
        if (!this.accountService.hasToken()) {
          DialogEventBus.$emit('show-login');
        }
        NProgress.done();
      }
    }
  }

  /**
   * runAfterEachHook
   */
  public runAfterEachHook(to: Route) {
    // set page title
    document.title = this.getPageTitle(to.meta.title);
    NProgress.done();
  }

  private getPageTitle = (key: string) => {
    const hasKey = this.i18n.te(`route.${key}`);
    if (hasKey) {
      const pageName = this.i18n.t(`route.${key}`);
      return `${pageName} - ${settings.title}`;
    }
    return `${settings.title}`;
  }

}

// router.beforeEach(async(to: Route, from: Route, next: any) => {
  
// })

// router.afterEach((to: Route) => {
//   // Finish progress bar
//   NProgress.done()

//   // set page title
//   // document.title = getPageTitle(to.meta.title)
// })
