import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const TaxInformationUpdateProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },

  }
})

@Component
export default class TaxInformationUpdate extends TaxInformationUpdateProps {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  loading = false;
  columnSpacing = 32;
  editDialogVisible = false;
  public taxes: any[] = [];
  public selectedTaxes: any[] = [];

  created() {
    this.retrieveTax();
  }

  public onSelectionChanged(rows: any[]) {
    this.selectedTaxes = rows;
    console.log('Selected taxes: %O', rows);
  }

  private retrieveTax() {
    this.dynamicWindowService('/api/c-tax-categories')
      .retrieve({
        criteriaQuery: 'active.equals=true',
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.taxes = res.data;
      });
  }
}
