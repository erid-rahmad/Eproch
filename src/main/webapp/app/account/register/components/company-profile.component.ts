import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Inject } from 'vue-property-decorator';
import { ElInput } from 'element-ui/types/input';
import { ElUploadInternalFileDetail, ElUpload, ElUploadInternalRawFile } from 'element-ui/types/upload';

const CompanyProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    company: {
      type: Object,
      default: () => {
        return null;
      }
    },
    
  }
})

@Component
export default class CompanyProfile extends CompanyProps {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  public countryOptions: any = {};
  public regionOptions: any = {};
  public cityOptions: any = {};

  public countryOptionsNpwp: any = {};
  public regionOptionsNpwp: any = {};
  public cityOptionsNpwp: any = {};

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf, .doc, .docx";

  private columnSpacing = 32;
  private rules = {
    website: {
      type: 'url'
    },
    /*file: [
      {
        required: true,
        message: 'File required!'
      }
    ]*/
  }

  private typeOptions = [
    {
      value: 'Company',
      key: 'COMPANY'
    }, 
    {
      value: 'Professional',
      key: 'PROFESSIONAL'
    }
  ]

  created() {
    this.retrieveCountry(1);
    this.retrieveCountry(2);
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate)
  }

  public handleTypeChange(value: string) {
    registrationStore.setVendorType(value);
  }

  validate(formIndex: number) {
    if (formIndex === 1) {
      (this.$refs.company as ElForm).validate((passed, errors) => {

        this.company.countryName = this.printValueByParam(this.company.country);
        this.company.regionName = this.printValueByParam(this.company.region);
        this.company.cityName = this.printValueByParam(this.company.city);
        this.company.countryId = this.printKeyByParam(this.company.country);
        this.company.regionId = this.printKeyByParam(this.company.region);
        this.company.cityId = this.printKeyByParam(this.company.city);

        this.company.npwpCountryName = this.printValueByParam(this.company.npwpCountry);
        this.company.npwpRegionName = this.printValueByParam(this.company.npwpRegion);
        this.company.npwpCityName = this.printValueByParam(this.company.npwpCity);
        this.company.npwpCountryId = this.printKeyByParam(this.company.npwpCountry);
        this.company.npwpRegionId = this.printKeyByParam(this.company.npwpRegion);
        this.company.npwpCityId = this.printKeyByParam(this.company.npwpCity);

        this.eventBus.$emit('step-validated', { passed, errors })
      })
    }
  }

  private printValueByParam(row: any){
    if(row){
      let value, key;
      key = parseInt(row.substring( 0, row.indexOf('_')));
      value = row.substring(row.indexOf('_') + 1, row.length);
      return value;
    }
  }
  
  private printKeyByParam(row: any){
    if(row){
      let value, key;
      key = parseInt(row.substring( 0, row.indexOf('_')));
      return key;
    }
  }

  private retrieveCountry(i) {
    this.dynamicWindowService('/api/c-countries')
    .retrieve()
    .then(res => {
        let country = res.data.map(item => {
            return{
                key: item.id,
                value: item.name
            };
        });

        if(i===1){
          this.countryOptions = country;
        }else{
          this.countryOptionsNpwp = country;
        }        
    });
  }

  private retrieveRegion(value, i) {
    let countryId = this.printKeyByParam(value);
    if(i===1){
      this.company.region = "";
      this.company.city = "";
    }else{
      this.company.npwpRegion = "";
      this.company.npwpCity = "";
    }
    
    this.dynamicWindowService('/api/c-regions')
    .retrieve({
        criteriaQuery: [`countryId.equals=${countryId}`]
    })
    .then(res => {
        let region = res.data.map(item => {
            return{
              key: item.id,
              value: item.name
            };
        });

        if(i===1){
          this.regionOptions = region;
        }else{
          this.regionOptionsNpwp = region;
        }
    });
  }
  
  private retrieveCity(value, i) {
    let regionId = this.printKeyByParam(value);
    if(i===1){
      this.company.city = "";
    }else{
      this.company.npwpCity = "";
    }
    this.dynamicWindowService('/api/c-cities')
    .retrieve({
        criteriaQuery: [`regionId.equals=${regionId}`]
    })
    .then(res => {
        let city = res.data.map(item => {
            return{
              key: item.id,
              value: item.name
            };
        });

        if(i===1){
          this.cityOptions = city;
        }else{
          this.cityOptionsNpwp = city;
        }
    });
  }

  get fileList() {
    if ( ! this.company.file) return [];
    return [this.company.file];
  }

  onUploadChange(file: any) {
    this.company.file = file;
  }

  handleRemove(files, fileList) {
    this.company.file = "";
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response: any) {
      console.log('File uploaded successfully ', response);
      this.company.fileId = response.attachment.id;
      //(this.$refs.upload as ElForm).validateField(this.company.file);
      /*(this.$refs.company as ElForm).validate((valid) => {
        console.log(valid);
        if (valid) { 
            console.log("submit");
        } else {
            console.log("error submit!!");
            return false;
        }
      });*/
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 files are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      this.$notify({
          title: 'Warning',
          message: "files with a size less than 5Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt5M;
    }

    // File type restriction
    const name = file.name ? file.name : '';
    const ext = name
      ? name.substr(name.lastIndexOf('.') + 1, name.length)
      : true;
    const isExt = this.accept.indexOf(ext) < 0;
    if (isExt) {
      this.$notify({
        title: 'Warning',
        message: "Please upload the correct format type",
        type: 'warning',
        duration: 3000
      });
      return !isExt;
    }
    
  }

}
