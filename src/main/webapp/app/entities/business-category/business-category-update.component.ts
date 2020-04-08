import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentTypeBusinessCategoryService from '../document-type-business-category/document-type-business-category.service';
import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

import VendorService from '../vendor/vendor.service';
import { IVendor } from '@/shared/model/vendor.model';

import AlertService from '@/shared/alert/alert.service';
import { IBusinessCategory, BusinessCategory } from '@/shared/model/business-category.model';
import BusinessCategoryService from './business-category.service';

@Component
export default class BusinessCategoryUpdate extends Vue {
  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;
  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;
  @Inject('vendorService') private vendorService: () => VendorService;
  public businessCategory: IBusinessCategory = new BusinessCategory();
  public businessCategories: IBusinessCategory[] = [];
  public documentTypeBusinessCategories: IDocumentTypeBusinessCategory[] = [];
  public vendors: IVendor[] = [];
  public isSaving = false;
  private rules = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.businessCategoryId) {
        vm.retrieveBusinessCategory(to.params.businessCategoryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.businessCategory.id) {
      this.businessCategoryService()
        .update(this.businessCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.businessCategory.updated', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'info',
            duration: 3000
          });
        });
    } else {
      this.businessCategoryService()
        .create(this.businessCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.businessCategory.created', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    }
  }

  public retrieveBusinessCategory(businessCategoryId): void {
    this.businessCategoryService()
      .find(businessCategoryId)
      .then(res => {
        this.businessCategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.businessCategoryService()
      .retrieve()
      .then(res => {
        this.businessCategories = res.data;
      });
    this.documentTypeBusinessCategoryService()
      .retrieve()
      .then(res => {
        this.documentTypeBusinessCategories = res.data;
      });
    this.businessCategoryService()
      .retrieve()
      .then(res => {
        this.businessCategories = res.data;
      });
    this.vendorService()
      .retrieve()
      .then(res => {
        this.vendors = res.data;
      });
  }
}
