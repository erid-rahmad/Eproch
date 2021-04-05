import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const VendorEvaluationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class VendorEvaluationDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  // Mockup Aggrement data.
  aggreements = new Map([
    ['13334', {
      documentNo: '13334',
      evaluationType: 'Vendor Otomotif',
      evaluationPeriod: 'Yearly',
      evaluationDate: new Date('2021-03-31')
    }]
  ]);

  mainForm = {
    documentNo: '11011',
    reviewer: 'Admin Evaluator',
    vendorId: null,
    vendorName: 'Ingram Micro Indonesia',
    aggreementNo: null,
    evaluationType: null,
    evaluationPeriod: null,
    evaluationDate: null,
    totalScore: null,
    evaluationLines: []
  }

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 256,
    height: 200
  };

  validationSchema = {};
  reviewers = [];
  vendorOptions = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  onAggreementChanged(documentNo?: string) {
    if (!documentNo) {
      this.$set(this.mainForm, 'evaluationType', null);
      this.$set(this.mainForm, 'evaluationPeriod', null);
      this.$set(this.mainForm, 'evaluationDate', null);
    }

    const aggreement = this.aggreements.get(documentNo);
    if (aggreement) {
      this.$set(this.mainForm, 'evaluationType', aggreement.evaluationType);
      this.$set(this.mainForm, 'evaluationPeriod', aggreement.evaluationPeriod);
      this.$set(this.mainForm, 'evaluationDate', aggreement.evaluationDate);

      this.retrieveLines(aggreement.evaluationType);
    }
  }

  onRateChanged(_rate: number) {
    const totalRates = this.mainForm.evaluationLines
      .map((line: any): number => line.rate || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.mainForm, 'totalScore', totalRates / this.mainForm.evaluationLines.length);
  }

  created() {
    this.retrieveReviewers();
    this.retrieveVendors();
  }

  retrieveLines(evaluationType: string) {
    this.commonService('/api/c-vendor-evaluation-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `cVendorEvaluationName.equals=${evaluationType}`
        ])
      })
      .then(res => {
        this.$set(this.mainForm, 'evaluationLines', res.data);
      });
  }

  retrieveReviewers() {
    this.commonService('/api/ad-users')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'employee.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['userLogin']
        }
      })
      .then(res => {
        this.reviewers = res.data;
      });
  }

  retrieveVendors() {
    this.commonService('/api/c-vendors')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'processed.equals=true',
          'approved.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name', 'code']
        }
      })
      .then(res => {
        this.vendorOptions = res.data;
      });
  }
}