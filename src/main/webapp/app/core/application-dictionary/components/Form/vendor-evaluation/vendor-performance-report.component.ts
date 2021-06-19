import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Component, { mixins } from 'vue-class-component';
import VendorAnalis from './vendor-analis.vue';

@Component({
  components: {
    VendorAnalis
  }
})
export default class VendorPerformanceReport extends mixins(AccessLevelMixin) {

  viewDetail=false;

  columnSpacing = 24;
  selectedRow: any = {};

  filter = {
    categoryId: null,
    subCategoryId: null,
    vendorId: null
  }

  performanceReports = [
    {
      vendorId: 1,
      vendorName: 'SISTECH KHARISMA',
      businessCategory: 'Automotive & Vehicle',
      subCategory: 'Engine Parts',
      evaluationScore: '75%',
      warningLetterScore: '0%',
      rating: '75%',
    },
    {
      vendorId: 2,
      vendorName: 'INGRAM MICRO INDONESIA',
      businessCategory: 'Automotive & Vehicle',
      subCategory: 'Car',
      evaluationScore: '65%',
      warningLetterScore: '0%',
      rating: '65%',
    },
    {
      vendorId: 3,
      vendorName: 'WESTCON INTERNATIONAL INDONESIA',
      businessCategory: 'Automotive & Vehicle',
      subCategory: 'Motorbike',
      evaluationScore: '65%',
      warningLetterScore: '0%',
      rating: '65%',
    }
  ];

  categories = [
    {
      id: 1,
      name: 'Automotive & Vehicle',
    }
  ];

  subCategories = [
    {
      id: 1,
      name: 'Car',
    },
    {
      id: 2,
      name: 'Motorbike',
    },
    {
      id: 3,
      name: 'Engine Parts',
    }
  ];

  vendors = [
    {
      id: 1,
      name: 'SISTECH KHARISMA',
    },
    {
      id: 2,
      name: 'INGRAM MICRO INDONESIA',
    },
    {
      id: 3,
      name: 'WESTCON INTERNATIONAL INDONESIA',
    }
  ];

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  mounted() {
    this.setRow(this.performanceReports[0]);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }
}
