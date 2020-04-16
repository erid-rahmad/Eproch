import { BusinessCategory, IBusinessCategory } from '@/shared/model/business-category.model';
import { DocumentTypeBusinessCategory, IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import { DocumentType, IDocumentType } from '@/shared/model/document-type.model';
import { flattenProperty } from '@/utils';
import { Component, Inject, Vue } from 'vue-property-decorator';
import BusinessCategoryService from '../business-category/business-category.service';
import DocumentTypeService from './document-type.service';

@Component
export default class DocumentTypeUpdate extends Vue {
  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;

  public documentType: IDocumentType = new DocumentType();
  public isSaving = false;
  public columnSpacing = 32;
  public rules = {};
  public businessCategories = [];
  public mandatoryBusinessCategories = [];
  public additionalBusinessCategories = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeId) {
        vm.retrieveDocumentType(to.params.documentTypeId);
      }
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
    this.documentType.documentTypeBusinessCategories = this.updateBusinessCategoriesLink(this.documentType.documentTypeBusinessCategories);
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

  private updateBusinessCategoriesLink(existingBusinessCategories: IDocumentTypeBusinessCategory[]): IDocumentTypeBusinessCategory[] {
    let join = [];
    let existingMap = new Map<number, number>();

    // Cache for mandatory & additional business categories.
    let mandatorySet = new Set<number>();
    let additionalSet = new Set<number>();

    let mandatory = (<any>this.$refs.mandatoryBusinessCategories).getCheckedNodes();
    let additional = (<any>this.$refs.additionalBusinessCategories).getCheckedNodes();

    if (existingBusinessCategories) {
      existingMap = new Map(existingBusinessCategories.map((item): [number, number] => [item.businessCategory.id, item.id]));
    }
    mandatory.forEach(element => {
      const entity = new DocumentTypeBusinessCategory(
        existingMap.get(element.value) || 0, // The existing ID if any.
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
        additionalSet.add(element.value);
      }
    });

    // Flag the unchecked items as deleted, if any.
    if (existingBusinessCategories) {
      existingBusinessCategories.forEach(element => {
        if (mandatorySet.has(element.businessCategory.id) || additionalSet.has(element.businessCategory.id)) {
          const entity = new DocumentTypeBusinessCategory(
            element.id || 0,
            false,
            false,
            element.documentType,
            element.businessCategory,
            true // Removal flag.
          );
          join.push(entity);
        }
      });
    }

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

  public initRelationships(): void {
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
