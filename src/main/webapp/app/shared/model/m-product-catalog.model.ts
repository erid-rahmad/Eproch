import { IMProductPrice } from '@/shared/model/m-product-price.model';

export interface IMProductCatalog {
  id?: number;
  name?: string;
  description?: string;
  shortDescription?: string;
  sku?: string;
  height?: number;
  length?: number;
  width?: number;
  weight?: number;
  price?: number;
  expiredDate?: Date;
  preOrder?: boolean;
  preOrderDuration?: number;
  warranty?: string;
  sold?: boolean;
  stockAvailable?: number;
  documentAction?: string;
  documentStatus?: string;
  approved?: boolean;
  processed?: boolean;
  rejectedReason?: string;
  uid?: string;
  active?: boolean;
  cGalleryId?: number;
  mProductPrices?: IMProductPrice[];
  adOrganizationId?: number;
  cDocumentTypeId?: number;
  cCurrencyId?: number;
  cUomId?: number;
  cVendorId?: number;
  mBrandId?: number;
  mProductId?: number;
}

export class MProductCatalog implements IMProductCatalog {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public shortDescription?: string,
    public sku?: string,
    public height?: number,
    public length?: number,
    public width?: number,
    public weight?: number,
    public price?: number,
    public expiredDate?: Date,
    public preOrder?: boolean,
    public preOrderDuration?: number,
    public warranty?: string,
    public sold?: boolean,
    public stockAvailable?: number,
    public documentAction?: string,
    public documentStatus?: string,
    public approved?: boolean,
    public processed?: boolean,
    public rejectedReason?: string,
    public uid?: string,
    public active?: boolean,
    public cGalleryId?: number,
    public mProductPrices?: IMProductPrice[],
    public adOrganizationId?: number,
    public cDocumentTypeId?: number,
    public cCurrencyId?: number,
    public cUomId?: number,
    public cVendorId?: number,
    public mBrandId?: number,
    public mProductId?: number
  ) {
    this.preOrder = this.preOrder || false;
    this.sold = this.sold || false;
    this.approved = this.approved || false;
    this.processed = this.processed || false;
    this.active = this.active || false;
  }
}
