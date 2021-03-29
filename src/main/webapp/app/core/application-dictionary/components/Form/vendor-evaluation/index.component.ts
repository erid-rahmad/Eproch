import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue } from 'vue-property-decorator';

const VendorEvaluationProp = Vue.extend({
  props: {
  }
});

@Component
export default class VendorEvaluation extends Mixins(AccessLevelMixin, VendorEvaluationProp) {

  data = {
    documentNo: null,
    type: null,
    period: null,
    date: null,
    reviewer: null,
    vendorName: null,
    aggreementNo: null,
    totalScore: null,
    evaluationLines: [
      {
        question: null,
        questionCategory: null,
        rate: 0,
        remark: null
      }
    ]
  }

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 256,
    height: 200
  };

  validationSchema = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

}