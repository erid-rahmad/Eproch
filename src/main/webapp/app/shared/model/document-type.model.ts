import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

export interface IDocumentType {
  id?: number;
  name?: string;
  description?: string;
  hasExpirationDate?: boolean;
  mandatoryBusinessCategories?: string;
  additionalBusinessCategories?: string;
  mandatoryForCompany?: boolean;
  mandatoryForProfessional?: boolean;
  additionalForCompany?: boolean;
  additionalForProfessional?: boolean;
  active?: boolean;
  vendorTypes?: Array<string>;
  documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[];
  mandatoryCategorySelections?: Array<any>;
  additionalCategorySelections?: Array<any>;
}

export class DocumentType implements IDocumentType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public hasExpirationDate?: boolean,
    public mandatoryBusinessCategories?: string,
    public additionalBusinessCategories?: string,
    public mandatoryForCompany?: boolean,
    public mandatoryForProfessional?: boolean,
    public additionalForCompany?: boolean,
    public additionalForProfessional?: boolean,
    public active?: boolean,
    public vendorTypes?: Array<string>,
    public documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[],
    public mandatoryCategorySelections?: Array<any>,
    public additionalCategorySelections?: Array<any>
  ) {
    this.hasExpirationDate = this.hasExpirationDate || false;
    this.mandatoryBusinessCategories = this.mandatoryBusinessCategories || 'A';
    this.additionalBusinessCategories = this.additionalBusinessCategories || 'A';
    this.mandatoryForCompany = this.mandatoryForCompany || false;
    this.mandatoryForProfessional = this.mandatoryForProfessional || false;
    this.additionalForCompany = this.additionalForCompany || false;
    this.additionalForProfessional = this.additionalForProfessional || false;
    this.active = this.active || true;
    this.vendorTypes = [];
    this.mandatoryCategorySelections = this.mandatoryCategorySelections || [];
    this.additionalCategorySelections = this.additionalCategorySelections || [];
  }
}
