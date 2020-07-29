import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
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
    },
    taxRates: {
      type: Array,
      default: () => []
    },
    taxInformations: {
      type: Object,
      default: () => {
        return null;
      }
    },
    
  }
})

@Component
export default class SummaryRegistration extends SummaryRegistrationProps {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 32;  
  public businessCategoryValues = []; 
  public businessCategoryOptions = [];

  mounted() {
    //console.log(this.businessCategory);
    this.retrieveBusinessCategories();
  }

  handleChange(val) {
    //console.log(val);
  }

  private printBusinessCategory(row: any){
    if(row.businessCategories){
        let i, value, key;
        let stringArray = [];
        for (i=0; i<row.businessCategories.length; i++) {
            key = parseInt(row.businessCategories[i].substring( 0, row.businessCategories[i].indexOf('_')));
            value = row.businessCategories[i].substring(row.businessCategories[i].indexOf('_') + 1, row.businessCategories[i].length);
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
