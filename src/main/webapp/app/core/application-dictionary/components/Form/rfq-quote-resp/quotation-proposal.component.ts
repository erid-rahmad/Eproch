import Vue from 'vue'
import settings from '@/settings';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const QuotationProposalProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => { }
    }
  }
})

@Component({
  components: {
  }
})
export default class QuotationProposal extends Mixins(AccessLevelMixin, QuotationProposalProps) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  mainForm: any = {};
  lineData: any[] = [];
  header: any = {};

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records',
    maxHeight: 500,
    height: 500
  };

  loading = false;
  publishDialog = false;

  created() {
    console.log(this.data);
    this.mainForm = this.data;
    this.mainForm.status = new Date() >= new Date(this.mainForm.dateRequired) ? 'Finished': 'In Progress'
    
    this.loading=true;
    this.loadLines();
  }

  get readonly(){
    return this.header.documentStatus==="SMT"
  }

  loadLines(){
    this.commonService("/api/m-rfq-lines").retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `quotationId.equals=${this.mainForm.quotationId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 1000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      res.data.forEach(el=>{
        el.submissionPrice = el.unitPrice;
        el.totalSubmissionPrice = el.orderAmount;
      });
      this.lineData = res.data;
      this.loadSavedProposal();
    }).catch((err)=>{
      console.log(err);
      this.$message.error("Failed to load quotation data.");
      this.loading = false;
    })
  }

  loadSavedProposal(){
    this.commonService("/api/m-rfq-submissions").retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `quotationId.equals=${this.mainForm.quotationId}`,
        `quoteSupplierId.equals=${this.mainForm.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 1,
        sort: ['id']
      }
    }).then((res)=>{
      if(res.data.length){
        this.header = res.data[0];
        if(this.header.documentStatus==="SMT") this.$emit('readOnly',true);
        this.commonService("/api/m-rfq-submission-lines").retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `submissionId.equals=${this.header.id}`
          ]),
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id']
          }
        }).then((res)=>{
          this.header.line = this.lineData;
          this.header.line.forEach((line)=>{
            let subLine = res.data.find(sl=>sl.quotationLineId == line.id);

            line.quotationLineId = subLine.quotationLineId;
            line.id = subLine.id;
            line.dateTrx = subLine.dateTrx;
            line.dateSubmitted = subLine.dateSubmitted
            line.approved = subLine.approved;
            line.processed = subLine.processed;
            line.submissionPrice = subLine.submissionPrice;
            line.totalSubmissionPrice = subLine.totalSubmissionPrice;
          })
          console.log(this.header);
        }).finally(()=>this.loading = false);
      } else {
        this.commonService(`/api/m-rfqs/${this.mainForm.quotationId}`).retrieve().then((res)=>{
          this.header = res.data;

          this.header.quotationId = this.mainForm.quotationId;
          this.header.id = null;
          this.header.submissionGrandTotal = this.header.grandTotal;
          this.header.quoteSupplierId = this.mainForm.id;

          this.header.line = this.lineData;
          this.header.line.forEach((line)=>{
            line.dateTrx = line.documentDate;
            line.quotationLineId = line.id
            line.id = null;
          })
          console.log(this.header);
          this.loading=false;
        })
      }
    }).catch((err)=>{
      console.log(err);
      this.$message.error("Failed to load proposal submisison");
      this.loading = false;
    })
  }

  updateTotal(row){
    console.log(row);
    row.submissionPrice = parseInt(row.submissionPrice);
    if(Object.is(row.submissionPrice,NaN)){
      row.submissionPrice = row.unitPrice;
    }
    row.totalSubmissionPrice = row.releaseQty * row.submissionPrice;
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  save(status: string){
    console.log(this.header);
    let submissionGrandTotal:number = 0;

    this.header.documentStatus = status;
    if(status=='SMT') this.header.dateSubmitted = new Date();

    this.header.line.forEach((line)=>{
      line.documentStatus = status;
      if(status=='SMT') line.dateSubmitted = this.header.dateSubmitted;

      submissionGrandTotal += line.totalSubmissionPrice;
    })
    
    this.header.submissionGrandTotal = submissionGrandTotal;

    this.commonService("/api/m-rfq-submissions")[this.header.id?'update':'create'](this.header).then((res)=>{
      this.$message.success("Saved Quotation Submission");
      if(!this.header.id) {
        this.header.id = res.data.id;
      }
      this.publishDialog = false;
    }).catch(err=>{
      this.$message.success("Failed Saving Quotation Submission");
      console.log(err);
    })
  }

  openPublishDialog(){
    this.publishDialog = true;
  }
}
