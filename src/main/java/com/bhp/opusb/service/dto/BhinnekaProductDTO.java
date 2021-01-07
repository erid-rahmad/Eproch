package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class BhinnekaProductDTO {
  private String sku;
  private String name;
  private String model;
  private String status;
  private String categoryId;
  private String categoryName;
  private List<BhinnekaCategoryDTO> categoryStructure;
  private Map<String, String> categoryBreadcrumbs;
  private String brandId;
  private String brandName;
  private String description;
  private List<String> inTheBox;
  private String realLength;
  private String realWidth;
  private String realHeight;
  private String realWeight;
  private List<BhinnekaProductSpecificationDTO> specs;
  private String richContent;
  private BigDecimal normalPrice;
  private BigDecimal specialPrice;
  private String url;
  private String thumbnailURL160x160;
  private String thumbnailURL60x60;
  private List<String> imageURLs300x300;
  private List<String> imageURLs500x500;
  private List<String> imageURLs1000x1000;
  private boolean isOutOfStock;
  private boolean isCallProduct;
  private boolean isNormalProduct;
  private boolean isCampaignActive;
  private ZonedDateTime createdAt;
  private ZonedDateTime modifiedAt;
  private long totalStock;
  private long offerStock;
  
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public List<BhinnekaCategoryDTO> getCategoryStructure() {
    return categoryStructure;
  }

  public void setCategoryStructure(List<BhinnekaCategoryDTO> categoryStructure) {
    this.categoryStructure = categoryStructure;
  }

  public Map<String, String> getCategoryBreadcrumbs() {
    return categoryBreadcrumbs;
  }

  public void setCategoryBreadcrumbs(Map<String, String> categoryBreadcrumbs) {
    this.categoryBreadcrumbs = categoryBreadcrumbs;
  }

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getInTheBox() {
    return inTheBox;
  }

  public void setInTheBox(List<String> inTheBox) {
    this.inTheBox = inTheBox;
  }

  public String getRealLength() {
    return realLength;
  }

  public void setRealLength(String realLength) {
    this.realLength = realLength;
  }

  public String getRealWidth() {
    return realWidth;
  }

  public void setRealWidth(String realWidth) {
    this.realWidth = realWidth;
  }

  public String getRealHeight() {
    return realHeight;
  }

  public void setRealHeight(String realHeight) {
    this.realHeight = realHeight;
  }

  public String getRealWeight() {
    return realWeight;
  }

  public void setRealWeight(String realWeight) {
    this.realWeight = realWeight;
  }

  public List<BhinnekaProductSpecificationDTO> getSpecs() {
    return specs;
  }

  public void setSpecs(List<BhinnekaProductSpecificationDTO> specs) {
    this.specs = specs;
  }

  public String getRichContent() {
    return richContent;
  }

  public void setRichContent(String richContent) {
    this.richContent = richContent;
  }

  public BigDecimal getNormalPrice() {
    return normalPrice;
  }

  public void setNormalPrice(BigDecimal normalPrice) {
    this.normalPrice = normalPrice;
  }

  public BigDecimal getSpecialPrice() {
    return specialPrice;
  }

  public void setSpecialPrice(BigDecimal specialPrice) {
    this.specialPrice = specialPrice;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getThumbnailURL160x160() {
    return thumbnailURL160x160;
  }

  public void setThumbnailURL160x160(String thumbnailURL160x160) {
    this.thumbnailURL160x160 = thumbnailURL160x160;
  }

  public String getThumbnailURL60x60() {
    return thumbnailURL60x60;
  }

  public void setThumbnailURL60x60(String thumbnailURL60x60) {
    this.thumbnailURL60x60 = thumbnailURL60x60;
  }

  public List<String> getImageURLs300x300() {
    return imageURLs300x300;
  }

  public void setImageURLs300x300(List<String> imageURLs300x300) {
    this.imageURLs300x300 = imageURLs300x300;
  }

  public List<String> getImageURLs500x500() {
    return imageURLs500x500;
  }

  public void setImageURLs500x500(List<String> imageURLs500x500) {
    this.imageURLs500x500 = imageURLs500x500;
  }

  public List<String> getImageURLs1000x1000() {
    return imageURLs1000x1000;
  }

  public void setImageURLs1000x1000(List<String> imageURLs1000x1000) {
    this.imageURLs1000x1000 = imageURLs1000x1000;
  }

  public boolean isOutOfStock() {
    return isOutOfStock;
  }

  public void setOutOfStock(boolean isOutOfStock) {
    this.isOutOfStock = isOutOfStock;
  }

  public boolean isCallProduct() {
    return isCallProduct;
  }

  public void setCallProduct(boolean isCallProduct) {
    this.isCallProduct = isCallProduct;
  }

  public boolean isNormalProduct() {
    return isNormalProduct;
  }

  public void setNormalProduct(boolean isNormalProduct) {
    this.isNormalProduct = isNormalProduct;
  }

  public boolean isCampaignActive() {
    return isCampaignActive;
  }

  public void setCampaignActive(boolean isCampaignActive) {
    this.isCampaignActive = isCampaignActive;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ZonedDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(ZonedDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public long getTotalStock() {
    return totalStock;
  }

  public void setTotalStock(long totalStock) {
    this.totalStock = totalStock;
  }

  public long getOfferStock() {
    return offerStock;
  }

  public void setOfferStock(long offerStock) {
    this.offerStock = offerStock;
  }

  public static class BhinnekaCategoryDTO {
    private int currentLevel;
    private String name;
    private String id;
    private String oldId;
    private String parentId;
    private String slug;

    public int getCurrentLevel() {
      return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
      this.currentLevel = currentLevel;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getOldId() {
      return oldId;
    }

    public void setOldId(String oldId) {
      this.oldId = oldId;
    }

    public String getParentId() {
      return parentId;
    }

    public void setParentId(String parentId) {
      this.parentId = parentId;
    }

    public String getSlug() {
      return slug;
    }

    public void setSlug(String slug) {
      this.slug = slug;
    }
  }

  public static class BhinnekaProductSpecificationDTO {
    private String name;
    private String id;
    private long oldId;
    private String value;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public long getOldId() {
      return oldId;
    }

    public void setOldId(long oldId) {
      this.oldId = oldId;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
