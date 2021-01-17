export interface ICProduct {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  type?: string;
  uid?: string;
  active?: boolean;
  adOrganizationId?: number;
  productClassificationId?: number;
  productCategoryId?: number;
  productSubCategoryId?: number;
  assetAcctId?: number;
  expenseAcctId?: number;
  uomId?: number;
}

export class CProduct implements ICProduct {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public type?: string,
    public uid?: string,
    public active?: boolean,
    public adOrganizationId?: number,
    public productClassificationId?: number,
    public productCategoryId?: number,
    public productSubCategoryId?: number,
    public assetAcctId?: number,
    public expenseAcctId?: number,
    public uomId?: number
  ) {
    this.active = this.active || false;
  }
}
