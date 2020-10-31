import Vue from 'vue'
import Component from 'vue-class-component'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service'
import { Inject } from 'vue-property-decorator'
import { buildCascaderOptions } from '@/utils/form'

const SummaryRegistrationProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    activeNames: {
      type: Array,
      default: () => {
        return ['1'];
      }
    },
    login: {
      type: Object,
      default: () => {}
    },
    company: {
      type: Object,
      default: () => {
        return null;
      }
    },
    businessCategory: {
      type: Object,
      default: () => {
        return null;
      }
    },
    mainDocuments: {
      type: Array,
      default: () => []
    },
    additionalDocuments: {
      type: Array,
      default: () => []
    },
    contacts: {
      type: Array,
      default: () => []
    },
    functionaries: {
      type: Array,
      default: () => []
    },
    payments: {
      type: Array,
      default: () => []
    }
  }
})

@Component
export default class SummaryRegistration extends SummaryRegistrationProps {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 32;
  public businessCategoryValues = [];
  public businessCategoryOptions = [];
  private npwp:boolean;
  private professional:boolean;
  private companyLocation: string = "Domestic";
  public companyTypeProfessional: string = "Professional";

  get eInvoice() {
    return registrationStore.eInvoice;
  }

  get taxableEmployers() {
    return registrationStore.taxableEmployers;
  }

  get taxes() {
    return registrationStore.taxes;
  }

  created() {
    console.log(this.company);
    this.retrieveBusinessCategories();
    if(this.company.type == this.companyTypeProfessional){
      this.professional = true;
    }else{
      this.professional = false;
    }
    if(this.company.location == this.companyLocation){
      this.npwp = true;
    }else{
      this.npwp = false;
    }
  }

  private printBusinessCategory(row: any){
    if(row.valueBusinessCategories){
        let i, value, key;
        let stringArray = [];
        for (i=0; i<row.valueBusinessCategories.length; i++) {
            key = parseInt(row.valueBusinessCategories[i].substring( 0, row.valueBusinessCategories[i].indexOf('_')));
            value = row.valueBusinessCategories[i].substring(row.valueBusinessCategories[i].indexOf('_') + 1, row.valueBusinessCategories[i].length);
            stringArray.push(value);
        }
        return stringArray?.join(', ') || "";
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

  private retrieveBusinessCategories() {
    this.dynamicWindowService('/api/c-business-categories')
    .retrieve({
      criteriaQuery: `id.in=${this.businessCategory.values}`
    })
    .then(res => {
        let categories = res.data.map(item => {
            return {
                value: item.id,
                label: item.name,
                parent: item.parentCategoryId
            };
        });
        //console.log(categories);
        // Use set to uniquely identify added category.
        this.businessCategoryOptions = buildCascaderOptions(categories);
    });
  }

}
