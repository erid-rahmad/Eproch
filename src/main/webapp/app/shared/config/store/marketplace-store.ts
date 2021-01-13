import { IProduct } from '@/core/application-dictionary/components/Form/marketplace/model/bhinneka-catalog.model';
import store from '@/shared/config/store';
import { idb } from "@/shared/config/store/idb";
import { CAttachment, CAttachmentType } from '@/shared/model/c-attachment.model';
import { CGalleryItem, CGalleryItemType } from '@/shared/model/c-gallery-item.model';
import { CGallery } from '@/shared/model/c-gallery.model';
import { MProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Action, getModule, Module, VuexModule, Mutation } from 'vuex-module-decorators';

export interface IMarketplaceState {
  cart: any;
  products: MProductCatalog[];
}

function buildProduct(item: IProduct) {
  const gallery: CGallery = buildGallery(item);
  const product: MProductCatalog = new MProductCatalog(
    null, item.name, item.description, item.description, 0, 0, 0, 1,
    item.variants[0]?.sellingPrice);

  product.mBrandName = item.brand?.name;
  product.cGallery = gallery;
  product.cVendorName = item.merchant.name;
  return product;
}

function buildGallery(item: IProduct) {
  const gallery: CGallery = new CGallery();
  const itemPreview: CGalleryItem = new CGalleryItem();
  const thumbnail: CAttachment = new CAttachment();

  gallery.cGalleryItems = [];
  thumbnail.type = CAttachmentType.REMOTE;
  thumbnail.imageSmall = item.image.thumbnail;
  thumbnail.imageMedium = item.image.small;
  thumbnail.imageLarge = item.image.large;
  itemPreview.cAttachment = thumbnail;
  itemPreview.sequence = 0;
  itemPreview.preview = true;
  gallery.cGalleryItems.push(itemPreview);

  let sequence = 0;
  for (const media of item.media.variant) {
    const image: CAttachment = new CAttachment(
      null, CAttachmentType.REMOTE, media.thumbnail,
      media.thumbnail, media.small, media.large
    );

    const galleryItem: CGalleryItem = new CGalleryItem(
      null, CGalleryItemType.IMAGE, ++sequence, false, null, true, image
    );

    gallery.cGalleryItems.push(galleryItem);
  }

  return gallery;
}

@Module({ dynamic: true, store, name: 'marketplaceStore', namespaced: true })
class MarketplaceStore extends VuexModule implements IMarketplaceState {
  cart: any = {};
  products: MProductCatalog[] = [];

  @Mutation
  private ADD_PRODUCT(product: MProductCatalog) {
    this.products.push(product);
  }

  @Mutation
  private SET_PRODUCTS(products: MProductCatalog[]) {
    this.products = products;
  }

  @Action
  public async initCatalog() {   
    idb.transaction('r', [
      idb.brand, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia
    ], async () => {
      const products = (await idb.product.toArray())
        .map(async item => {
          let brand = null;
          if (item.brandId) {
            brand = await idb.brand.get(item.brandId);
          }

          const image = await idb.image.get(item.imageId);
          const merchant = await idb.merchant.get(item.merchantId);

          // console.log('brand: %O, image: %O, merchant: %O', brand, image, merchant);
          const productCategories = await idb.productCategory
            .where('productId').equals(item.id)
            .toArray();

          const categories = await idb.category.bulkGet(productCategories.map(c => c.categoryId));

          const variants = await idb.productVariant
            .where('productId').equals(item.id)
            .toArray();

          const variantMedia = await idb.productVariantMedia
            .where('productVariantId').anyOf(variants.map(v => v.id))
            .toArray();

          const product: IProduct = {
            brand,
            image,
            merchant,
            category: categories,
            name: item.name,
            description: item.description,
            isPreOrder: item.isPreOrder,
            durationPreOrder: item.durationPreOrder,
            isSold: item.isSold,
            productWarranty: item.productWarranty,
            media: {
              variant: variantMedia
            },
            variants
          };
          return product;
        });

      return Promise.all(products);
    })
    .then(result => {
      console.log('result no variants:', result.find(item => !item.variants.length));
      this.SET_PRODUCTS(result.map(product => buildProduct(product)));
    })
    .catch(e => {
      console.log('Failed getting marketplace catalog.', e);
    });
  }

  @Action
  public async addProduct(product: IProduct) {
    console.log('addProduct', product);
    idb.transaction('rw', [
      idb.brand, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia
    ], async () => {
      // Check for the existing product.
      const defaultVariant = product.variants[0];
      let productId = (await idb.productVariant
        .where('id').equals(defaultVariant.id)
        .first())?.productId;

      const isNew: boolean = !productId;

      // Save brand.
      const brandId = await idb.brand.put({
        id: product.brand.id,
        name: product.brand.name
      });
      console.log('Brand saved. id:', brandId);

      // Save image.
      let imageId = 0;
      if (isNew) {
        imageId = await idb.image.put({
          large: product.image.small.replace('small', 'large'),
          medium: product.image.small.replace('small', 'medium'),
          small: product.image.thumbnail,
          thumbnail: product.image.small
        });
        console.log('Image saved. id:', imageId);
      }

      // Save merchant.
      const merchantId = await idb.merchant.put({
        id: product.merchant.id,
        code: product.merchant.code,
        name: product.merchant.name
      });
      console.log('merchant saved. id:', merchantId);

      // Save product.
      if (isNew) {
        productId = await idb.product.put({
          name: product.name,
          description: product.description,
          isPreOrder: product.isPreOrder,
          isSold: product.isSold,
          durationPreOrder: product.durationPreOrder,
          brandId: brandId,
          imageId: imageId,
          merchantId: merchantId,
          productWarranty: product.productWarranty
        });
        console.log('product saved. id:', productId);
      }

      // Save categories.
      if (isNew) {
        const categories = product.category.map(cat => {
          return { id: cat.id, name: cat.name }
        });
        const categoryIds = await idb.category.bulkPut(categories, { allKeys: true });

        idb.productCategory.bulkPut(
          categoryIds.map(categoryId => {
            return { productId, categoryId };
          })
        );
        console.log('Categories saved. ids:', categoryIds);
      }
      
      // Save product variants.
      const variants = product.variants.map(variant => {
        return {
          productId,
          id: variant.id,
          fullName: variant.fullName,
          name: variant.name,
          activePrice: variant.activePrice,
          priceAfterTax: variant.priceAfterTax,
          priceNormal: variant.priceNormal,
          sellingPrice: variant.sellingPrice,
          skuInternal: variant.skuInternal,
          stockAvailable: variant.stockAvailable,
          stockMinimum: variant.stockMinimum,
          isActive: variant.isActive,
          nameVariantMedia: variant.nameVariantMedia
        };
      });

      idb.productVariant.bulkPut(variants);

      if (isNew) {
        const variantMedias = product.media.variant.map((mediaVariant, idx) => {
          return {
            name: mediaVariant.name,
            large: mediaVariant.small.replace('small', 'large'),
            medium: mediaVariant.small.replace('small', 'medium'),
            small: mediaVariant.thumbnail,
            thumbnail: mediaVariant.small,
            productVariantId: variants.find(v => v.nameVariantMedia === mediaVariant.name)?.id
          };
        });

        idb.productVariantMedia.bulkPut(variantMedias);
      }

      return product;
    })
    .then(result => {
      this.ADD_PRODUCT(buildProduct(result));
    })
    .catch(e => {
      console.log('Failed when saving the new product.', e);
    });
  }

  @Action
  public async updateProduct(product: Record<string, any>) {
    console.log('updateProduct is not yet implemented');
  }

  @Action
  public async removeProduct(id) {
    console.log('removeProduct is not yet implemented');
  }

  @Action
  public async clearCatalog() {
    idb.transaction('rw', [
      idb.brand, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia
    ], async () => {
      idb.productVariantMedia.clear();
      idb.productVariant.clear();
      idb.productCategory.clear();
      idb.product.clear();
      idb.merchant.clear();
      idb.image.clear();
      idb.category.clear();
      idb.brand.clear();
    });
  }
}

export const MarketplaceStoreModule = getModule(MarketplaceStore)
