import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const VendorScoringProp = Vue.extend({
  props: {
    biddingInformation: {
      type: Object,
      default: () => {}
    },
    vendorScoring: {
      type: Array,
      default: () => []
    },
  }
})

@Component
export default class VendorScoring extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, VendorScoringProp) {

  data() {
    return {
      vendorscoring: [
       
       
        {
          no: '1',
          criteria: 'quality',
          subcriteria: 'quality',
          persentage: '20',
          pic: 'admin quality',
        },
        {
          no: '2',
          criteria: 'cost',
          subcriteria: 'cost',
          persentage: '40',
          pic: 'admin cost',
        },
        {
          no: '3',
          criteria: 'Delivery',
          subcriteria: 'Timeline',
          persentage: '10',
          pic: 'admin Timeline',
        },
        {
          no: '4',
          criteria: 'safety',
          subcriteria: 'Packaging',
          persentage: '10',
          pic: 'admin Packaging',
        },
        {
          no: '5',
          criteria: 'Morale',
          subcriteria: 'Morale',
          persentage: '10',
          pic: 'admin Morale',
        },
        
      ]
    }
  };

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  processing = false;
  dialogConfirmationVisible:boolean = false;

  public criteriaOptions: any = {};
  public subCriteriaOptions: any = {};

  private vendorScoringCriteria:any = {
    criteria: "",
    criteriaObj: "",
    subCriteria: "",
    subCriteriaObj: "",
    percentage: "",
    pic: "",
    picName: ""
  };

  created() {
    this.getCriteria();
  }

  private getCriteria(): void {
    this.dynamicWindowService("/api/c-bidding-criteria")
      .retrieve({
        criteriaQuery: `active.equals=true`
      })
      .then(res => {
        this.criteriaOptions = res.data;
      });
  }

  private getSubCriteria(criteriaId): void {
    this.vendorScoringCriteria.subCriteria = "";
    this.vendorScoringCriteria.pic = "";
    this.vendorScoringCriteria.picName = "";
    this.dynamicWindowService("/api/c-bidding-sub-criteria")
      .retrieve({
        criteriaQuery: `active.equals=true&biddingCriteriaId.equals=${criteriaId}`
      })
      .then(res => {
        this.subCriteriaOptions = res.data;
      });
  }

  private getPic(subCriteria){
    let subCriteriaObj = this.subCriteriaOptions.find(item => item.id === subCriteria);
    this.vendorScoringCriteria.pic = subCriteriaObj.adUserUserId;
    this.vendorScoringCriteria.picName = subCriteriaObj.adUserUserName;
  }

  addScoring(){
    this.dialogConfirmationVisible = true;
  }

  removeScoring(index){
    this.vendorScoring.splice(index, 1);
  }

  saveScoring(){

    this.vendorScoringCriteria.criteriaObj = this.criteriaOptions.find(item => item.id === this.vendorScoringCriteria.criteria);
    this.vendorScoringCriteria.subCriteriaObj = this.subCriteriaOptions.find(item => item.id === this.vendorScoringCriteria.subCriteria);

    this.vendorScoring.push(this.vendorScoringCriteria);
    this.dialogConfirmationVisible = false;
    this.vendorScoringCriteria = {
      criteria: "",
      criteriaObj: "",
      subCriteria: "",
      subCriteriaObj: "",
      percentage: "",
      pic: "",
      picName: ""
    };
  }

  //=======================================================================

  validate() {
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if(passed){
        //this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
