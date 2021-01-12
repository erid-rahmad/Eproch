import { Dexie } from "dexie";

class EprocIDB extends Dexie {
  brand: Dexie.Table<IBrand, string>;
  category: Dexie.Table<ICategory, string>;
  image: Dexie.Table<IImage, number>;
  merchant: Dexie.Table<IMerchant, string>;
  product: Dexie.Table<IProduct, number>;
  productCategory: Dexie.Table<IProductCategory, number>;
  productVariant: Dexie.Table<IProductVariant, string>;
  productVariantMedia: Dexie.Table<IProductVariantMedia, number>;

  constructor() {
    super('bhpMarketplace');

    this.version(3).stores({
      brand: 'id, name',
      category: 'id, name',
      image: '++id',
      merchant: 'id, code, name',
      product: '++id, name, brandId, merchantId',
      productCategory: '++id, productId, categoryId',
      productVariant: 'id, productId, skuInternal, stockAvailable, sellingPrice, variantName',
      productVariantMedia: '++id, name, productVariantId'
    });
  }
}

interface IBrand {
  id: string;
  name: string;
}

interface ICategory {
  id: string;
  name: string;
}

interface IImage {
  // Auto-incremented id.
  id?: number;

  small: string;
  thumbnail: string;
}

interface IMerchant {
  id: string;
  code: string;
  name: string;
}

interface IProduct {
  // Auto-incremented id.
  id?: number;

  name: string;
  description: string;
  isPreOrder: boolean;
  durationPreOrder: number;
  productWarranty: string;
  isSold: boolean;

  // Foreign key to IBrand.
  brandId?: string;

  // Foreign key to IMerchant.
  merchantId: string;

  // Foreign key to IImage.
  imageId: number;
}

interface IProductCategory {
  // Auto-incremented id.
  id?: number;

  productId: number;
  categoryId: string;
}

interface IProductVariant {
  id: string;
  productId: number;
  name: string;
  fullName: string;
  skuInternal: string;
  stockAvailable: number;
  stockMinimum: number;
  priceAfterTax: number;
  priceNormal: number;
  isActive: boolean;
  sellingPrice: number;
  activePrice: number;
  nameVariantMedia: string;
}

interface IProductVariantMedia {
  // Auto-incremented id.
  id?: number;

  name: string;
  small: string;
  thumbnail: string;

  // Foreign key to IProductVariant.
  productVariantId: string;
}

export const idb = new EprocIDB();