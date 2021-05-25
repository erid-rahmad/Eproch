import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import ProductImages from './components/form/product-images.vue';
import ProductVideo from './components/form/product-video.vue';
import SpecialPrice from './components/form/special-price.vue';

const baseApiCatalog = 'api/m-product-catalogs';
const baseApiDocumentType = 'api/c-document-types';

const ProductCatalogProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})

@Component({
  components: {
    SpecialPrice,
    ProductImages,
    ProductVideo
  }
})
export default class ProductInformation extends Mixins(AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  rules = {
    mProductCategory: { required: true, message: 'Product is required' },
    name: { required: true, message: 'Name is required' },
    mBrandId: { required: true, message: 'Brand is required' },
    shortDescription: { required: true, message: 'Short Description is required' },
    cUomId: { required: true, message: 'UoM is required' },
    cCurrencyId: { required: true, message: 'Currency is required' },
    price: { required: true, message: 'Price is required' },
  };

  props = {
    lazy: true,
    lazyLoad: async (node, resolve) => {
      const { level } = node;
      const { data } = node;
      if(level == 1) {
        const list = await this.retrieveProductSubCategories(data.value);
        resolve(list);
      } else if(level == 2) {
        const list = await this.retrieveProduct(data.value, level);
        resolve(list);
      }
    }

  }

  mainForm: IMProductCatalog = {};
  galleryId: number = 0;
  productGallery = {
    images: [],
    imagesId: []
  };

  public productCategoryOptions = [];

  private baseApiUrl = "/api/m-product-catalogs";
  fullscreenLoading = false;
  public uomOptions: any = {};
  public currencyOptions: any = {};
  public brandOptions: any = {};
  columnSpacing = 32;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get formConfig() {
    return settings.form;
  }

  get galleryName() {
    const name = this.mainForm.name;
    return name?.length > 30 ? name.substring(0, 29) : name;
  }

  created() {
    console.log('product-info created. data:', this.data);
    this.mainForm = { ...this.data };

    this.retrieveDocType('Product Catalog');
    this.retrieveProductCategories();
    this.retrieveUom();
    this.retrieveCurrency();
    this.retrieveBrand();

    if ( ! this.mainForm?.id) {
      this.mainForm.mProductCategory = [
        this.mainForm.mProductCategoryId,
        this.mainForm.mProductSubCategoryId,
        this.mainForm.mProductId
      ];
    }
  }

  setProductId(value){
    console.log(value);
    this.mainForm.mProductId = value[value.length-1];
  }

  setGalleryId(value){
    this.galleryId = value;
  }

  submit() {
    this.fullscreenLoading = true;

    console.log('mainForm:', this.mainForm);
    if ( ! this.mainForm.id) {
      this.mainForm.documentAction = "APR";
      this.mainForm.documentStatus = "DRF";
      this.mainForm.active = true;
      this.mainForm.adOrganizationId = accountStore.organizationInfo.id;
      this.mainForm.cVendorId = accountStore.userDetails.cVendorId;

      this.commonService(baseApiCatalog)
        .create(this.mainForm)
        .then(res => {
          this.$message.success('Product Catalog has been saved successfully');
          this.$emit('saved', res);
        }).catch(() => {
          this.$message.error('Failed to save the product catalog');
        }).finally(() => {
          this.fullscreenLoading = false;
        });
    } else {
      if (this.galleryId != 0) {
        this.mainForm.cGalleryId = this.galleryId;
      }

      this.commonService(baseApiCatalog)
        .update(this.mainForm)
        .then(res => {
          this.$message.success('Product Catalog has been updated successfully');
          this.$emit('saved', res);
        }).catch(error => {
          this.$message.error('Failed to update the product catalog');
        }).finally(() => {
          this.fullscreenLoading = false;
        });
    }

  }

  private retrieveDocType(name: string) {
    this.commonService(baseApiDocumentType)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `name.equals=${name}`
        ]
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm.cDocumentTypeId = res.data[0].id;
        }
      })
  }

  private retrieveUom() {
    this.commonService('/api/c-unit-of-measures')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.uomOptions = res.data;
      });
  }

  private retrieveCurrency() {
    this.commonService('/api/c-currencies')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.currencyOptions = res.data;
      });
  }

  private retrieveBrand() {
    this.commonService('/api/m-brands')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.brandOptions = res.data;
      });
  }

  retrieveProductCategories() {
    this.commonService('/api/c-product-categories')
    .retrieve({
      criteriaQuery: [`parentCategoryId.specified=false`],
      paginationQuery: {
          size: 1000,
          page: 0,
          sort: ['name']
      }
    }).then(res => {
      let categories = res.data.map(item => {
        return {
          value: item.id,
          label: item.name,
          parent: item.parentCategoryId
        };
      });

      this.productCategoryOptions = categories;
    });
  }

  retrieveProductSubCategories(productCategoryParent) {
    return new Promise((resolve, reject) => {
      this.commonService('/api/c-product-categories')
      .retrieve({
        criteriaQuery: [`parentCategoryId.equals=`+productCategoryParent],
        paginationQuery: {
            size: 1000,
            page: 0,
            sort: ['name']
        }
      }).then(res => {

        resolve(res.data.map(item => {
            return {
              value: item.id,
              label: item.name,
              parent: item.parentCategoryId,
            }
          }));

      }).catch(err => reject(err));
    });
  }

  retrieveProduct(productSubCategory, level) {
    return new Promise((resolve, reject) => {
      this.commonService('/api/c-products')
      .retrieve({
        criteriaQuery: [`productSubCategoryId.equals=`+productSubCategory],
        paginationQuery: {
            size: 1000,
            page: 0,
            sort: ['name']
        }
      }).then(res => {

        resolve(res.data.map(item => {
            return {
              value: item.id,
              label: item.name,
              parent: item.productSubCategoryId,
              leaf: level >= 2
            }
          }));

      }).catch(err => reject(err));
    });
  }

  //=======================================================================

  save() {
    (this.$refs.mainForm as ElForm).validate((passed, errors) => {
      if(passed){
        this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
