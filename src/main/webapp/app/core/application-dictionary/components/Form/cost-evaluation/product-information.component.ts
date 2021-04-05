import {
  ElForm
} from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import ProductImages from './components/form/product-images.vue';
import SpecialPrice from './components/form/special-price.vue';
import ProductVideo from './components/form/product-video.vue';
import Vue from 'vue';
import {
  AccountStoreModule as accountStore
} from '@/shared/config/store/account-store';
import {
  IMProductCatalog
} from '@/shared/model/m-product-catalog.model';

const ProductCatalogProp = Vue.extend({
  props: {
    setRowProductCatalog: {
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
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, ProductCatalogProp) {

  data() {
    return {
      isHidden: true,
      tableData: [{
        1: 'Honda 2015',
        22: 'Honda 2014',
        33: 'Honda 2013',
        2: '50',
        3: 'Ea',
        4: '',
        5: '238.000.000',
        6: '11.900.000.000',
        7: '239.000.000',
        8: '11.950.000.000',
        9: '0.4%',
        10: '238.500.000',
        11: '11.925.000.000',
        12: '0.2%',
      }, {
        1: 'Honda Civic 2017',
        22: 'Honda Civic 2017',
        33: 'Honda Civic 2017',
        2: '30',
        3: 'Ea',
        4: '',
        5: '439.000.000',
        6: '13.170.000.000',
        7: '440.000.000',
        8: '13.200.000.000',
        9: '0.22%',
        10: '439.000.000',
        11: '13.170.000.000',
        12: '0%',
      }, {
        1: 'Honda 2020',
        22: 'Honda 2020',
        33: 'Honda 2020',
        2: '100',
        3: 'Ea',
        4: '',
        5: '45.000.000',
        6: '4.500.000.000',
        7: '41.600.000',
        8: '4.160.000.000',
        9: '7.85%',
        10: '45.000.000',
        11: '4.500.000.000',
        12: '0%',
      }, ],
      checked: true,
    }
  };
  rules = {}

  props = {
    lazy: true,
    lazyLoad: async (node, resolve) => {
      const {
        level
      } = node;
      const {
        data
      } = node;
      if (level == 1) {
        const list = await this.retrieveProductSubCategories(data.value);
        resolve(list);
      } else if (level == 2) {
        const list = await this.retrieveProduct(data.value, level);
        resolve(list);
      }
    }

  }

  productCatalog: IMProductCatalog = {};
  galleryId: number = 0;
  visible: false;
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

  created() {
    this.retrieveProductCategories();
    this.retrieveUom();
    this.retrieveCurrency();
    this.retrieveBrand();

    if (this.setRowProductCatalog != null) {
      this.productCatalog = this.setRowProductCatalog;
      this.productCatalog.mProductCategory = [];
      this.productCatalog.mProductCategory.push(this.productCatalog.mProductCategoryId);
      this.productCatalog.mProductCategory.push(this.productCatalog.mProductSubCategoryId);
      this.productCatalog.mProductCategory.push(this.productCatalog.mProductId);
      console.log(this.productCatalog);
    } else {
      this.productCatalog = {};
    }
  }

  back() {
    this.$emit("closeProductInformation");
  }

  setProductId(value) {
    console.log(value);
    this.productCatalog.mProductId = value[value.length - 1];
  }

  setGalleryId(value) {
    this.galleryId = value;
  }

  submit() {

    this.fullscreenLoading = true;


    if (this.setRowProductCatalog == null) {
      this.productCatalog.documentAction = "APR";
      this.productCatalog.documentStatus = "DRF";
      this.productCatalog.active = true;
      this.productCatalog.adOrganizationId = 1;
      this.productCatalog.cDocumentTypeId = 655951;
      this.productCatalog.cVendorId = accountStore.userDetails.cVendorId;
      this.productCatalog.cGalleryId = this.galleryId;

      console.log(this.productGallery);
      console.log(this.productCatalog);

      this.dynamicWindowService(this.baseApiUrl)
        .create(this.productCatalog)
        .then(() => {

          this.$notify({
            title: 'Success',
            message: 'Product Catalog submitted.',
            type: 'success'
          });

          this.back();

        }).catch(error => {
          this.$notify({
            title: 'Error',
            message: error,
            type: 'error',
          });

        }).finally(() => {
          this.fullscreenLoading = false;
        });
    } else {
      if (this.galleryId != 0) {
        this.productCatalog.cGalleryId = this.galleryId;
      }

      this.dynamicWindowService(this.baseApiUrl)
        .update(this.productCatalog)
        .then(() => {

          this.$notify({
            title: 'Success',
            message: 'Product Catalog updated.',
            type: 'success'
          });

          this.back();

        }).catch(error => {
          this.$notify({
            title: 'Error',
            message: error,
            type: 'error',
          });

        }).finally(() => {
          this.fullscreenLoading = false;
        });
    }

  }

  private retrieveUom() {
    this.dynamicWindowService('/api/c-unit-of-measures')
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
    this.dynamicWindowService('/api/c-currencies')
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
    this.dynamicWindowService('/api/m-brands')
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

  jsonEncode(data: any, fields: string[]) {
    const record: Record < string, any > = {};
    for (const field of fields) {
      record[field] = data[field];
    }
    return JSON.stringify(record);
  }

  private getJsonField(json: string, field: string) {
    if (json) {
      const data = JSON.parse(json);
      return data[field];
    }
    return null;
  }

  retrieveProductCategories() {
    this.dynamicWindowService('/api/c-product-categories')
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

        console.log(categories);
        this.productCategoryOptions = categories;
      });
  }

  retrieveProductSubCategories(productCategoryParent) {
    return new Promise((resolve, reject) => {
      this.dynamicWindowService('/api/c-product-categories')
        .retrieve({
          criteriaQuery: [`parentCategoryId.equals=` + productCategoryParent],
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
      this.dynamicWindowService('/api/c-products')
        .retrieve({
          criteriaQuery: [`productSubCategoryId.equals=` + productSubCategory],
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

  validate() {
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if (passed) {
        this.submit();
      } else {
        console.log(errors);
      }

    });
  }

}
