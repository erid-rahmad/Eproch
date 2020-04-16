import { IDocumentType } from '@/shared/model/document-type.model';
import { IBusinessCategory } from '@/shared/model/business-category.model';

export interface IDocumentTypeBusinessCategory {
  id?: number;
  mandatory?: boolean;
  additional?: boolean;
  documentType?: IDocumentType;
  businessCategory?: IBusinessCategory;
  removed?: boolean;
}

export class DocumentTypeBusinessCategory implements IDocumentTypeBusinessCategory {
  constructor(
    public id?: number,
    public mandatory?: boolean,
    public additional?: boolean,
    public documentType?: IDocumentType,
    public businessCategory?: IBusinessCategory,
    public removed?: boolean
  ) {
    this.mandatory = this.mandatory || false;
    this.additional = this.additional || false;
  }
}
