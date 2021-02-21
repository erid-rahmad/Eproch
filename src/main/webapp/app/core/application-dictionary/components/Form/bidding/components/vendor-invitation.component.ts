import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import { buildCascaderOptions } from '@/utils/form';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const VendorInvitationProp = Vue.extend({
  props: {
    biddingInformation: {
      type: Object,
      default: () => {}
    },
    vendorInvitation: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class VendorInvitation extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, VendorInvitationProp) {

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };

  processing = false;

  dialogConfirmationVisibleBusinessCategory: boolean = false;
  dialogConfirmationVisibleVendorSuggestion: boolean = false;

  public vendorOptions: any = {};
  businessCategory: any = {};
  businessCategoriesId = [];
  businessCategoriesName = [];
  businessCategorieValues = {
    id: '',
    name: ''
  }

  vendorBusinessCategory:any = {
    businessClassificationId: '',
    businessClassificationName: '',
    businessCategoryId: '',
    businessCategoryName: '',
    businessSubCategoryId: '',
    businessSubCategoryName: ''
  }

  vendorSuggestion:any = {
    vendor: '',
    vendorObj: '',
    subCategoryId: '',
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

  vendorSelection: string = this.biddingInformation.vendorSelection;
  buttonDisable: boolean = false;

  created() {
    console.log(this.vendorSelection);
    this.retrieveBusinessCategories();
    this.retrieveVendor(0, 1);

    if(this.vendorSelection == 'Open'){
      this.buttonDisable = true;
    }else if(this.vendorSelection == 'Invitation Only'){
      this.buttonDisable = true;
    }else if(this.vendorSelection == 'Direct Assignment'){
      this.buttonDisable = false;
    }
  }

  addBusinessCategory(){
    this.dialogConfirmationVisibleBusinessCategory = true;
  }

  addVendorSuggestion(){
    this.dialogConfirmationVisibleVendorSuggestion = true;
  }

  saveBusinessCategory(){
    const values = (<any>this.$refs.businessCategories).getCheckedNodes();
    values.forEach(element => {
      if(element.level === 3){
        this.businessCategoriesId = element.path;
        this.businessCategoriesName = element.pathLabels;

        this.vendorBusinessCategory = {
          businessClassificationId: this.businessCategoriesId[0],
          businessClassificationName: this.businessCategoriesName[0],
          businessCategoryId: this.businessCategoriesId[1],
          businessCategoryName: this.businessCategoriesName[1],
          businessSubCategoryId: this.businessCategoriesId[2],
          businessSubCategoryName: this.businessCategoriesName[2]
        }

        const lineExist = (this.vendorInvitation.vendorBusinessCategory.some((vLine: any) => {
          return vLine.businessSubCategoryId === this.vendorBusinessCategory.businessSubCategoryId;
        }));

        if(!lineExist){
          this.vendorInvitation.vendorBusinessCategory.push(this.vendorBusinessCategory);

          if(this.vendorSelection == 'Open'){
            //console.log('1');
            //console.log(this.vendorBusinessCategory.businessClassificationId);
            //console.log(this.vendorBusinessCategory.businessClassificationName);
            this.retrieveBusinessCategory(this.vendorBusinessCategory.businessClassificationId, 2);
          }else if(this.vendorSelection == 'Invitation Only'){
            //console.log('2');
            //console.log(this.vendorBusinessCategory.businessSubCategoryId);
            //console.log(this.vendorBusinessCategory.businessSubCategoryName);
            //this.retrieveBusinessCategory(this.vendorBusinessCategory.businessSubCategoryId, 3);
            this.retrieveVendorBySubCategory(this.vendorBusinessCategory.businessSubCategoryId);
          }
        }

      }
    });

    this.dialogConfirmationVisibleBusinessCategory = false;
  }

  saveVendorSuggestion(){
    this.vendorSuggestion.vendorObj = this.vendorOptions.find(item => item.id === this.vendorSuggestion.vendor);
    this.vendorInvitation.vendorSuggestion.push(this.vendorSuggestion);

    this.dialogConfirmationVisibleVendorSuggestion = false;

    this.vendorSuggestion = {
      vendor: '',
      vendorObj: '',
      subCategoryId: '',
      subCategory: '',
      addressId: '',
      address: ''
    }
  }

  removeBusinessCategory(index){
    this.vendorInvitation.vendorBusinessCategory.splice(index, 1);
  }

  removeVendorSuggestion(index){
    this.vendorInvitation.vendorSuggestion.splice(index, 1);
  }

  private retrieveBusinessCategories() {
      this.dynamicWindowService('/api/c-business-categories')
      .retrieve({
          criteriaQuery: 'active.equals=true',
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

  private retrieveVendorBySubCategory(businessCategory) {
    this.dynamicWindowService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: `businessCategoryId.equals=${businessCategory}`
      })
      .then(res => {
        res.data.map((item: any) => {
          this.retrieveVendor(item.vendorId, 2);
          //console.log(item);
          return item;
        });
      });
  }

  private retrieveVendor(vendorId, key): void {
    let filterQuery = "";
    if(key == 1){
      filterQuery = "active.equals=true";
    }else{
      filterQuery = `active.equals=true&id.equals=${vendorId}`;
    }
    this.dynamicWindowService("/api/c-vendors")
      .retrieve({
        criteriaQuery: filterQuery
      })
      .then(res => {
        if(key == 1){
          this.vendorOptions = res.data;
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
  }

  getVendorDetail(vendorId){
    this.retrieveVendorSubCategory(vendorId);
    this.retrieveVendorAddress(vendorId);
  }

  // retrieve vendor sub category
  private retrieveVendorSubCategory(vendorId) {
    this.dynamicWindowService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: `vendorId.equals=${vendorId}`
      })
      .then(res => {
        this.retrieveBusinessCategory(res.data[0].businessCategoryId, 1);
      });
  }

  private retrieveBusinessCategory(businessCategoryId, key) {
    this.dynamicWindowService('/api/c-business-categories')
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
          //let
        }
      });
  }

  // retrieve vendor address
  private retrieveVendorAddress(vendorId) {
    this.dynamicWindowService('/api/c-vendor-locations')
      .retrieve({
        criteriaQuery: `vendorId.equals=${vendorId}`
      })
      .then(res => {
        this.retrieveLocation(res.data[0].locationId);
      });
  }

  private retrieveLocation(locationId) {
    this.dynamicWindowService('/api/c-locations')
      .retrieve({
        criteriaQuery: `id.equals=${locationId}`
      })
      .then(res => {
        this.vendorSuggestion.addressId = res.data[0].id;
        this.vendorSuggestion.address = res.data[0].address1;
      });
  }

  // ================================================================

  private validate(formIndex: number) {
      if (formIndex === 2) {
          (<ElForm>this.$refs.businessCategoryForm)?.validate(async (passed, errors) => {

            /*  const getValues = Array.from(registrationStore.businessCategories);
              for(let i in getValues){
                  registrationStore.deleteBusinessCategory(getValues[i]);
              };

              const values = (<any>this.$refs.businessCategories).getCheckedNodes();
              values.forEach(element => {
                  if(element.level === 3){
                      registrationStore.addBusinessCategory(element.value);
                  }
              });
              if (passed) {
                  await this.retrieveDocumentTypes(true);
                  await this.retrieveDocumentTypes(false);
                  const businessCategories = Array.from(registrationStore.businessCategories);
                  this.businessCategoryValues = businessCategories;
                  console.log(this.businessCategoryValues);
              }
              this.eventBus.$emit('step-validated', { passed, errors });
              */
          });
      }
  }

}
