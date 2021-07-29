import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';



const ContractInfoProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class TaskComponent extends Mixins(AccessLevelMixin, ContractInfoProps) {



  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  indexs:boolean=true;

  loading:boolean;
  ContactTasks:any=[
    {
      id:'',
      name:'TESS',
      contractDocID:'',
      Pic:'',
      DueDate:'',
      ContractTaskStatus:'',
      contractId:''
    },
    {
      id:'',
      name:'TES',
      contractDocID:'',
      Pic:'',
      DueDate:'',
      ContractTaskStatus:'',
      contractId:''
    },
    {
      id:'',
      name:'TES',
      contractDocID:'',
      Pic:'',
      DueDate:'',
      ContractTaskStatus:'',
      contractId:''
    },

  ]

  ContactTask:any={
    id:'',
    contractDocID:'',
    Pic:'',
    DueDate:'',
    ContractTaskStatus:'',
    contractId:''
  }

  view(row){


  }


}
