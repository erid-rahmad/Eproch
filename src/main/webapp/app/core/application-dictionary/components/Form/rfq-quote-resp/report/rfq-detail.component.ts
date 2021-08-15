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

  private chart:any;
  private chartConfig: any= {
    type : "bar",
    data : {
      labels: [""],
      datasets : [{
        label: "",
        data: [],
        backgroundColor : '#b0d5ed',
        borderColor : "#FF5370"
      }]
    }
  };

  chartId= "performanceChart";
  currentDate = new Date();

  private processing: boolean= false;

  selectedWinner :any = {};
  selectedWinners:any[]=[];

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
    /*
    this.$nextTick(()=>{
      this.initChart();
    });
    */
  }

  initChart(){
    try{
      const context= document.getElementById(this.chartId);
      console.log("context : " , context);
      this.chart= new Chart(context, this.chartConfig);
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
        /*
        this.analis = res.data;
        let contractPerformanceLabel: any[]= [];
        let contractPerformanceData: any[]= [];

        this.analis.performanceProjectAnalysis.forEach(el => {
          contractPerformanceLabel.push(el.documentNo);
          contractPerformanceData.push(el.totalScore);
        });

        if(this.chart){

          console.log("refreshing chart data ");
          this.chartConfig.data.labels= contractPerformanceLabel;
          this.chartConfig.data.datasets[0].data= contractPerformanceData;
          
          this.chart.update();
        }
        */
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
