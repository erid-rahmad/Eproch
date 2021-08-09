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
import axios from "axios";

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

  private readOnly = false;

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
        let regis = res.data.map((item)=>{
          item.pass = '';
          return item;
        });
        this.commonService('api/m-preq-regist-evaluations').retrieve({
          criteriaQuery: this.updateCriteria([
            `prequalificationId.equals=${this.mainForm.id}`
          ]),
          paginationQuery: {
            page: 0,
            size: 100,
            sort: ['id']
          }
        })
        .then(res => {
          if(res.data.length){
            this.readOnly=true;
            res.data.forEach(element => {
              regis.forEach(e2=>{
                if(e2.vendorId == element.vendorId){
                  e2.pass = element.evaluation
                }
              })
            });

            this.registeredVendors = regis;
          }
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
    axios.get(row.file.downloadUrl,{
      responseType: 'arraybuffer'
    }).then((res)=>{
      let filename = (<string>res.headers['content-disposition']).substring(
        (<string>res.headers['content-disposition']).indexOf("\"")+1,(<string>res.headers['content-disposition']).lastIndexOf("\""))

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', filename); //or any other extension
      document.body.appendChild(link);
      link.click();
    })
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