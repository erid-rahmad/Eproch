import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import PrequalificationForm from './prequalification-form.vue';

const VendorScoringProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
})

@Component({
  components: {
    PrequalificationForm
  }
})
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

  evaluationMethod = 'Tender Goods';
  processing = false;
  dialogConfirmationVisible:boolean = false;

  public criteriaOptions: any = {};
  public subCriteriaOptions: any = {};

  private vendorScoringCriteria:any = {
    criteria: '',
    criteriaObj: '',
    subCriteria: '',
    subCriteriaObj: '',
    percentage: '',
    pic: '',
    picName: ''
  };
  private num = 1958806;
  bidding: Record<string, any> = {};
  private line: any = {};

  get readOnly() {
    return this.bidding.biddingStatus === 'In Progress';
  }

  created() {
    this.bidding = { ...this.data };
    console.log("this bidding",this.bidding);
    
    this.bidding.step = BiddingStep.SCORING;
    // this.bidding.scoringCriteria = [
    //   {
    //     criteria: 'Quality',
    //     criteriaObj: '',
    //     subCriteria: 'Quality',
    //     subCriteriaObj: '',
    //     percentage: 20,
    //     pic: 'Procurement',
    //     picName: 'Procurement'
    //   },
    //   {
    //     criteria: 'Cost',
    //     criteriaObj: '',
    //     subCriteria: 'Cost',
    //     subCriteriaObj: '',
    //     percentage: 40,
    //     pic: 'admincost',
    //     picName: 'admincost'
    //   },
    //   {
    //     criteria: 'Delivery',
    //     criteriaObj: '',
    //     subCriteria: 'Timeline',
    //     subCriteriaObj: '',
    //     percentage: 15,
    //     pic: 'admintimeline',
    //     picName: 'admintimeline'
    //   },
    //   {
    //     criteria: 'Safety',
    //     criteriaObj: '',
    //     subCriteria: 'Packaging',
    //     subCriteriaObj: '',
    //     percentage: 15,
    //     pic: 'adminpackaging',
    //     picName: 'adminpackaging'
    //   },
    //   {
    //     criteria: 'Morale',
    //     criteriaObj: '',
    //     subCriteria: 'Morale',
    //     subCriteriaObj: '',
    //     percentage: 15,
    //     pic: 'adminmorale',
    //     picName: 'adminmorale'
    //   }
    // ];
    this.getVendorScoringLine();
    this.getVendorScoring();
    
    this.getCriteria();

    
    
  }

  private getVendorScoring() {
    this.commonService('/api/m-vendor-scorings')
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${this.bidding.id}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.bidding.scoringCriteria = res.data;
        console.log("this.bidding.scoringCriteria", this.bidding.scoringCriteria);
      });
  }

  private getVendorScoringLine() {
    this.commonService('/api/m-vendor-scoring-lines')
      .retrieve({
        criteriaQuery: [
          // `vendorScoringId.equals=${this.bidding.scoringCriteria.id}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.line = res.data;
        console.log("this.bidding.scoringCriteria.line", this.line);
        
      });
  }

  private getCriteria() {
    this.commonService('/api/c-bidding-criteria')
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

    this.vendorScoringCriteria.subCriteria = '';
    this.vendorScoringCriteria.pic = '';
    this.vendorScoringCriteria.picName = '';
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
      criteria: '',
      criteriaObj: '',
      subCriteria: '',
      subCriteriaObj: '',
      percentage: '',
      pic: '',
      picName: ''
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
