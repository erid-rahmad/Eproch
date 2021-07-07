
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import echarts from 'echarts';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
const baseApiDashbord ='api/m-purchase-orders/dashbordView';

@Component
export default class DashBoard extends  Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  contenBar:any=[[]]

  private chart:any;


  async mounted() {
    await this.retrieveVendorBidding();
    this.chart = echarts.init(document.getElementById("echarts"));

  }


  retrieveVendorBidding(){
    this.commonService(baseApiDashbord)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        const product = res.data;
        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.orderAmount;
          const bandB = b.orderAmount;
          let comparison = 0;
          if (bandA > bandB) {
            comparison = 1;
          } else if (bandA < bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await product.sort(compare);

        await product.forEach(data=>{
          this.contenBar.push([data.orderAmount,data.productName])
        })

        await this.chart.setOption({
          dataset: {
            source: this.contenBar
          },
          grid: {containLabel: true},
          xAxis: {name: 'amount'},
          yAxis: {type: 'category'},
          series: [
            {
              type: 'bar',
              encode: {
                // Map the "amount" column to X axis.
                x: 'amount',
                // Map the "product" column to Y axis
                y: 'product'
              }
            }
          ]
        })
      })
      .finally();
  }


}
