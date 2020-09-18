export const forms: Map<string, () => Promise<typeof import('*.vue')>> = new Map([
  ['accountActivation', () => import(/* webpackChunkName: "account" */ '@/account/activate/activate.vue')],
  ['accountResetPasswordInit', () => import(/* webpackChunkName: "account" */ '@/account/reset-password/init/reset-password-init.vue')],
  ['accountResetPasswordFinish', () => import(/* webpackChunkName: "account" */ '@/account/reset-password/finish/reset-password-finish.vue')],
  ['accountChangePassword', () => import(/* webpackChunkName: "account" */ '@/account/change-password/change-password.vue')],
  ['accountSettings', () => import(/* webpackChunkName: "account" */ '@/account/settings/settings.vue')],

  ['adminConfiguration', () => import(/* webpackChunkName: "admin" */'../admin/configuration/configuration.vue')],
  ['userList', () => import(/* webpackChunkName: "admin" */'../admin/user-management/user-management.vue')],
  ['userDetail', () => import(/* webpackChunkName: "admin" */'../admin/user-management/user-management-view.vue')],
  ['userEdit', () => import(/* webpackChunkName: "admin" */'../admin/user-management/user-management-edit.vue')],

  ['systemAPI', () => import(/* webpackChunkName: "system" */'../admin/docs/docs.vue')],
  ['systemHealth', () => import(/* webpackChunkName: "system" */'../admin/health/health.vue')],
  ['systemLogs', () => import(/* webpackChunkName: "system" */'../admin/logs/logs.vue')],
  ['systemAudit', () => import(/* webpackChunkName: "system" */'../admin/audits/audits.vue')],
  ['systemMetric', () => import(/* webpackChunkName: "system" */'../admin/metrics/metrics.vue')],
  ['systemTracker', () => import(/* webpackChunkName: "system" */'../admin/tracker/tracker.vue')]
]);

export const blankForm = () => import(/* webpackChunckName: "blankForm" */'@/core/application-dictionary/components/Form/index.vue');
