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
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class VendorInvitation extends Mixins(AccessLevelMixin, VendorInvitationProp) {

  private updated = false;
  private recordsLoaded = true;

  private prequalificationUrl = '/api/m-prequalification-informations';
  private prequalificationSuggestionUrl = '/api/m-prequal-vendor-suggestions';
  private vendorBusinessCategoryUrl = '/api/c-vendor-business-cats';
  private prequalificationSubmissionUrl = '/api/m-prequalification-submissions';

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

  passedSubmissionVendorIds: number[] = [];

  get readOnly() {
    return this.bidding.biddingStatus === 'P';
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
      if(this.vendorSelection === 'DRC'){
        if(!this.bidding.vendorSuggestions.length || !this.bidding.vendorInvitations.length){
          console.log('suggestions / invitations empty, fetching from prequalifications...')
          // if empty, presumably because suggestions are not set up yet
          Promise.allSettled([
            new Promise((resolve, reject)=>{
              this.commonService(this.prequalificationUrl)
              .retrieve({
                criteriaQuery: this.updateCriteria([
                  'active.equals=true',
                  `quotationId.equals=${this.data.quotationId}`,
                  'documentStatus.notEquals=TRM'
                ]),
                paginationQuery: {
                  page: 0,
                  size: 100,
                  sort: ['id']
                }
              }).then((res)=>{
                if (res.data.length) {
                  Promise.allSettled([
                    this.retrieveSuggestionsFromPrequalification(res.data[0].id)
                  ]).then(_results=>resolve(true))
                } else reject(false);
              }).catch((err)=>{
                console.log(err);
                reject(false);
              })
            })
          ]).then((_res)=>{
            
          })
        }
      }
      this.recordsLoaded = true;
    });

    this.bidding.step = BiddingStep.SELECTION;
    this.vendorSelection = this.bidding.vendorSelection;

    if (this.vendorSelection === 'OPN') {
      this.canAddVendor = false;
    } else if (this.vendorSelection === 'IVT') {
      this.canAddVendor = true;
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

  private retrieveSuggestionsFromPrequalification(preqId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingCategories = true;
      this.commonService(this.prequalificationSuggestionUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `prequalificationId.equals=${preqId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        let suggestions: any[] = [];
        suggestions = res.data.map((elem)=>{
          return {
            biddingId: this.bidding.id,
            adOrganizationId: elem.adOrganizationId,
            businessSubCategoryId: elem.businessSubCategoryId,
            businessSubCategoryName: elem.businessSubCategoryName,
            vendorId: elem.vendorId,
            vendorName: elem.vendorName
          }
        });

        Promise.allSettled([this.retrievePassedPreqVendors(preqId)]).then(_res=>{
          console.log(this.passedSubmissionVendorIds);
          suggestions = suggestions.filter((elem)=>{
            return this.passedSubmissionVendorIds.findIndex((id)=>id==elem.vendorId)!=-1;
          });
          this.$set(this.bidding, 'vendorSuggestions', suggestions);
          this.$set(this.bidding, 'removedVendorSuggestions', []);

          Promise.allSettled([this.retrieveInvitationsFromPassedVendors(this.passedSubmissionVendorIds)]).then(_res=>{
            resolve(true);
          }).catch(_err=> reject(false));
        }).catch(_err=> reject(false));
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

  private retrieveInvitationsFromPassedVendors(vendorIds: number[]): Promise<boolean> {
    let additionalCriteria = [];
    vendorIds.forEach((a)=>{
      additionalCriteria.push(`vendorId.in=${a}`)
    })
    return new Promise((resolve, reject) => {
      this.loadingCategories = true;
      this.commonService(this.vendorBusinessCategoryUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          ...additionalCriteria
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        let invitations: any[] = [];
        let subcats: number[] = [];
        let i = 0;

        for(i=0; i<res.data.length; i++) {
          if( subcats.length==0 || subcats.findIndex((id)=>{res.data[i].subBusinessCategoryId==id})!=-1 ){
            subcats.push(res.data[i].subBusinessCategoryId);
            invitations.push({
              biddingId: this.bidding.id,
              adOrganizationId: this.bidding.adOrganizationId,
              businessClassificationId: res.data[i].businessClassificationId,
              businessClassificationName: res.data[i].businessClassificationName,
              businessCategoryId: res.data[i].businessCategoryId,
              businessCategoryName: res.data[i].businessCategoryName,
              businessSubCategoryId: res.data[i].subBusinessCategoryId,
              businessSubCategoryName: res.data[i].subBusinessCategoryName,
            })
          }
        }

        console.log(invitations);

        this.$set(this.bidding, 'vendorInvitations', invitations);
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

  private retrievePassedPreqVendors(preqId: number): Promise<boolean> {
    return new Promise((resolve,reject)=>{
      this.commonService(this.prequalificationSubmissionUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `prequalificationId.equals=${preqId}`,
          'passFail.equals=pass'
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      }).then((res)=>{
        this.passedSubmissionVendorIds = res.data.map((res)=>{return res.vendorId});
        console.log(this.passedSubmissionVendorIds);
        resolve(true);
      }).catch(err => {
        console.log(err);
        reject(false);
      })
    })
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
    let vendorSuggestionCriteria: Set<string> = new Set(this.updateCriteria[
      'active.equals=true'
    ]);

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

          if (this.vendorSelection === 'IVT') {
            vendorSuggestionCriteria.add(`subBusinessCategoryId.in=${path[2]}`);
          } else {
            if (! classifications.has(path[0])) {
              classifications.add(path[0]);
              vendorSuggestionCriteria.add(`businessClassificationId.in=${path[0]}`);
            }
          }
        }
      }
    });

    if (this.vendorSelection !== 'DRC') {
      this.bidding = {...this.bidding, ...{ vendorInvitations }};
      this.onVendorInvitationChanged(Array.from(vendorSuggestionCriteria));
    }
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
    const removedItem = this.bidding.vendorInvitations.splice(index, 1);
    this.bidding.removedVendorInvitations.push(removedItem[0]);
    console.log('removed suggestion:', this.bidding.removedVendorInvitations);
  }

  removeVendorSuggestion(index) {
    const removedItem = this.bidding.vendorSuggestions.splice(index, 1);
    this.bidding.removedVendorSuggestions.push(removedItem[0]);
    console.log('removed suggestion:', this.bidding.removedVendorSuggestions);
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
        let sugs = (res.data as any[]).map(location => {
          let subCategory = {
            subCategoryId: null,
            subCategoryName: null
          };

          if (subCategoryMap) {
            subCategory = subCategoryMap.get(location.vendorId);
          }

          try{
            return {
              vendorId: location.vendorId,
              vendorName: location.vendorRegisteredName,
              businessSubCategoryId: subCategory.subCategoryId,
              businessSubCategoryName: subCategory.subCategoryName,
              locationId: location.locationId,
              address: location.locationName + ', ' + location.cityName
            };
          } catch(e){
            console.log(e);
            return {};
          }
        });
        if(sugs.length>0){
          sugs.forEach((elem)=>{
            if(this.bidding.vendorSuggestions.length==0 || 
              this.bidding.vendorSuggestions.findIndex(e=>{elem.vendorId==e.vendorId})==-1){
              this.bidding.vendorSuggestions.push(elem);
            }
          })
        }
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
    let vendorSuggestionCriteria = this.updateCriteria([
      'active.equals=true'
    ]);

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
  save(changeStep: boolean) {
    this.commonService('/api/m-biddings/save-form')
      .update(this.bidding)
      .then(res => {
        this.$message.success('Vendor Invitation has been saved successfully');
        this.$emit('saved', {
          data: res,
          changeStep
        });
      })
      .catch(err => {
        console.log('Failed to save bidding schedule. %O', err);
        this.$message.error('Failed to save vendor invitations');
      });
  }
}
