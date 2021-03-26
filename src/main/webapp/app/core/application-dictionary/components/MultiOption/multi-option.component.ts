import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";
import AccessLevelMixin from '../../mixins/AccessLevelMixin';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { IADField } from '@/shared/model/ad-field.model';

const MultiOptionProps = Vue.extend({
  props: {
    checkboxGroup: Boolean,
    field: Object,
    placeholder: String,
    size: String,
    value: String
  }
})

@Component
export default class MultiOption extends Mixins(AccessLevelMixin, MultiOptionProps) {

  @Inject('dynamicWindowService')
  private service: (baseApiUrl: string) => DynamicWindowService;

  selectedOptions = [];
  options: any[] = [];

  @Watch('value')
  onValueChanged(value: string) {
    this.selectedOptions = value?.split(',') || [];
  }

  onOptionChanged(values: string[]) {
    const value = this.selectedOptions.join(',');
    this.$emit('input', value);
    this.$emit('change', value);
  }

  created() {
    const field = (this.field as IADField);
    const referenceId = field.adReferenceId || field.adColumn?.adReferenceId;

    this.onValueChanged(this.value);

    if (referenceId) {
      this.retrieveOptions(referenceId);
    }
  }

  private retrieveOptions(referenceId: number) {
    this.service('/api/ad-reference-lists')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `adReferenceId.equals=${referenceId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.options = res.data;
      })
  }
}