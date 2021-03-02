package com.bhp.opusb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Opus Web App.
 * <p>
 * Properties are configured in the {@code application.yml} file. nk
 * o.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final Attachment attachment = new Attachment();
  private final Integration integration = new Integration();

  private long defaultOrganizationId = 1L;
  private long defaultCostCenterId = 1L;
  private long defaultElementValueId = 1L;
  private String defaultProductType = "I";
  private long defaultProductClassificationId = 1L;
  private long defaultProductCategoryId = 1L;
  private long defaultProductSubCategoryId = 1L;
  private long defaultProductAssetAccountId = 1L;
  private long defaultProductExpenseAccountId = 1L;

  public Attachment getAttachment() {
    return attachment;
  }

  public Integration getIntegration() {
    return integration;
  }

  public long getDefaultOrganizationId() {
    return defaultOrganizationId;
  }

  public void setDefaultOrganizationId(long defaultOrganizationId) {
    this.defaultOrganizationId = defaultOrganizationId;
  }

  public long getDefaultCostCenterId() {
    return defaultCostCenterId;
  }

  public void setDefaultCostCenterId(long defaultCostCenterId) {
    this.defaultCostCenterId = defaultCostCenterId;
  }

  public long getDefaultElementValueId() {
    return defaultElementValueId;
  }

  public void setDefaultElementValueId(long defaultElementValueId) {
    this.defaultElementValueId = defaultElementValueId;
  }

  public String getDefaultProductType() {
    return defaultProductType;
  }

  public void setDefaultProductType(String defaultProductType) {
    this.defaultProductType = defaultProductType;
  }

  public long getDefaultProductClassificationId() {
    return defaultProductClassificationId;
  }

  public void setDefaultProductClassificationId(long defaultProductClassificationId) {
    this.defaultProductClassificationId = defaultProductClassificationId;
  }

  public long getDefaultProductCategoryId() {
    return defaultProductCategoryId;
  }

  public void setDefaultProductCategoryId(long defaultProductCategoryId) {
    this.defaultProductCategoryId = defaultProductCategoryId;
  }

  public long getDefaultProductSubCategoryId() {
    return defaultProductSubCategoryId;
  }

  public void setDefaultProductSubCategoryId(long defaultProductSubCategoryId) {
    this.defaultProductSubCategoryId = defaultProductSubCategoryId;
  }

  public long getDefaultProductAssetAccountId() {
    return defaultProductAssetAccountId;
  }

  public void setDefaultProductAssetAccountId(long defaultProductAssetAccountId) {
    this.defaultProductAssetAccountId = defaultProductAssetAccountId;
  }

  public long getDefaultProductExpenseAccountId() {
    return defaultProductExpenseAccountId;
  }

  public void setDefaultProductExpenseAccountId(long defaultProductExpenseAccountId) {
    this.defaultProductExpenseAccountId = defaultProductExpenseAccountId;
  }

  public static final class Attachment {
    private String uploadDir = "/opt/data/attachment";

    public String getUploadDir() {
      return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
      this.uploadDir = uploadDir;
    }
  }

  public static final class Integration {
    private final Endpoint endpoint = new Endpoint();
    private final Marketplace marketplace = new Marketplace();

    public Endpoint getEndpoint() {
      return endpoint;
    }

    public Marketplace getMarketplace() {
      return marketplace;
    }
  }

  public static final class Endpoint {
    private String invoiceVerificationUrl;

    public String getInvoiceVerificationUrl() {
      return invoiceVerificationUrl;
    }

    public void setInvoiceVerificationUrl(String invoiceVerificationUrl) {
      this.invoiceVerificationUrl = invoiceVerificationUrl;
    }
  }

  public static final class Marketplace {
    private final Bhinneka bhinneka = new Bhinneka();

    public Bhinneka getBhinneka() {
      return bhinneka;
    }
  }

  public static final class Bhinneka {
    private String productFeedUrl;

    public String getProductFeedUrl() {
      return productFeedUrl;
    }

    public void setProductFeedUrl(String productFeedUrl) {
      this.productFeedUrl = productFeedUrl;
    }
  }
}
