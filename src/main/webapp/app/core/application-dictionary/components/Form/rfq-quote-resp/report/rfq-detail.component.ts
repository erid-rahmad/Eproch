import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
// import echarts from 'echarts';
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import Chart from 'chart.js';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';

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
export default class RfqDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {
  period: any = '1y'
  joinedSuppliers: any[] = [];
  
  periodSelections=[
    {value: '3m', label: '3 Months'},
    {value: '6m', label: '6 Months'},
    {value: '1y', label: '1 Year'},
    {value: '2y', label: '2 Years'},
  ]
  
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private chart:any[] = [];
  private chartConfig: any= {
    type : "bar",
    data : {
      labels: [""],
      datasets : [{
        label: "Saved Value (Rp. )",
        data: [],
        backgroundColor : '#b0d5ed',
        borderColor : "#FF5370"
      }]
    },
    options:{
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
                return 'Rp. ' + value;
            }
          }
        }
      }
    }
  };
  private chartConfig2: any= {
    type: 'scatter',
    data: {datasets: [{
      label: 'Best Quotes By Time',
      data: [],
      backgroundColor: 'rgb(255, 99, 132)'
    }]},
    options: {
      scales: {
        x: {
          type: 'linear',
          position: 'bottom'
        },
        y: {
          ticks: {
              // Include a dollar sign in the ticks
              callback: function(value, index, values) {
                  return 'Rp. ' + value;
              }
          }
        }
      }
    }
  };

  chartId = "bestQuotesByTime";
  chartId2= "bestSavesBySupplier";
  currentDate = new Date();

  private processing: boolean= false;

  selectedWinner :any = {};
  selectedWinners:any[]=[];

  colors: any = [];
  borderColors: any = [];

  contractParameter = {};

  created(){
    console.log(this.data);

    if (!this.data.dateRequired) {
      this.data.timeRemaining = '-';
    }

    if (this.currentDate >= new Date(this.data.dateRequired)) {
      this.data.timeRemaining = '-';
    }

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(this.data.dateRequired)
    });

    this.data.timeRemaining = formatDuration(duration, {
      format: ['days', 'hours', 'minutes']
    });

    this.refreshContent();
  }

  mounted(){
    this.$nextTick(()=>{
      this.initChart();
    });
  }

  initChart(){
    try{
      let context= document.getElementById(this.chartId);
      this.chart.push(new Chart(context, this.chartConfig));

      context= document.getElementById(this.chartId2);
      this.chart.push(new Chart(context, this.chartConfig2));
    }catch(e){
      console.log(e);
    }
  }

  refreshContent(){
    this.processing= true;
    this.commonService(`/api/m-rfq-submissions`).retrieve({
      criteriaQuery: this.updateCriteria([
        `quotationId.equals=${this.data.quotationId}`,
        'documentStatus.equals=SMT'
      ]),paginationQuery: {
        page: 0,
        size: 1000,
        sort:['submissionGrandTotal,desc']
      }
    })
    .then(
      (res)=>{
        console.log(res.data);
        this.joinedSuppliers = res.data;
        
        let savingsLabel: any[]= [];
        let savingsData: any[]= [];

        let quotesData: any[] = [];

        this.joinedSuppliers.forEach(el => {
          let colorString = '#'+Math.random().toString(16).substr(2,6);
          this.colors.push(colorString+'40');
          this.borderColors.push(colorString);

          savingsLabel.push(el.vendorName);
          savingsData.push(el.submissionGrandTotal - this.data.grandTotal);

          let timeDiff= Math.abs(new Date(el.dateSubmitted).getTime() - new Date(this.data.dateTrx).getTime());
          let dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));
          quotesData.push({x:dayDiff, y:el.submissionGrandTotal});
        });

        if(this.chart[0]){
          console.log("refreshing chart data ");
          this.chartConfig.data.labels= savingsLabel;
          this.chartConfig.data.datasets[0].data= savingsData;
          this.chartConfig.data.datasets[0].backgroundColor= this.colors;
          this.chartConfig.data.datasets[0].borderColor= this.borderColors;
          
          this.chart[0].update();
        }

        if(this.chart[1]){
          console.log("refreshing chart data ");
          this.chartConfig2.data.datasets[0].data = quotesData;
          this.chartConfig2.data.datasets[0].backgroundColor= this.colors;
          
          this.chart[1].update();
        }
        
        this.processing= false;
      })
    .catch(
      (res)=>{
        this.$message.error("Failed to fetch data.");
        console.log(res);
      }
    )
  }

  onCurrentRowChanged(row: any) {
    this.selectedWinner = row;
  }

  onSelectionChanged(value: any) {
    this.selectedWinners = value;
    this.$emit('selectedRows', this.selectedWinners);
  }

  createContract(){
    if(this.selectedWinners.length==0)
      this.$message.warning("Select at least 1 winner.");
    else {
      console.log(this.selectedWinners);
      let quotation:any = {};
      let contractCount = 0;
      Promise.allSettled([
        new Promise<boolean>((resolve, reject)=>{
          this.commonService(`/api/m-rfqs/${this.data.quotationId}`).retrieve().then((res)=>{
            quotation = res.data;
            resolve(true);
          }).catch((err)=>{
            console.log(err);
            reject(false);
          })
        }),
        new Promise<boolean>((resolve, reject)=>{
          this.commonService(`/api/m-contracts/count`).retrieve({
            criteriaQuery: this.updateCriteria([
            `quotationId.equals=${this.data.quotationId}`
            ])
          }).then((res)=>{
            contractCount = res.data;
            resolve(true);
          }).catch((err)=>{
            console.log(err);
            reject(false);
          })
        })
      ]).then((res)=>{
        if(quotation.id){
          if(contractCount==0){
            this.$emit('generatingContract', true)
            this.contractParameter = {
              adOrganizationId: quotation.adOrganizationId,
              quotationId: this.data.quotationId,
              name: this.data.title,
              costCenterId: quotation.costCenterId,
              //picUserId: row.vendorConfirmationPicId,
              //vendorId: row.vendorId,
              startDate: quotation.dateTrx,
              expirationDate: quotation.dateRequired,
              vendorEvaluationId: null,
              evaluationPeriod: null,
              currencyId: quotation.currencyId,
              price: this.data.grandTotal,
              rfqSubmissions: this.joinedSuppliers
            };

            console.log(this.contractParameter);

            this.commonService("/api/m-contracts/generate-from-rfq").create(this.contractParameter)
            .then(res => this.$message.success(`Contract(s) has been generated successfully`))
            .catch(err => {
              console.error('Failed to generate contract', err);
              this.$message.error('Failed to generate contract');
              //this.contractParameterFormVisible = false;
            })
            .finally(() => this.$emit('generatingContract', false));
          } else this.$message.error("Contract already generated");
        } else this.$message.error("Cannot find Quotation");
      })
      
    }
  }
}
