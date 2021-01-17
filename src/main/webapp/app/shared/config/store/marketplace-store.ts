import { IProduct } from '@/core/application-dictionary/components/Form/marketplace/model/bhinneka-catalog.model';
import store from '@/shared/config/store';
import { idb } from "@/shared/config/store/idb";
import { CAttachment, CAttachmentType } from '@/shared/model/c-attachment.model';
import { CGalleryItem, CGalleryItemType } from '@/shared/model/c-gallery-item.model';
import { CGallery } from '@/shared/model/c-gallery.model';
import { MProductCatalog, IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Action, getModule, Module, VuexModule, Mutation } from 'vuex-module-decorators';
import { stat } from 'fs';

export interface IMarketplaceState {
  isShoppingCart: boolean;
  cart: any[];
  products: IMProductCatalog[];
}

const baseApiUrl = '/api/c-attachments/image/load-remote';

function buildProduct(item: IProduct) {
  const gallery: CGallery = buildGallery(item);
  const defaultVariant = item.variants.length && item.variants[0];
  const product: MProductCatalog = new MProductCatalog(
    item.id, item.name, item.description, item.description,
    defaultVariant?.skuInternal, 0, 0, 0, 1,
    defaultVariant?.sellingPrice, null, item.isPreOrder,
    item.durationPreOrder, item.productWarranty, item.isSold,
    defaultVariant?.stockAvailable, 'APV', 'DRF', true, true, null, null,
    true, gallery);

  product.mBrandName = item.brand?.name;
  product.cGallery = gallery;
  product.vendorId = item.merchant.id;
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
  thumbnail.imageLarge = item.image.medium;
  itemPreview.cAttachment = thumbnail;
  itemPreview.sequence = 0;
  itemPreview.preview = true;
  gallery.cGalleryItems.push(itemPreview);

  let sequence = 0;
  for (const media of item.media.variant) {
    const image: CAttachment = new CAttachment(
      null, CAttachmentType.REMOTE, media.thumbnail,
      media.thumbnail, media.small, media.medium
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
  isShoppingCart: boolean = false;
  cart: any[] = [];
  products: IMProductCatalog[] = [];

  get cartItemCount() {
    return 0;
  }

  get cartGrandTotal() {
    return 0;
  }

  get product() {
    return async (key: number) => {
      return await idb.transaction('r', [
        idb.brand, idb.category, idb.image, idb.merchant, idb.product,
        idb.productCategory, idb.productVariant, idb.productVariantMedia
      ], async () => {
        const item = await idb.product.get(key);
        let brand = null;
        if (item.brandId) {
          brand = await idb.brand.get(item.brandId);
        }

        const image = await idb.image.get(item.imageId);
        const merchant = await idb.merchant.get(item.merchantId);

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

        return {
          id: item.id,
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
        } as IProduct;
      });
    };
  }

  @Mutation
  private ADD_PRODUCT(product: MProductCatalog) {
    this.products.push(product);
  }

  @Mutation
  private ADD_TO_CART(item: any) {
    const product: IMProductCatalog = item.product;
    const vendorName = product.cVendorName;
    let itemsGroup = this.cart.find(group => group.vendorName === vendorName);

    if (!itemsGroup) {
      itemsGroup = {
        vendorName,
        items: []
      };

      this.cart.push(itemsGroup);
    }

    const items: any[] = itemsGroup.items;
    const cartItem = items.find(i => i.product.id === item.product.id);

    if (!cartItem) {
      itemsGroup.items.push(item);
    } else {
      cartItem.quantity = item.quantity;
    }
  }

  @Mutation
  private CLEAR_CART() {
    this.cart = [];
  }

  @Mutation
  private REMOVE_FROM_CART(item: any) {
    const product: IMProductCatalog = item.product;
    const vendorName = product.cVendorName;
    let itemsGroup = this.cart.find(group => group.vendorName === vendorName);

    if (!!itemsGroup) {
      const items: any[] = itemsGroup.items;
      items.splice(items.findIndex(i => i.product.id === item.product.id), 1);
    }
  }

  @Mutation
  private SET_PRODUCTS(products: MProductCatalog[]) {
    this.products = products;
  }

  @Mutation
  private SET_SHOPPING_CART_VIEW(state: boolean) {
    this.isShoppingCart = state;
  }

  @Action
  public async initCatalog() {   
    idb.transaction('r', [
      idb.brand, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia
    ], async () => {
      const products = (await idb.product.toArray())
        .map(async item => {
          return this.product(item.id);
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
  public async initCart() {
    idb.transaction('r', [
      idb.brand, idb.cartItem, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia
    ], async () => {
      (await idb.cartItem.toArray())
        .forEach(async item => {
          const product = await this.product(item.productId);
          this.ADD_TO_CART({
            id: item.id,
            product: buildProduct(product),
            quantity: item.quantity
          });
        });
    });
  }

  @Action
  public async addProduct(product: IProduct) {
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
      let brandId = null;
      if (product.brand) {
        brandId = await idb.brand.put({
          id: product.brand.id,
          name: product.brand.name
        });
      }

      // Save image.
      let imageId = 0;
      if (isNew) {
        const mediumSize = product.image.small.replace('thumbnail', 'medium');
        imageId = await idb.image.put({
          large: `${baseApiUrl}?url=${mediumSize}`,
          medium: `${baseApiUrl}?url=${mediumSize}`,
          small: `${baseApiUrl}?url=${product.image.small}`,
          thumbnail: `${baseApiUrl}?url=${product.image.thumbnail}`
        });
      }

      // Save merchant.
      const merchantId = await idb.merchant.put({
        id: product.merchant.id,
        code: product.merchant.code,
        name: product.merchant.name
      });

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
          const mediumSize = mediaVariant.small.replace('thumbnail', 'medium');
          return {
            name: mediaVariant.name,
            large: `${baseApiUrl}?url=${mediumSize}`,
            medium: `${baseApiUrl}?url=${mediumSize}`,
            small: `${baseApiUrl}?url=${product.image.small}`,
            thumbnail: `${baseApiUrl}?url=${product.image.thumbnail}`,
            productVariantId: variants.find(v => v.nameVariantMedia === mediaVariant.name)?.id
          };
        });

        idb.productVariantMedia.bulkPut(variantMedias);
      }

      product.id = productId;
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
  public async clearCatalog() {
    idb.transaction('rw', [
      idb.brand, idb.category, idb.image, idb.merchant, idb.product,
      idb.productCategory, idb.productVariant, idb.productVariantMedia,
      idb.cartItem
    ], () => {
      idb.productVariantMedia.clear();
      idb.productVariant.clear();
      idb.productCategory.clear();
      idb.product.clear();
      idb.merchant.clear();
      idb.image.clear();
      idb.category.clear();
      idb.brand.clear();
      idb.cartItem.clear().then(() => {
        this.CLEAR_CART();
      });
    });
  }

  @Action
  public async addToCart(item: any) {
    idb.transaction('rw', idb.cartItem, () => {
      const product = <IMProductCatalog>item.product;

      idb.cartItem.where('productId').equals(product.id).first()
        .then(cartItem => {
          let data: any;

          if (!cartItem) {
            data = {
              productId: product.id,
              vendorId: product.vendorId,
              vendorName: product.cVendorName,
              quantity: item.quantity
            };
          } else {
            data = cartItem;
            data.quantity = item.quantity;
          }

          idb.cartItem.put(data)
            .then(key => {
              item.id = key;
              this.ADD_TO_CART(item);
            });
        })
        .catch(() => {
          console.log('Failed to add item.', item);
        });
    });
  }

  @Action
  public async clearCart() {
    idb.transaction('rw', idb.cartItem, () => {
      idb.cartItem.clear()
        .then(() => {
          this.CLEAR_CART();
        });
    })
    .catch(() => {
      console.log('Failed to clear cart.');
    });
  }

  @Action
  public async removeFromCart(item: any) {
    idb.transaction('rw', idb.cartItem, () => {
      idb.cartItem.delete(item.id)
        .then(() => {
          this.REMOVE_FROM_CART(item);
        });
    })
    .catch(() => {
      console.log('Failed to delete item.', item);
    });
  }

  @Action
  public async setShoppingCartView(state: boolean) {
    this.SET_SHOPPING_CART_VIEW(state);
  }
}

export const MarketplaceStoreModule = getModule(MarketplaceStore)
