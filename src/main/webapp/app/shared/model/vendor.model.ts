import { ICompanyFunctionary } from '@/shared/model/company-functionary.model';
import { ILocation } from '@/shared/model/location.model';
import { IPersonInCharge } from '@/shared/model/person-in-charge.model';
import { ISupportingDocument } from '@/shared/model/supporting-document.model';
import { IBusinessCategory } from '@/shared/model/business-category.model';

export const enum VendorType {
  COMPANY = 'COMPANY',
  PROFESSIONAL = 'PROFESSIONAL'
}

export const enum PaymentCategory {
  RED = 'RED',
  GREEN = 'GREEN'
}

export const enum VendorApprovalStatus {
  PENDING = 'PENDING',
  REJECTED = 'REJECTED',
  APPROVED = 'APPROVED'
}

export interface IVendor {
  id?: number;
  code?: string;
  name?: string;
  npwp?: number;
  branch?: boolean;
  email?: string;
  phone?: string;
  fax?: string;
  website?: string;
  type?: VendorType;
  paymentCategory?: PaymentCategory;
  approvalStatus?: VendorApprovalStatus;
  companyFunctionaries?: ICompanyFunctionary[];
  locations?: ILocation[];
  personInCharges?: IPersonInCharge[];
  supportingDocuments?: ISupportingDocument[];
  businessCategories?: IBusinessCategory[];
}

export class Vendor implements IVendor {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public npwp?: number,
    public branch?: boolean,
    public email?: string,
    public phone?: string,
    public fax?: string,
    public website?: string,
    public type?: VendorType,
    public paymentCategory?: PaymentCategory,
    public approvalStatus?: VendorApprovalStatus,
    public companyFunctionaries?: ICompanyFunctionary[],
    public locations?: ILocation[],
    public personInCharges?: IPersonInCharge[],
    public supportingDocuments?: ISupportingDocument[],
    public businessCategories?: IBusinessCategory[]
  ) {
    this.branch = this.branch || false;
  }
}
