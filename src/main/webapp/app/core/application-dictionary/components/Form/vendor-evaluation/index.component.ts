import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const VendorEvaluationProp = Vue.extend({
  props: {
  }
});

@Component
export default class VendorEvaluation extends Mixins(AccessLevelMixin, VendorEvaluationProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  // Mockup Aggrement data.
  aggreements = new Map([
    ['AG-20210330', {
      documentNo: 'AG-20210330',
      evaluationType: 'Evaluation Type 103',
      evaluationPeriod: 'Quarterly',
      evaluationDate: new Date()
    }],
    ['AG-20210329', {
      documentNo: 'AG-20210329',
      evaluationType: 'Evaluation Type 102',
      evaluationPeriod: 'Yearly',
      evaluationDate: new Date()
    }],
    ['AG-20210328', {
      documentNo: 'AG-20210328',
      evaluationType: 'Evaluation Type 101',
      evaluationPeriod: 'End of Aggreement',
      evaluationDate: new Date()
    }]
  ]);

  data = {
    documentNo: null,
    reviewer: null,
    vendorId: null,
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
      this.$set(this.data, 'evaluationType', null);
      this.$set(this.data, 'evaluationPeriod', null);
      this.$set(this.data, 'evaluationDate', null);
    }

    const aggreement = this.aggreements.get(documentNo);
    if (aggreement) {
      this.$set(this.data, 'evaluationType', aggreement.evaluationType);
      this.$set(this.data, 'evaluationPeriod', aggreement.evaluationPeriod);
      this.$set(this.data, 'evaluationDate', aggreement.evaluationDate);

      this.retrieveLines(aggreement.evaluationType);
    }
  }

  onRateChanged(_rate: number) {
    const totalRates = this.data.evaluationLines
      .map((line: any): number => line.rate || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.data, 'totalScore', totalRates / this.data.evaluationLines.length);
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
        this.$set(this.data, 'evaluationLines', res.data);
      })
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