import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const VendorEvaluationDetailProp = Vue.extend({
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
export default class VendorEvaluationDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {

  analis: any =
    {
      awarded: "70.287.000.000",
      awarded1: "-16.577.000.000",
      awarded2: "14",
      awarded3: "60 %",
      awarded4: "2",

    }
    analis1: any =
    {
      awarded: "55.560.000.000",
      awarded1: "70.578.000.000",
      awarded2: "43.560.000.000",
      awarded3: "55.560.000.000",
      awarded4: "78 %",

    }

  gridData = [
    {
      Name: "Pengadaan Kendaraan Operasional",
      Name1: "05/01/2021",
      Name2: "05/03/2021",
      Name3: "11.500.000.000",
    }, {
      Name: "Service Repair Otomotif",
      Name1: "05/03/2021",
      Name2: "05/03/2022",
      Name3: "15.220.000.000",
    },
  ]

  gridData1 = [
    {
      Name: "Pengadaan Sparepart",
      Name1: "Admin A",
      Name2: "01/12/2020",
      Name3: "57 %",
      Name4: "1",
    }, {
      Name: "Perbaikan Mesin",
      Name1: "Admin B",
      Name2: "03/10/2020",
      Name3: "60 %",
      Name4: "1",
    },
  ]
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

}
