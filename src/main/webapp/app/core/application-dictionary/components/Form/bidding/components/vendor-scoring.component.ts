import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import { BiddingStep } from '../steps-form.component';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { Inject, Mixins } from 'vue-property-decorator';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';

const VendorScoringProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class VendorScoring extends Mixins(AccessLevelMixin, VendorScoringProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  evaluationMethod = null;
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

  bidding: Record<string, any> = {};

  created() {
    this.bidding = {...this.data};
    this.bidding.step = BiddingStep.SCORING;
    this.bidding.scoringCriteria = [
      {
        criteria: 'Quality',
        criteriaObj: "",
        subCriteria: 'Quality',
        subCriteriaObj: "",
        percentage: 20,
        pic: 'Procurement',
        picName: 'Procurement'
      },
      {
        criteria: 'Cost',
        criteriaObj: "",
        subCriteria: 'Cost',
        subCriteriaObj: "",
        percentage: 50,
        pic: 'Finance',
        picName: 'Finance'
      },
      {
        criteria: 'Delivery',
        criteriaObj: "",
        subCriteria: 'Timeline',
        subCriteriaObj: "",
        percentage: 0,
        pic: 'Finance',
        picName: 'Finance'
      },
      {
        criteria: 'Safety',
        criteriaObj: "",
        subCriteria: 'Packaging',
        subCriteriaObj: "",
        percentage: 0,
        pic: 'Finance',
        picName: 'Finance'
      }
    ];

    this.getCriteria();
  }

  private getCriteria() {
    this.commonService("/api/c-bidding-criteria")
      .retrieve({
        criteriaQuery: this.updateCriteria([`active.equals=true`])
      })
      .then(res => {
        this.criteriaOptions = res.data;
      });
  }

  getSubCriteria(criteriaId?: number) {
    if (!criteriaId) {
      return;
    }

    this.vendorScoringCriteria.subCriteria = "";
    this.vendorScoringCriteria.pic = "";
    this.vendorScoringCriteria.picName = "";
    this.commonService('/api/c-bidding-sub-criteria')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingCriteriaId.equals=${criteriaId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.subCriteriaOptions = res.data;
      });
  }

  getPic(subCriteriaId?: number) {
    let subCriteriaObj = this.subCriteriaOptions.find(item => item.id === subCriteriaId);
    this.vendorScoringCriteria.pic = subCriteriaObj.adUserUserId;
    this.vendorScoringCriteria.picName = subCriteriaObj.adUserUserName;
  }

  addScoring() {
    this.dialogConfirmationVisible = true;
  }

  removeScoring(index) {
    this.bidding.scoringCriteria.splice(index, 1);
  }

  saveScoring() {

    this.vendorScoringCriteria.criteriaObj = this.criteriaOptions.find(item => item.id === this.vendorScoringCriteria.criteria);
    this.vendorScoringCriteria.subCriteriaObj = this.subCriteriaOptions.find(item => item.id === this.vendorScoringCriteria.subCriteria);

    this.bidding.scoringCriteria.push(this.vendorScoringCriteria);
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
