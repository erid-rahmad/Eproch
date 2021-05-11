import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { buildCascaderOptions } from '@/utils/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
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

  private updated = false;
  private recordsLoaded = true;

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
  };

  vendorSuggestion: any = {
    vendor: '',
    vendorObj: '',
    subCategoryObj: '',
    subCategory: '',
    addressId: '',
    address: ''
  };

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

  get readOnly() {
    return this.bidding.biddingStatus === 'In Progress';
  }

  @Watch('bidding', { deep: true })
  onBiddingChanged(_bidding: Record<string, any>) {
    if (this.recordsLoaded && ! this.updated) {
      this.updated = true;
      this.$emit('change');
    }
  }

  onBusinessCategoryChanged(values) {
    console.log('onBusinessCategoryChanged. values:', values);
  }

  created() {
    this.recordsLoaded = false;
    this.bidding = {...this.data};
    this.bidding = {...this.bidding, ...{
      vendorInvitations: [],
      vendorSuggestions: []
    }};

    this.retrieveBusinessCategories();
    this.retrieveSubBusinessCategories();

    Promise.allSettled([
      this.retrieveVendorInvitations(this.bidding.id),
      this.retrieveVendorSuggestions(this.bidding.id)
    ])
    .then(_results => {
      this.recordsLoaded = true;
    });

    this.bidding.step = BiddingStep.SELECTION;
    this.vendorSelection = this.bidding.vendorSelection;

    if (this.vendorSelection === 'OPN') {
      this.canAddVendor = false;
    } else if (this.vendorSelection === 'IVT') {
      this.canAddVendor = false;
    } else if (this.vendorSelection === 'DRC') {
      this.canAddVendor = true;
    }
  }

  addBusinessCategory() {
    this.vendorInvitationFormVisible = true;
  }

  addVendorSuggestion() {
    this.dialogConfirmationVisibleVendorSuggestion = true;
  }

  private retrieveVendorInvitations(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingCategories = true;
      this.commonService('/api/m-vendor-invitations')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'vendorInvitations', res.data);
        this.$set(this.bidding, 'removedVendorInvitations', []);
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get vendor invitations. %O', err);
        this.$message.error('Failed to get vendor invitations');
        reject(false);
      })
      .finally(() => {
        this.loadingCategories = false;
      });
    });
  }

  private retrieveVendorSuggestions(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingSuggestions = true;
      this.commonService('/api/m-vendor-suggestions')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'vendorSuggestions', res.data);
        this.$set(this.bidding, 'removedVendorSuggestions', []);
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get vendor suggestions. %O', err);
        this.$message.error('Failed to get vendor suggestions');
        reject(false);
      })
      .finally(() => {
        this.loadingSuggestions = false;
      });
    });
  }

  saveBusinessCategory() {
    const nodes = (<any>this.$refs.businessCategories).getCheckedNodes();
    const classifications = new Set<number>();
    const vendorInvitations = this.bidding.vendorInvitations || [];
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
        };

        const lineExist = (vendorInvitations.some((vLine: any) => {
          return vLine.businessSubCategoryId === vendorInvitation.businessSubCategoryId;
        }));

        if (!lineExist) {
          vendorInvitations.push(vendorInvitation);

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

    this.bidding = {...this.bidding, ...{ vendorInvitations }};
    this.onVendorInvitationChanged(vendorSuggestionCriteria);
    this.vendorInvitationFormVisible = false;
  }

  saveVendorSuggestion() {
    this.vendorSuggestion.vendorObj = this.vendorOptions.find(item => item.vendorId === this.vendorSuggestion.vendor);
    this.vendorSuggestion.subCategoryObj = this.subCategoryOptions.find(item => item.id === this.vendorSuggestion.subCategory);
    this.bidding.vendorSuggestions.push({ ...this.vendorSuggestion });

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

  private onVendorInvitationChanged(criteria: string[]) {
    this.loadingSuggestions = true;
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
        this.retrieveVendorLocations(locationCriteria, subCategoryMap);
      })
      .catch(err => {
        console.log('Failed to get business categories. %O', err);
        this.$message.error('Failed to get business categories');
        this.loadingSuggestions = false;
      });
  }

  private retrieveVendorLocations(criteria: string[], subCategoryMap?: Map<number, any>) {
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

  private retrieveVendorsByCriteria(criteria: string[]) {
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
        this.vendorOptions = res.data;
      });
  }

  getVendorDetail(vendorId){
    this.retrieveVendorSubCategory(vendorId);
    this.retrieveVendorAddress(vendorId);
  }

  getVendor(businessCategory) {
    const classifications = new Set<number>();
    let vendorSuggestionCriteria = [
      'active.equals=true'
    ];

    this.retrieveVendorsByCriteria(vendorSuggestionCriteria);
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
  private retrieveVendorSubCategory(vendorId) {
    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: `vendorId.equals=${vendorId}`
      })
      .then(res => {
        this.retrieveBusinessCategory(res.data[0].businessCategoryId, 1);
      });
  }

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
    this.commonService('/api/m-biddings/save-form')
      .update(this.bidding)
      .then(res => {
        this.$emit('saved', {
          data: res
        });
      })
      .catch(err => {
        console.log('Failed to save bidding schedule. %O', err);
        this.$message.error('Failed to save vendor invitations');
      });
  }
}
