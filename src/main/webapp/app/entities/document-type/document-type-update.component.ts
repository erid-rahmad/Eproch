import { Component, Vue, Inject } from 'vue-property-decorator';

import DocumentTypeBusinessCategoryService from '../document-type-business-category/document-type-business-category.service';
import { IDocumentTypeBusinessCategory, DocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentType, DocumentType } from '@/shared/model/document-type.model';
import DocumentTypeService from './document-type.service';
import BusinessCategoryService from '../business-category/business-category.service';
import { flatten } from '@/utils';
import { ElCascader } from 'element-ui/types/cascader';
import { ElCascaderPanel } from 'element-ui/types/cascader-panel';
import { BusinessCategory } from '@/shared/model/business-category.model';

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
  public businessCategoriesProps = {
    multiple: true,
    lazy: true,
    lazyLoad: (node, resolve) => {
      let query = node.root ? { 'parentCategoryId.specified': false } : { 'parentCategoryId.equals': node.value };
      
      this.businessCategoryService()
        .retrieveWithFilter(query)
        .then(res => {
          const nodes = res.data.map(item => {
            return {value: item.id, label: item.name}
          });
          resolve(nodes);
        });
    }
  }

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
    this.documentType.documentTypeBusinessCategories = this.buildJoin();

    if (this.documentType.id) {
      this.documentTypeService()
        .update(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
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
          this.$router.go(-1);
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

  private buildJoin(): Array<any> {
    let join = [];
    let mandatorySet = new Set();
    let mandatory = (<any>this.$refs.mandatoryBusinessCategories).getCheckedNodes();
    let additional = (<any>this.$refs.additionalBusinessCategories).getCheckedNodes();
    mandatory.forEach(element => {
      const entity = new DocumentTypeBusinessCategory(
        0,
        true, false,
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
          0,
          false, true,
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
        let vendorTypes = [];
        if (this.documentType.mandatoryForCompany) {
          vendorTypes.push('C')
        }
        if (this.documentType.mandatoryForProfessional) {
          vendorTypes.push('P')
        }
        this.documentType.vendorTypes = vendorTypes;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentTypeBusinessCategoryService()
      .retrieve()
      .then(res => {
        this.documentTypeBusinessCategories = res.data;
      });
  }
}
