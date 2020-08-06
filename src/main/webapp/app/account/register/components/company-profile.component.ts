import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Inject } from 'vue-property-decorator';

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

  private columnSpacing = 32;
  private rules = {
    website: {
      type: 'url'
    }
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
        this.eventBus.$emit('step-validated', { passed, errors })
      })
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
    if(i===1){
      this.company.region = "";
      this.company.city = "";
    }else{
      this.company.npwpRegion = "";
      this.company.npwpCity = "";
    }
    
    this.dynamicWindowService('/api/c-regions')
    .retrieve({
        criteriaQuery: [`countryId.equals=${value}`]
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
    if(i===1){
      this.company.city = "";
    }else{
      this.company.npwpCity = "";
    }
    this.dynamicWindowService('/api/c-cities')
    .retrieve({
        criteriaQuery: [`regionId.equals=${value}`]
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

  getFile(file, fileList) {
    this.company.file = file;
  }

  handleRemove(files, fileList) {
    this.company.file = "";
    //console.log("remove");
  }

  handleExceed(files, fileList) {
    this.$notify({
        title: 'Warning',
        message: "The limit file is 1",
        type: 'warning',
        duration: 3000
    });
  }
}
