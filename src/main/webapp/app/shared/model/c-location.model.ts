export interface ICLocation {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  taxInvoiceAddress?: boolean;
  active?: boolean;
  cityId?: number;
}

export class CLocation implements ICLocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public taxInvoiceAddress?: boolean,
    public active?: boolean,
    public cityId?: number
  ) {
    this.taxInvoiceAddress = this.taxInvoiceAddress || false;
    this.active = this.active || false;
  }
}
