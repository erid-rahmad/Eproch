import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';

const baseApiReferenceList = 'api/ad-reference-lists';

const AdInputListProps = Vue.extend({
  props: {
    classNames: [Array, Object],
    disabled: Boolean,
    placeholder: String,
    query: {
      type: Array,
      default: () => {
        return [];
      }
    },
    referenceKey: String,
    size: String,
    value: String,
  },
});

@Component
export default class AdInputList extends Mixins(AccessLevelMixin, AdInputListProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  reference: string = null;
  referenceList: any[] = [];

  @Watch('value')
  onValueChanged(value?: string) {
    this.reference = value;
  }

  onChanged(value?: number) {
    this.$emit('input', value);
    this.$emit('change', value);
  }

  created() {
    this.reference = this.value;
    this.initRelationships();
  }

  private async initRelationships() {
    this.loading = true;
    
    this.commonService(baseApiReferenceList)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `adReferenceValue.equals=${this.referenceKey}`,
          ...this.query
        ]),
      })
      .then(res => this.referenceList = res.data)
      .catch(err => console.warn('Failed initializing field relationships.', err))
      .finally(() => (this.loading = false));
  }
}