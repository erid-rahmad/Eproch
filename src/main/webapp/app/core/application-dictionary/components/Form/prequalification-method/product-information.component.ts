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

  deleteRow(index, rows) {
    this.tableDataquestion.splice(index, 1);
    if (this.addCount > 0)
      --this.addCount;
  };
  saveRow(index, rows) {
    //  api
  };
  addRow() {
    this.tableDataquestion.push({ question: null });
    ++this.addCount;
  };
  saveAll() {
    // api
    // console.log(JSON.stringify(this.disabledList));
  };
  handleSaveRow() {
    this.isEdit = false
  };
  handleSaveRow1() {
    this.isEdit = true
  };
  isEdit=true;

  tableDataquestion = [{
      question: 'Policy Statment - apakah perusahaan memiliki kebijakan K3L dalam menjalankan usahanya ? ',

    },
    {
      question: 'Emergency Response procedure - apakah perusahaan memiliki prosedur tanggap darurat ?',

    },
    {
      question: 'Basic Safety Rules - apakah perusahaan memiliki peraturan dasar keselamatan kerja ?',

    },
  ];
  addCount = 0;

  handleChange(val) {
    console.log(val);
  }
  activeNames = ['1'];


  evaluationtype = [{
    value: 'Pass Fail',
    label: 'Pass Fail'
  }, {
    value: 'non Pass Fail',
    label: 'non Pass Fail'
  }, ];
  active = [{
    value: 'yes',
    label: 'yes'
  }, {
    value: 'no',
    label: 'no'
  }, ];
  type = [{
    value: 'prequalification',
    label: 'prequalification'
  }, {
    value: 'non prequalification',
    label: 'non prequalification'
  }, ];
  optiontype = [{
    value: 'Drop Down',
    label: 'Drop Down'
  }, {
    value: 'Input Text',
    label: 'Input Text'
  }, ];

  value: any = {};
  dialogTableVisible = false;
  tableData = [{
      1: 'Organisasi dan Management',
    }, {
      1: 'Pengelolaan k3',
    }, {
      1: 'Pengelolaan Sumberdaya',
    },

  ];
  checked = true;

  dummy: any = {};


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
