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

  analis: any = {}

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

  created(){
    console.log(this.data);
    this.commonService(`/api/m-vendor-performance-report/detail`).retrieve({
      criteriaQuery: this.updateCriteria([
        `vendorId=${this.data.vendorId}`
      ]),
    })
    .then(
      (res)=>{
        console.log(res.data);
        this.analis = res.data;
      })
    .catch(
      (res)=>{
        this.$message.error("Failed to fetch data.");
        console.log(res);
      }
    )
  }
}
