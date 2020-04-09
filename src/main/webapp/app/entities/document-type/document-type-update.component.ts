import { Component, Vue, Inject } from 'vue-property-decorator';

import DocumentTypeBusinessCategoryService from '../document-type-business-category/document-type-business-category.service';
import { IDocumentTypeBusinessCategory, DocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentType, DocumentType } from '@/shared/model/document-type.model';
import DocumentTypeService from './document-type.service';
import BusinessCategoryService from '../business-category/business-category.service';
import { flattenProperty } from '@/utils';
import { BusinessCategory, IBusinessCategory } from '@/shared/model/business-category.model';

@Component
export default class DocumentTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;

  public documentType: IDocumentType = new DocumentType();
  public documentTypeBusinessCategories: IDocumentTypeBusinessCategory[] = [];
  public isSaving = false;
  public columnSpacing = 32;
  public rules = {};
  public businessCategories = [];
  private mandatoryBusinessCategories = [];
  private additionalBusinessCategories = [];
  public businessCategoriesProps = {
    multiple: true,
    lazy: true,
    lazyLoad: (node, resolve) => {
      let query = node.root ? { 'parentCategoryId.specified': false } : { 'parentCategoryId.equals': node.value };

      this.businessCategoryService()
        .retrieveWithFilter(query)
        .then(res => {
          const nodes = res.data.map(item => {
            return { value: item.id, label: item.name };
          });
          resolve(nodes);
        });
    }
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeId) {
        vm.retrieveDocumentType(to.params.documentTypeId);
      }
      vm.initBusinessCategories();
      vm.initRelationships();
    });
  }

  public shouldHide(option: string) {
    return option !== 'S';
  }

  public handleMandatoryVendorType(value: String[]) {
    this.documentType.mandatoryForCompany = value.includes('C');
    this.documentType.additionalForCompany = value.includes('P');
  }

  public save(): void {
    this.isSaving = true;
    this.documentType.documentTypeBusinessCategories = this.buildJoin(this.documentType.documentTypeBusinessCategories);
    if (this.documentType.id) {
      this.documentTypeService()
        .update(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.previousState();
          const message = this.$t('opusWebApp.documentType.updated', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'info',
            duration: 3000
          });
        });
    } else {
      this.documentTypeService()
        .create(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.previousState();
          const message = this.$t('opusWebApp.documentType.created', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    }
  }

  private buildJoin(existingBusinessCategories: IDocumentTypeBusinessCategory[]): IDocumentTypeBusinessCategory[] {
    let join = [];
    let existingMap = new Map();
    let mandatorySet = new Set();
    let mandatory = (<any>this.$refs.mandatoryBusinessCategories).getCheckedNodes();
    let additional = (<any>this.$refs.additionalBusinessCategories).getCheckedNodes();

    if (existingBusinessCategories) {
      existingMap = new Map(existingBusinessCategories.map((item): [number, number] => [item.businessCategory.id, item.id]));
    }
    mandatory.forEach(element => {
      const entity = new DocumentTypeBusinessCategory(
        existingMap.get(element.value) || 0,
        true,
        false,
        new DocumentType(this.documentType.id),
        new BusinessCategory(element.value)
      );

      join.push(entity);
      mandatorySet.add(element.value);
    });

    additional.forEach(element => {
      // Don't add additional sub-category if it's already declared as mandatory.
      if (!mandatorySet.has(element.value)) {
        const entity = new DocumentTypeBusinessCategory(
          existingMap.get(element.value) || 0,
          false,
          true,
          new DocumentType(this.documentType.id),
          new BusinessCategory(element.value)
        );

        join.push(entity);
      }
    });

    return join;
  }

  public retrieveDocumentType(documentTypeId): void {
    this.documentTypeService()
      .find(documentTypeId)
      .then(res => {
        this.documentType = res;

        // Mandatory for Supplier Type checkbox group.
        let vendorTypes = [];
        if (this.documentType.mandatoryForCompany) {
          vendorTypes.push('C');
        }
        if (this.documentType.mandatoryForProfessional) {
          vendorTypes.push('P');
        }
        this.documentType.vendorTypes = vendorTypes;

        // Init model for Mandatory & Additional for Sub-business Category fields.
        this.initSubBusinessCategoryModels(this.documentType);
      });
  }

  public handleMandatoryBusinessCategoriesChange(value: number[][]) {
    this.documentType.mandatoryCategorySelections = value;
  }

  public handleAdditionalBusinessCategoriesChange(value: number[][]) {
    this.documentType.mandatoryCategorySelections = value;
  }

  private initSubBusinessCategoryModels(documentType: IDocumentType): void {
    if (documentType.documentTypeBusinessCategories.length) {
      let mandatorySelections = [];
      let additionalSelections = [];
      documentType.documentTypeBusinessCategories.forEach(item => {
        let ids = flattenProperty(item.businessCategory, 'parentCategory', 'id');
        if (ids.length === 3) {
          if (item.mandatory) {
            mandatorySelections.push(ids);
          } else if (item.additional) {
            additionalSelections.push(ids);
          }
        }
      });
      this.mandatoryBusinessCategories = mandatorySelections;
      this.additionalBusinessCategories = additionalSelections;
    }
  }

  public previousState(): void {
    this.$router.push('/redirect/document-type/list');
  }

  public initBusinessCategories(): void {
    this.businessCategoryService()
      .retrieve()
      .then(res => {
        let categories = (res.data as IBusinessCategory[]).map(item => {
          return {
            value: item.id,
            label: item.name,
            parent: item.parentCategoryId
          };
        });

        // Use set to to uniquely identify added category.
        this.businessCategories = this.collect(categories);
      });
  }

  public initRelationships(): void {
    this.documentTypeBusinessCategoryService()
      .retrieve()
      .then(res => {
        this.documentTypeBusinessCategories = res.data;
      });
  }

  private collect(source) {
    let parents = new Map();
    let children = new Map();
    let result = [];
    let addToChildrenCache = item => {
      let items = children.get(item.parent);
      if (!items) {
        items = [];
        children.set(item.parent, items);
      }
      items.push(item);
    };

    let addChild = (parent, item) => {
      if (!parent.children) {
        parent.children = [];
      }
      parent.children.push(item);
    };

    let addChildren = item => {
      let items = children.get(item.value);
      if (items) {
        if (!item.children) {
          item.children = [];
        }
        item.children.concat(items);
      }
    };

    source.forEach(s => {
      let item = { ...s };
      if (item.parent) {
        let parent = parents.get(item.parent);
        if (!parent) {
          addToChildrenCache(item);
        } else {
          addChild(parent, item);
        }

        addChildren(item);
      } else {
        result.push(item);
        addChildren(item);
      }
      parents.set(item.value, item);
      delete item.parent;
    });

    return result;
  }
}
