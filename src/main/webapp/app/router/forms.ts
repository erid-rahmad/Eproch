export const forms: Map<string, () => Promise<typeof import('*.vue')>> = new Map([
  ['accountActivation', () => import(/* webpackChunkName: "account" */ '@/account/activate/activate.vue')],
  ['accountResetPasswordInit', () => import(/* webpackChunkName: "account" */ '@/account/reset-password/init/reset-password-init.vue')],
  ['accountResetPasswordFinish', () => import(/* webpackChunkName: "account" */ '@/account/reset-password/finish/reset-password-finish.vue')],
  ['accountChangePassword', () => import(/* webpackChunkName: "account" */ '@/account/change-password/change-password.vue')],
  ['accountSettings', () => import(/* webpackChunkName: "account" */ '@/account/settings/settings.vue')],

  ['adminConfiguration', () => import(/* webpackChunkName: "admin" */'@/admin/configuration/configuration.vue')],
  ['userList', () => import(/* webpackChunkName: "admin" */'@/admin/user-management/user-management.vue')],
  ['userDetail', () => import(/* webpackChunkName: "admin" */'@/admin/user-management/user-management-view.vue')],
  ['userEdit', () => import(/* webpackChunkName: "admin" */'@/admin/user-management/user-management-edit.vue')],

  ['systemAPI', () => import(/* webpackChunkName: "system" */'@/admin/docs/docs.vue')],
  ['systemHealth', () => import(/* webpackChunkName: "system" */'@/admin/health/health.vue')],
  ['systemLogs', () => import(/* webpackChunkName: "system" */'@/admin/logs/logs.vue')],
  ['systemAudit', () => import(/* webpackChunkName: "system" */'@/admin/audits/audits.vue')],
  ['systemMetric', () => import(/* webpackChunkName: "system" */'@/admin/metrics/metrics.vue')],
  ['systemTracker', () => import(/* webpackChunkName: "system" */'@/admin/tracker/tracker.vue')],

  ['eVerification', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/e-verification/e-verification.vue')],
  ['invoiceVerification', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/invoice-verification/invoice-verification.vue')],
  ['verificationDocumentInquiry', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/verification-document-inquiry/verification-document-inquiry.vue')],
  ['paymentStatus', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/payment-status/payment-status.vue')],
  ['productReceiveInfo', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/product-receive-info/product-receive-info.vue')],
  ['eNofa', () => import(/* webpackChunkName: "invoiceVerification" */'@/core/application-dictionary/components/Form/e-verification/e-nofa/e-nofa.vue')],

  ['marketplace', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/marketplace/product-catalog-list.vue')],
  ['bhinnekaCatalogImporter', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/marketplace/bhinneka-catalog-importer.vue')]
]);

export const blankForm = () => import(/* webpackChunckName: "blankForm" */'@/core/application-dictionary/components/Form/index.vue');
