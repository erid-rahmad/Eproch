import { IBusinessCategory } from '@/shared/model/business-category.model';
import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import { IVendor } from '@/shared/model/vendor.model';

export interface IBusinessCategory {
  id?: number;
  name?: string;
  description?: string;
  businessCategories?: IBusinessCategory[];
  documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[];
  parentCategoryId?: number;
  vendors?: IVendor[];
}

export class BusinessCategory implements IBusinessCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public businessCategories?: IBusinessCategory[],
    public documentTypeBusinessCategories?: IDocumentTypeBusinessCategory[],
    public parentCategoryId?: number,
    public vendors?: IVendor[]
  ) {}
}
