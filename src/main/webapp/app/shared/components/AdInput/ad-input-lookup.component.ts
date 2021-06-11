import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { kebabCase } from 'lodash';
import pluralize from 'pluralize';
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';

const baseApiColumn: string = 'api/ad-columns';

const AdInputLookupProps = Vue.extend({
  props: {
    classNames: Array,
    disabled: Boolean,
    items: Array,
    labelFields: Array,
    placeholder: String,
    query: Array,
    size: String,
    lookupByField: String,
    tableName: String,
    value: [String, Number],
  },
});

@Component
export default class AdInputLookup extends Mixins(AccessLevelMixin, AdInputLookupProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  key: number | string = null;
  lookupItems: any[] = [];
  identifiers: string[] = ['id'];

  apiEndpoint: string = null;

  @Watch('value')
  onValueChanged(value?: number) {
    if (this.key !== value) {
      this.key = value;
    }
  }

  onChanged(value?: number) {
    this.$emit('input', value);
    this.$emit('change', value);
  }

  async created() {
    const resourceName = pluralize.plural(kebabCase(this.tableName));

    if (this.labelFields) {
      this.identifiers = this.labelFields as string[];
    }

    this.apiEndpoint = `api/${resourceName}`;
    this.key = this.value;
    await this.retrieveIdentifiers();
    this.fetchRecords(this.key);
  }

  /**
   * This method is invoked each time user typing in el-select component
   * with remote attribute enabled.
   * @param value
   */
  fetchRecords(value?: string | number) {
    this.loading = true;
    const isUserTable = this.tableName === 'ad_user';
    let baseQuery: string[] = ['active.equals=true'];

    if (this.query) {
      baseQuery = [
        ...baseQuery,
        ...this.query as string[]
      ];
    }

    if (!!value) {
      if (typeof value === 'number') {
        baseQuery.push(`id.equals=${value}`);
      } else if (isUserTable) {
        baseQuery.push(`userLogin.contains=${value}`);
      } else {
        const fieldName = this.lookupByField || this.identifiers[0];
        baseQuery.push(`${fieldName}.contains=${value}`);
      }
    }

    this.commonService(this.apiEndpoint)
      .retrieve({
        criteriaQuery: this.updateCriteria(baseQuery),
        paginationQuery: {
          page: 0,
          size: 20,
          sort: isUserTable ? ['id'] : this.identifiers,
        },
      })
      .then(res => {
        this.lookupItems = res.data;
        this.$emit('update:items', this.lookupItems);
      })
      .finally(() => (this.loading = false));
  }

  private retrieveIdentifiers() {
    return this.commonService(baseApiColumn)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'identifier.equals=true',
          `adTableName.equals=${this.tableName}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['selectionSequence', 'id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.identifiers = res.data.map(item => item.name);
        }
      });
  }

  printLabel(data: any) {
    return this.identifiers
      .filter(key => data.hasOwnProperty(key))
      .map(key => data[key])
      .join(' - ');
  }
}
