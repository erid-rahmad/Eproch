import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { buildCascaderOptions } from '@/utils/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';

const VendorInvitationProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class VendorInvitation extends Mixins(AccessLevelMixin, VendorInvitationProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };

  loadingCategories = false;
  loadingSuggestions = false;

  vendorInvitationFormVisible: boolean = false;
  dialogConfirmationVisibleVendorSuggestion: boolean = false;

  vendorOptions: any[] = [];
  subCategoryOptions: any[] = [];

  businessCategory: any = {};
  businessCategorieValues = {
    id: '',
    name: ''
  }

  vendorSuggestion:any = {
    vendor: '',
    vendorObj: '',
    subCategoryObj: '',
    subCategory: '',
    addressId: '',
    address: ''
  }

  public rules = {
      values: {
          type: 'array',
          required: true,
          defaultFields: {type: 'array', len: 3, required: true},
          message: 'Business Category is required'
      }
  };
  public businessCategoryOptions = [];

  canAddVendor: boolean = false;
  bidding: Record<string, any> = {};
  vendorSelection: string;

  created() {
    this.bidding = {...this.data};
    this.bidding.step = BiddingStep.SELECTION;
    this.vendorSelection = this.bidding.vendorSelection;

    if (! this.editMode) {
      this.bidding.vendorInvitations = [];
      this.bidding.vendorSuggestions = [];
    }

    this.retrieveBusinessCategories();
    this.retrieveSubBusinessCategories();

    if (this.vendorSelection === 'OPN') {
      this.canAddVendor = false;
    } else if (this.vendorSelection == 'IVT') {
      this.canAddVendor = false;
    } else if (this.vendorSelection == 'DRC') {
      this.canAddVendor = true;
    }
  }

  addBusinessCategory() {
    this.vendorInvitationFormVisible = true;
  }

  addVendorSuggestion() {
    this.dialogConfirmationVisibleVendorSuggestion = true;
  }

  saveBusinessCategory() {
    const nodes = (<any>this.$refs.businessCategories).getCheckedNodes();
    const classifications = new Set<number>();
    let vendorSuggestionCriteria = [
      'active.equals=true'
    ];

    nodes.forEach(element => {
      const path = element.path;
      const pathLabels = element.pathLabels;

      if (element.level === 3) {
        const vendorInvitation = {
          adOrganizationId: this.adOrganizationId,
          businessClassificationId: path[0],
          businessClassificationName: pathLabels[0],
          businessCategoryId: path[1],
          businessCategoryName: pathLabels[1],
          businessSubCategoryId: path[2],
          businessSubCategoryName: pathLabels[2]
        }

        const lineExist = (this.bidding.vendorInvitations.some((vLine: any) => {
          return vLine.businessSubCategoryId === vendorInvitation.businessSubCategoryId;
        }));

        if (!lineExist) {
          this.bidding.vendorInvitations.push(vendorInvitation);

          if (this.vendorSelection === 'OPN') {
            if (! classifications.has(path[0])) {
              classifications.add(path[0]);
              vendorSuggestionCriteria.push(`businessClassificationId.in=${path[0]}`);
            }
          } else if (this.vendorSelection === 'IVT') {
            vendorSuggestionCriteria.push(`subBusinessCategoryId.in=${path[2]}`);
          }
        }
      }
    });

    this.retrieveVendorSuggestions(vendorSuggestionCriteria);
    this.vendorInvitationFormVisible = false;
  }

  saveVendorSuggestion() {
    this.vendorSuggestion.vendorObj = this.vendorOptions.find(item => item.vendorId === this.vendorSuggestion.vendor);
    this.vendorSuggestion.subCategoryObj = this.subCategoryOptions.find(item => item.id === this.vendorSuggestion.subCategory);
    this.bidding.vendorSuggestions.push(this.vendorSuggestion);

    this.dialogConfirmationVisibleVendorSuggestion = false;

    this.vendorSuggestion = {
      vendor: '',
      vendorObj: '',
      subCategoryObj: '',
      subCategory: '',
      addressId: '',
      address: ''
    }
  }

  removeBusinessCategory(index) {
    this.bidding.vendorInvitations.splice(index, 1);
  }

  removeVendorSuggestion(index) {
    this.bidding.vendorSuggestions.splice(index, 1);
  }

  private retrieveBusinessCategories() {
      this.commonService('/api/c-business-categories')
      .retrieve({
          criteriaQuery: this.updateCriteria(['active.equals=true']),
          paginationQuery: {
              size: 1000,
              page: 0,
              sort: ['name']
          }
      })
      .then(res => {
          let categories = res.data.map(item => {
              return {
                  value: item.id,
                  label: item.name,
                  parent: item.parentCategoryId
              };
          });

          // Use set to uniquely identify added category.
          this.businessCategoryOptions = buildCascaderOptions(categories);
      });
  }

  private retrieveVendorSuggestions(criteria: string[]) {
    this.loadingCategories = true;
    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: this.updateCriteria(criteria),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        const categories = res.data as any[];
        const locationCriteria = categories.map(category => `vendorId.in=${category.vendorId}`);
        const subCategoryMap = new Map<number, any>();

        for (const category of categories) {
          subCategoryMap.set(category.vendorId, {
            subCategoryId: category.subBusinessCategoryId,
            subCategoryName: category.subBusinessCategoryName
          });
        }

        this.vendorOptions = categories;
        this.loadingCategories = false;
        this.retrieveVendorLocations(locationCriteria, subCategoryMap);
      })
      .catch(err => {
        console.log('Failed to get business categories. %O', err);
        this.$message.error('Failed to get business categories');
        this.loadingCategories = false;
      });
  }

  private retrieveVendorLocations(criteria: string[], subCategoryMap?: Map<number, any>) {
    this.loadingSuggestions = true;
    this.commonService('/api/c-vendor-locations')
      .retrieve({
        criteriaQuery: this.updateCriteria(criteria),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        this.bidding.vendorSuggestions = (res.data as any[]).map(location => {
          let subCategory = {
            subCategoryId: null,
            subCategoryName: null
          };

          if (subCategoryMap) {
            subCategory = subCategoryMap.get(location.vendorId);
          }

          return {
            vendorId: location.vendorId,
            vendorName: location.vendorRegisteredName,
            businessSubCategoryId: subCategory.subCategoryId,
            businessSubCategoryName: subCategory.subCategoryName,
            locationId: location.locationId,
            address: location.locationName + ', ' + location.cityName
          };
        });

        console.log('Loaded suggestions: %O', this.bidding.vendorSuggestions);
      })
      .catch(err => {
        console.log('Failed to get vendor suggestions. %O', err);
        this.$message.error('Failed to get the vendor suggestions');
      })
      .finally(() => {
        this.loadingSuggestions = false;
      })
  }

  private retrieveSubBusinessCategories(): void {
    this.commonService("/api/c-business-categories")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'sector.equals=TERTIARY'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.subCategoryOptions = res.data;
      });
  }

  /* private retrieveVendor(vendorId, key): void {
    let filterQuery = "";
    if(key == 1){
      filterQuery = "active.equals=true";
    }else{
      filterQuery = `active.equals=true&id.equals=${vendorId}`;
    }
    this.commonService("/api/c-vendors")
      .retrieve({
        criteriaQuery: filterQuery
      })
      .then(res => {
        //if(key == 1){
          //this.vendorOptions = res.data;
        }else{
          res.data.map((item: any) => {
            //this.retrieveVendor(item.vendorId, 2);
            //console.log(item);
            this.getVendorDetail(item.id);
            this.vendorSuggestion.vendor = item.id;
            this.vendorSuggestion.vendorObj = item;
            this.vendorInvitation.vendorSuggestion.push(this.vendorSuggestion);

            return item;
          });
        }
      });
  } */

  getVendorDetail(vendorId){
    //this.retrieveVendorSubCategory(vendorId);
    this.retrieveVendorAddress(vendorId);
  }

  getVendor(businessCategory){
    console.log(businessCategory);
    // this.retrieveVendorBySubCategory(businessCategory);
    this.vendorSuggestion.vendor = "";
    this.vendorSuggestion.address = "";
  }

  clearSubCategory(){
    this.vendorSuggestion.vendor = "";
    this.vendorSuggestion.vendorObj = "";
    this.vendorOptions = []
    this.vendorSuggestion.address = "";
    this.vendorSuggestion.addressId = "";
  }

  clearVendor(){
    this.vendorSuggestion.address = "";
    this.vendorSuggestion.addressId = "";
  }

  // retrieve vendor sub category
  /*private retrieveVendorSubCategory(vendorId) {
    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: `vendorId.equals=${vendorId}`
      })
      .then(res => {
        this.retrieveBusinessCategory(res.data[0].businessCategoryId, 1);
      });
  }*/

  private retrieveBusinessCategory(businessCategoryId, key) {
    this.commonService('/api/c-business-categories')
      .retrieve({
        criteriaQuery: `id.equals=${businessCategoryId}`
      })
      .then(res => {
        if(key == 1){
          this.vendorSuggestion.subCategoryId = res.data[0].id;
          this.vendorSuggestion.subCategory = res.data[0].name;
        } else if(key == 2){
          //select business category primary
        } else if(key == 3){
          //select business category tertiary
        }
      });
  }

  // retrieve vendor address
  private retrieveVendorAddress(vendorId) {
    this.commonService('/api/c-vendor-locations')
      .retrieve({
        criteriaQuery: `vendorId.equals=${vendorId}`
      })
      .then(res => {
        if(vendorId){
          this.retrieveLocation(res.data[0].locationId);
        }
      });
  }

  private retrieveLocation(locationId) {
    this.commonService('/api/c-locations')
      .retrieve({
        criteriaQuery: `id.equals=${locationId}`
      })
      .then(res => {
        this.vendorSuggestion.addressId = res.data[0].id;
        this.vendorSuggestion.address = res.data[0].address1;
      });
  }

  /**
   * Invoked before proceeding to the next step.
   */
  save() {
    this.$emit('saved', {
      data: this.bidding
    });
  }
}
