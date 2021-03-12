import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { PermissionStoreModule as permissionStore } from '@/shared/config/store/permission-store';
import { Message } from 'element-ui';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import VueI18n from 'vue-i18n';
import VueRouter, { Route } from 'vue-router';
import AccountService from './account/account.service';
// import i18n from '@/lang' // Internationalization
import settings from './settings';

NProgress.configure({ showSpinner: false });

const whiteList = [
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

  constructor(
    private router: VueRouter,
    private i18n: VueI18n,
    private accountService: AccountService
  ) {}

  /**
   * runBeforeEachHook
   */
  public async runBeforeEachHook(to: Route, _from: Route, next: any) {
    // Start progress bar
    NProgress.start();

    // Determine whether the user has logged in
    if (accountStore.authenticated) {
      if (to.path === '/login') {
        // If is logged in, redirect to the home page
        next({ path: '/' });
      } else {
        // Check whether the user has obtained his permission roles
        if (accountStore.authorities.size === 0) {
          try {
            // Note: roles must be a object array! such as: ['admin'] or ['developer', 'editor']
            const roles = accountStore.authorities;
            // Generate accessible routes map based on role
            await permissionStore.generateRoutes(roles);
            // Dynamically add accessible routes
            this.router.addRoutes(permissionStore.dynamicRoutes);
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
          }
        } else {
          next();
        }
      }
    } else {
      // Has no token
      if (whiteList.includes(to.path)) {
        // In the free login whitelist, go directly
        next();
      } else {
        // Other pages that do not have permission to access are redirected to the forbidden page.
        sessionStorage.setItem('requested-url', to.fullPath);
        next(`/login`);
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
