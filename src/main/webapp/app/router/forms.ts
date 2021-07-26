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

  ['eVerification', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/e-verification/e-verification.vue')],
  ['invoiceVerification', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/invoice-verification/invoice-verification.vue')],
  ['verificationDocumentInquiry', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/verification-document-inquiry/verification-document-inquiry.vue')],
  ['paymentStatus', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/payment-status/payment-status.vue')],
  ['productReceiveInfo', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/product-receive-info/product-receive-info.vue')],
  ['eNofa', () => import(/* webpackChunkName: "eVerification" */'../core/application-dictionary/components/Form/e-verification/e-nofa/e-nofa.vue')],

  ['marketplace', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/marketplace/index.vue')],
  ['productCatalog', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/product-catalog/product-catalog.vue')],
  ['shoppingCart', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/marketplace/shopping-cart.vue')],
  ['bhinnekaCatalogImporter', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/marketplace/bhinneka-catalog-importer.vue')],
  ['generatePo', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/generate-po/generate-po.vue')],
  ['generateQuotation', () => import(/* webpackChunkName: "marketplace" */'@/core/application-dictionary/components/Form/generate-rfq/generate-rfq.vue')],

  // Auction
  ['auction', () => import(/* webpackChunkName: "auction" */'@/core/application-dictionary/components/Form/auction/index.vue')],
  ['auctionInvitation', () => import(/* webpackChunkName: "auction" */'@/core/application-dictionary/components/Form/auction/invitation/index.vue')],

  ['bidding', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding/bidding.vue')],
  ['biddingSubmission', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding/submission/registered-bidding-list.vue')],
  ['biddingResult', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding/event/bidding-submission/bidding-submission-grid.vue')],
  ['biddingResultAnnouncement', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-result/announcement.vue')],
  ['biddingResultForm', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-result/components/result.vue')],
  ['biddingEvaluation', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-evaluation/bidding-evaluation-grid.vue')],
  ['biddingEvaluationApproval', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-evaluation/bidding-evaluation-approval.vue')],
  ['costEvaluation', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/cost-evaluation/product-catalog.vue')],
  ['costEvaluationList', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/cost-evaluation/index.vue')],
  ['costEvaluationApproval', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/cost-evaluation/components/detail.vue')],
  ['prequalificationCriteria', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/prequalification-criteria/product-catalog.vue')],
  ['prequalificationMethod', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/prequalification-method/product-catalog.vue')],
  ['biddingEvaluationTeam', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding/evaluation-team/evaluation-team.vue')],
  // ['biddingAgreement', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/bidding-agreement/index.vue')],
  ['biddingEvaluationLanding', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-evaluation/bidding-evaluation-landing.vue')],
  // Bidding events.
  ['biddingAnnouncementForm', () => import(/* webpackChunkName: "biddingEvent" */'@/core/application-dictionary/components/Form/bidding/event/bidding-announcement/bidding-announcement.vue')],
  ['preBidMeeting', () => import(/* webpackChunkName: "biddingEvent" */'@/core/application-dictionary/components/Form/bidding/event/pre-bid-meeting/pre-bid-meeting.vue')],
  ['biddingSubmissionForm', () => import(/* webpackChunkName: "biddingEvent" */'@/core/application-dictionary/components/Form/bidding/event/bidding-submission/bidding-submission.vue')],
  ['biddingAnnouncementDetails', () => import(/* webpackChunkName: "biddingEvent" */'@/core/application-dictionary/components/Form/event-announcement/components/details-announcement-landing.vue')],
  ['evaluationAnnouncementResult', () => import(/* webpackChunkName: "biddingEvent" */'@/core/application-dictionary/components/Form/bidding-evaluation-announcement/evaluation-announcement-landing.vue')],

  ['vendorEvaluation', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/vendor-evaluation/index.vue')],
  ['warningLetter', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/vendor-evaluation/warning-letter.vue')],
  ['complaintList', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/vendor-evaluation/complaint-list.vue')],
  ['vendorBlacklist', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/vendor-evaluation/vendor-blacklist.vue')],
  ['vendorPerformanceReport', () => import(/* webpackChunkName: "vendorManagement" */'@/core/application-dictionary/components/Form/vendor-evaluation/vendor-performance-report.vue')],
  ['vendorConfirmation', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/vendor-confirmation/index.vue')],
  ['biddingNegotiation', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-negotiation/index.vue')],
  ['eventAnnouncement', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/event-announcement/event-announcement.vue')],

  ['biddingRegistration', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/event-announcement/bidding-registration.vue')],

  ['evaluationAnnouncement', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-evaluation-announcement/evaluation-announcement.vue')],
  ['evaluationAnnouncementVendor', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding-evaluation-announcement/evaluation-announcement-vendor.vue')],

  ['preBidMeetingGrid', () => import(/* webpackChunkName: "bidding" */'@/core/application-dictionary/components/Form/bidding/event/pre-bid-meeting/pre-bid-meeting-grid.vue')],

  ['contract', () => import(/* webpackChunkName: "contract" */'@/core/application-dictionary/components/Form/vendor-contract/vendor-contract.vue')],
  ['contractTeam', () => import(/* webpackChunkName: "contract" */'@/core/application-dictionary/components/Form/vendor-contract/team-grid/contract-team-grid.vue')],

  // Prequalification
  ['prequalification', () => import(/* webpackChunkName: "prequalification" */'@/core/application-dictionary/components/Form/prequalification/prequalification.vue')],
  ['prequalificationSchedule', () => import(/* webpackChunkName: "prequalification" */'@/core/application-dictionary/components/Form/prequalification/schedule/index.vue')],
  ['prequalificationAnnouncement', () => import(/* webpackChunkName: "prequalification" */'@/core/application-dictionary/components/Form/prequalification/announcement/preq-announcement-grid.vue')],
  ['prequalificationRegistration', () => import(/* webpackChunkName: "prequalification" */'@/core/application-dictionary/components/Form/prequalification/registration/prequalification-registration.vue')],
  ['prequalificationSubmission', () => import(/* webpackChunkName: "prequalification" */'@/core/application-dictionary/components/Form/prequalification/submission/registered-prequalification-list.vue')],
  
]);

export const blankForm = () => import(/* webpackChunckName: "blankForm" */'@/core/application-dictionary/components/Form/index.vue');
