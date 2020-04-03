export interface ICompanyFunctionary {
  id?: number;
  name?: string;
  position?: string;
  phone?: string;
  email?: string;
  vendorId?: number;
}

export class CompanyFunctionary implements ICompanyFunctionary {
  constructor(
    public id?: number,
    public name?: string,
    public position?: string,
    public phone?: string,
    public email?: string,
    public vendorId?: number
  ) {}
}
