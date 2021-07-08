
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
// @ts-ignore
import echarts from 'echarts';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
const baseApiDashbord = 'api/pa-dashboards/SpendByCostCenter';

@Component
export default class DashBoard extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;


  private chart:any;
  private data:any;

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
        await this.chart.setOption({
          title: {
            text: ' '
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: res.data.Header
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: res.data.Header.reverse()
          },
          yAxis: {
            type: 'value'
          },
          series:  res.data.Data
        });




      })
      .finally();
  }

  mounted() {
    this.retrieveVendorBidding();
    this.chart = echarts.init(document.getElementById("echarts1"));

  }


}
