
export interface IProduct {
  name: string;
  description: string;
  isPreOrder: boolean;
  durationPreOrder: number;
  productWarranty: string;
  isSold: boolean;

  brand: IBrand;
  category: ICategory[];
  image: IImage;
  media: IMedia;
  merchant: IMerchant;
  variants: IProductVariant[]
}

export interface IBrand {
  id: string;
  name: string;
}

export interface ICategory {
  id: string;
  name: string;
}

export interface IImage {
  large: string;
  medium: string;
  small: string;
  thumbnail: string;
}

export interface IMedia {
  variant: IMediaVariant[]
}

export interface IMediaVariant {
  name: string;
  large: string;
  medium: string;
  small: string;
  thumbnail: string;
}

export interface IMerchant {
  id: string;
  code: string;
  name: string;
}

export interface IProductVariant {
  id: string;
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
