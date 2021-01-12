package com.bhp.opusb.service.dto.marketplace;

import java.math.BigDecimal;
import java.util.List;

import com.bhp.opusb.domain.enumeration.CAttachmentType;
import com.bhp.opusb.domain.enumeration.CGalleryItemType;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.dto.CGalleryDTO;
import com.bhp.opusb.service.dto.CGalleryItemDTO;
import com.bhp.opusb.service.dto.MProductCatalogDTO;

public class BhinnekaItemDetailDTO implements MarketplaceCatalogItemMapper {
  private String name;
  private String description;
  private boolean isPreOrder;
  private Integer durationPreOrder;
  private String productWarranty;
  private boolean isSold;
  private Brand brand;
  private Image image;
  private Media media;
  private List<Category> category;
  private List<ItemVariant> variants;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPreOrder() {
    return isPreOrder;
  }

  public void setPreOrder(boolean isPreOrder) {
    this.isPreOrder = isPreOrder;
  }

  public Integer getDurationPreOrder() {
    return durationPreOrder;
  }

  public void setDurationPreOrder(Integer durationPreOrder) {
    this.durationPreOrder = durationPreOrder;
  }

  public String getProductWarranty() {
    return productWarranty;
  }

  public void setProductWarranty(String productWarranty) {
    this.productWarranty = productWarranty;
  }

  public boolean isSold() {
    return isSold;
  }

  public void setSold(boolean isSold) {
    this.isSold = isSold;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

  public List<Category> getCategory() {
    return category;
  }

  public void setCategory(List<Category> category) {
    this.category = category;
  }

  public List<ItemVariant> getVariants() {
    return variants;
  }

  public void setVariants(List<ItemVariant> variants) {
    this.variants = variants;
  }

  public static class Brand {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class Image {
    private String small;
    private String thumbnail;

    public String getSmall() {
      return small;
    }

    public void setSmall(String small) {
      this.small = small;
    }

    public String getThumbnail() {
      return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
    }
  }

  public static class Media {
    private List<MediaVariant> variant;

    public List<MediaVariant> getVariant() {
      return variant;
    }

    public void setVariant(List<MediaVariant> variant) {
      this.variant = variant;
    }
  }

  public static class MediaVariant {
    private String name;
    private String small;
    private String thumbnail;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSmall() {
      return small;
    }

    public void setSmall(String small) {
      this.small = small;
    }

    public String getThumbnail() {
      return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
    }
  }

  public static class Category {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class ItemVariant {
    private String skuInternal;
    private String fullName;
    private int stockAvailable;
    private int stockMinimum;
    private BigDecimal priceAfterTax;
    private BigDecimal priceNormal;
    private BigDecimal sellingPrice;
    private BigDecimal activePrice;
    private boolean isActive;

    public String getSkuInternal() {
      return skuInternal;
    }

    public void setSkuInternal(String skuInternal) {
      this.skuInternal = skuInternal;
    }

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public int getStockAvailable() {
      return stockAvailable;
    }

    public void setStockAvailable(int stockAvailable) {
      this.stockAvailable = stockAvailable;
    }

    public int getStockMinimum() {
      return stockMinimum;
    }

    public void setStockMinimum(int stockMinimum) {
      this.stockMinimum = stockMinimum;
    }

    public BigDecimal getPriceAfterTax() {
      return priceAfterTax;
    }

    public void setPriceAfterTax(BigDecimal priceAfterTax) {
      this.priceAfterTax = priceAfterTax;
    }

    public BigDecimal getPriceNormal() {
      return priceNormal;
    }

    public void setPriceNormal(BigDecimal priceNormal) {
      this.priceNormal = priceNormal;
    }

    public BigDecimal getSellingPrice() {
      return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
      this.sellingPrice = sellingPrice;
    }

    public BigDecimal getActivePrice() {
      return activePrice;
    }

    public void setActivePrice(BigDecimal activePrice) {
      this.activePrice = activePrice;
    }

    public boolean isActive() {
      return isActive;
    }

    public void setActive(boolean isActive) {
      this.isActive = isActive;
    }
  }

  @Override
  public MProductCatalogDTO toDto() {
    final CGalleryDTO gallery = buildGallery();
    MProductCatalogDTO dto = new MProductCatalogDTO();

    if (!getVariants().isEmpty()) {
      ItemVariant variant = getVariants().get(0);
      dto.setName(variant.getFullName());
      dto.setPrice(variant.getSellingPrice());
    }

    dto.setCGallery(gallery);
    dto.setMBrandName(getBrand().getName());
    dto.setShortDescription(getDescription());
    dto.setCVendorName("Bhinneka");

    return null;
  }

  private CGalleryDTO buildGallery() {
    CGalleryDTO gallery = new CGalleryDTO();

    // Thumbnail.
    final CGalleryItemDTO itemPreview = new CGalleryItemDTO();
    final CAttachmentDTO thumbnail = new CAttachmentDTO();
    thumbnail.setType(CAttachmentType.REMOTE);
    thumbnail.setImageSmall(getImage().getSmall());
    thumbnail.setImageMedium(getImage().getThumbnail());
    thumbnail.setImageLarge(getImage().getThumbnail());
    itemPreview.setCAttachment(thumbnail);
    itemPreview.setSequence(0);
    itemPreview.setPreview(true);
    gallery.addCGalleryItem(itemPreview);

    // Carousel
    if (getMedia() != null && getMedia().getVariant() != null) {
      int sequence = 0;
      for (MediaVariant variant : getMedia().getVariant()) {
        final CGalleryItemDTO item = new CGalleryItemDTO();
        final CAttachmentDTO image = new CAttachmentDTO();
        image.setFileName(variant.getThumbnail());
        image.setImageSmall(variant.getSmall());
        image.setImageMedium(variant.getThumbnail());
        image.setImageLarge(variant.getThumbnail());
        image.setType(CAttachmentType.REMOTE);
        item.setCAttachment(image);
        item.setSequence(++sequence);
        item.setType(CGalleryItemType.IMAGE);
        gallery.addCGalleryItem(item);
      }
    }

    return gallery;
  }
}
