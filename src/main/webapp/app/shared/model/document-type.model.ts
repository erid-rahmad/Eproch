import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

export interface IDocumentType {
  id?: number;
  name?: string;
  description?: string;
  hasExpirationDate?: boolean;
  forCompany?: boolean;
  forProfessional?: boolean;
  active?: boolean;
  documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[];
}

export class DocumentType implements IDocumentType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public hasExpirationDate?: boolean,
    public forCompany?: boolean,
    public forProfessional?: boolean,
    public active?: boolean,
    public documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[]
  ) {
    this.hasExpirationDate = this.hasExpirationDate || false;
    this.forCompany = this.forCompany || false;
    this.forProfessional = this.forProfessional || false;
    this.active = this.active || false;
  }
}
