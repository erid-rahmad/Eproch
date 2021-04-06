import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Component, { mixins } from 'vue-class-component';

@Component
export default class ScoreDetail extends mixins(AccessLevelMixin) {

  columnSpacing = 24;


  mainForm = {
    vendorName: 'SISTECH KHARISMA',
    totalScore: 17
  };

  tableData = [
    {
      criteria: 'Price',
      subCriteria: 'Item Price',
      percentage: '30%',
      totalScore: '5',
    },
    {
      criteria: 'Delivery',
      subCriteria: 'Delivery Time',
      percentage: '30%',
      totalScore: '3',
    },
    {
      criteria: 'On Required Demand',
      subCriteria: 'Based on Contract',
      percentage: '20%',
      totalScore: '4',
    },
    {
      criteria: 'Quality',
      subCriteria: 'Product Quality',
      percentage: '20%',
      totalScore: '5',
    },
  ];
}
