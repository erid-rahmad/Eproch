import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import AlertMixin from "@/shared/alert/alert.mixin";
import { mixins } from "vue-class-component";
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import { Component, Inject, Vue, Watch } from "vue-property-decorator";
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';
import DynamicWindowService from "../../../DynamicWindow/dynamic-window.service";
import settings from '@/settings';

const RegistDetailProp = Vue.extend({
  props: {
    data: Object,
  }
})

@Component({

})
export default class RegistDetailBuyer extends mixins(Vue2Filters.mixin, AccessLevelMixin, AlertMixin, RegistDetailProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  mainForm: any;
  private currentDate = new Date();
  showDialog: boolean = false;

  submit: boolean = false;

  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";

  private registeredVendors: any = [];

  passfail = [{
    value: 'pass',
    label: 'Pass'
  }, {
    value: 'fail',
    label: 'Fail'
  }, ];

  uploadModels=[
    {name: 'SIUP', file: {}},
    {name: 'SPDA', file: {}}
  ]

  created() {
    this.mainForm = this.data;
    this.loadRegisteredVendors();
  }

  loadRegisteredVendors(){
    this.commonService('api/m-prequal-registrations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${this.mainForm.id}`,
          `registrationStatus.equals=R`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.registeredVendors= res.data.map((item)=>{
          item.pass = '';
          return item;
        });
      });
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  minatAction() {
    this.showDialog = true;
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  loadAttachment(row){
    this.commonService('api/m-preq-regist-documents')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `registrationId.equals=${row.id}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        if(res.data.length){
          this.uploadModels[0].file={
            fileName: res.data[0].siupFileName,
            downloadUrl: res.data[0].siupDownloadUrl
          }
          this.uploadModels[1].file={
            fileName: res.data[0].spdaFileName,
            downloadUrl: res.data[0].spdaDownloadUrl
          }
          this.showDialog = true;
        } else this.showDialog = false;
      });
  }

  downloadFile(row){
    window.open(row.file.downloadUrl, '_blank');
  }

  process(){
    let valid = true;
    this.registeredVendors.forEach((item)=>{
      valid = valid && !!item.pass
    })
    if(!valid){
      this.$message.warning("Evaluasikan seluruh vendor sebelum submit.");
    } else {
      this.submit = true;
      let data = [];
      
      this.registeredVendors.forEach((item)=>{
        data.push({
          adOrganizationId: item.adOrganizationId,
          evaluation: item.pass,
          vendorId: item.vendorId,
          prequalificationId: this.mainForm.id
        });
      })

      this.commonService('/api/m-preq-regist-evaluations/submit').create(data).then((res)=>{
        this.$message.success("Registration evaluation processed successfully.");
      }).catch((err)=>{
        this.$message.error("Error while processing registration evaluation.");
      }).finally(()=>{
        this.submit = false;
      })
    }
  }
}