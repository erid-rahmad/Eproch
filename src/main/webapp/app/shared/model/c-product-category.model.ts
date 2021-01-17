export interface ICProductCategory {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  uid?: string;
  active?: boolean;
  cProductCategories?: ICProductCategory[];
  adOrganizationId?: number;
  parentCategoryId?: number;
}

export class CProductCategory implements ICProductCategory {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public uid?: string,
    public active?: boolean,
    public cProductCategories?: ICProductCategory[],
    public adOrganizationId?: number,
    public parentCategoryId?: number
  ) {
    this.active = this.active || false;
  }
}
