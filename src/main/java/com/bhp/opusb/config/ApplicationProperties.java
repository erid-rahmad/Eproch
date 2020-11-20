package com.bhp.opusb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Opus Web App.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final Attachment attachment = new Attachment();

  private long defaultOrganizationId = 1L;
  private long defaultCostCenterId = 1L;
  private long defaultElementValueId = 1L;
  private String defaultProductType = "I";
  private long defaultProductClassificationId = 1L;
  private long defaultProductCategoryId = 1L;
  private long defaultProductAssetAccountId = 1L;
  private long defaultProductExpenseAccountId = 1L;

  public Attachment getAttachment() {
    return attachment;
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
}
