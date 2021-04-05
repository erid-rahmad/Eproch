import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const WarningLetterDetailProp = Vue.extend({
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
export default class WarningLetterDetail extends Mixins(AccessLevelMixin, WarningLetterDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 24;
  mainForm = {
    documentAction: null,
    documentStatus: null
  };

  warningTypes = [
    {
      id: 1,
      name: 'Admonition',
      value: 'A'
    },
    {
      id: 2,
      name: 'Admonition 1',
      value: 'A1'
    },
    {
      id: 3,
      name: 'Admonition 2',
      value: 'A2'
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get readOnly() {
    return this.mainForm.documentStatus === 'CLS';
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.mainForm = data;
  }

  created() {
    this.onDataChanged(this.data);
  }
}