import { ICGallery } from './c-gallery.model';

export interface IMProductCatalog {
  id?: number;
  name?: string;
  description?: string;
  shortDescription?: string;
  height?: number;
  length?: number;
  width?: number;
  weight?: number;
  price?: number;
  expiredDate?: Date;
  documentAction?: string;
  documentStatus?: string;
  approved?: boolean;
  processed?: boolean;
  rejectedReason?: string;
  uid?: string;
  active?: boolean;
  cGallery?: ICGallery;
  cGalleryId?: number;
  adOrganizationId?: number;
  cDocumentTypeId?: number;
  cCurrencyId?: number;
  cCurrencyName?: string;
  cUomId?: number;
  cUomName?: string;
  cVendorId?: number;
  cVendorName?: string;
  mBrandId?: number;
  mBrandName?: string;
  mProductId?: number;
  mProductName?: string;
}

export class MProductCatalog implements IMProductCatalog {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public shortDescription?: string,
    public height?: number,
    public length?: number,
    public width?: number,
    public weight?: number,
    public price?: number,
    public expiredDate?: Date,
    public documentAction?: string,
    public documentStatus?: string,
    public approved?: boolean,
    public processed?: boolean,
    public rejectedReason?: string,
    public uid?: string,
    public active?: boolean,
    public cGallery?: ICGallery,
    public cGalleryId?: number,
    public adOrganizationId?: number,
    public cDocumentTypeId?: number,
    public cCurrencyId?: number,
    public cCurrencyName?: string,
    public cUomId?: number,
    public cUomName?: string,
    public cVendorId?: number,
    public cVendorName?: string,
    public mBrandId?: number,
    public mBrandName?: string,
    public mProductId?: number,
    public mProductName?: string
  ) {
    this.approved = this.approved || false;
    this.processed = this.processed || false;
    this.active = this.active || false;
  }
}
