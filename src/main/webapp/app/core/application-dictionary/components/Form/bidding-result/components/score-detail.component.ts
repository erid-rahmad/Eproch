import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Component, { mixins } from 'vue-class-component';

@Component
export default class ScoreDetail extends mixins(AccessLevelMixin) {

  columnSpacing = 24;


  mainForm = {
    vendorName: 'SISTECH KHARISMA',
    totalScore: 14
  };

  tableData = [
    {
      criteria: 'Price',
      subCriteria: 'Item Price',
      percentage: '30%',
      score: '5',
    },
    {
      criteria: 'Delivery',
      subCriteria: 'Delivery Time',
      percentage: '30%',
      score: '3',
    },
    {
      criteria: 'On Required Demand',
      subCriteria: 'Based on Contract',
      percentage: '20%',
      score: '4',
    },
    {
      criteria: 'Quality',
      subCriteria: 'Product Quality',
      percentage: '20%',
      score: '5',
    },
  ];
}
