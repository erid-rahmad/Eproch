import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const ComplaintDetailProp = Vue.extend({
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
export default class ComplaintDetail extends Mixins(AccessLevelMixin, ComplaintDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 24;
  mainForm = {
    documentAction: null,
    documentStatus: null
  };

  costCenters = [
    {
      id: 1,
      name: 'Procurement',
      value: 1
    },
    {
      id: 2,
      name: 'Marketing',
      value: 2
    },
    {
      id: 3,
      name: 'Sales',
      value: 3
    }
  ];

  vendors = [
    {
      id: 1,
      name: 'INGRAM MICRO INDONESIA',
      value: 1
    },
    {
      id: 2,
      name: 'WESTCON INTERNATIONAL INDONESIA',
      value: 2
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get readOnly() {
    return this.mainForm.documentStatus === 'RVW';
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.mainForm = data;
  }

  created() {
    this.onDataChanged(this.data);
  }
}