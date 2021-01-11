package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProductCatalog} entity.
 */
public class MProductCatalogDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 70)
    private String name;

    private String description;

    @NotNull
    @Size(max = 100)
    private String shortDescription;

    private Double height;

    private Double length;

    private Double width;

    private Double weight;

    private BigDecimal price;

    private LocalDate expiredDate;

    /**
     * Next action for the document.
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Next action for the document.", required = true)
    private String documentAction;

    /**
     * Current document status.
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Current document status.", required = true)
    private String documentStatus;

    /**
     * Document is rejected if approved = false and processed = true.
     */
    @ApiModelProperty(value = "Document is rejected if approved = false and processed = true.")
    private Boolean approved;

    /**
     * Determine whether the document is already processed or not.
     */
    @ApiModelProperty(value = "Determine whether the document is already processed or not.")
    private Boolean processed;

    /**
     * Mandatory if the document is rejected.
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Mandatory if the document is rejected.")
    private String rejectedReason;

    private UUID uid;

    private Boolean active;

    @JsonProperty("cGallery")
    private CGalleryDTO cGallery;

    @JsonProperty("cGalleryId")
    private Long cGalleryId;

    @JsonProperty("cGalleryName")
    private String cGalleryName;

    private Long adOrganizationId;
    private String adOrganizationName;

    @JsonProperty("cDocumentTypeId")
    private Long cDocumentTypeId;

    @JsonProperty("cDocumentTypeName")
    private String cDocumentTypeName;

    @JsonProperty("cCurrencyId")
    private Long cCurrencyId;

    @JsonProperty("cCurrencyName")
    private String cCurrencyName;

    @JsonProperty("cUomId")
    private Long cUomId;

    @JsonProperty("cUomName")
    private String cUomName;

    @JsonProperty("cVendorId")
    private Long cVendorId;

    @JsonProperty("cVendorName")
    private String cVendorName;

    @JsonProperty("mBrandId")
    private Long mBrandId;

    @JsonProperty("mBrandName")
    private String mBrandName;

    @JsonProperty("mProductId")
    private Long mProductId;

    @JsonProperty("mProductCode")
    private String mProductCode;

    @JsonProperty("mProductShortName")
    private String mProductShortName;

    @JsonProperty("mProductDescription")
    private String mProductDescription;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CGalleryDTO getCGallery() {
        return cGallery;
    }

    public void setCGallery(CGalleryDTO cGallery) {
        this.cGallery = cGallery;
    }

    public Long getCGalleryId() {
        return cGalleryId;
    }

    public void setCGalleryId(Long cGalleryId) {
        this.cGalleryId = cGalleryId;
    }

    public String getCGalleryName() {
        return cGalleryName;
    }

    public void setCGalleryName(String cGalleryName) {
        this.cGalleryName = cGalleryName;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getCDocumentTypeId() {
        return cDocumentTypeId;
    }

    public void setCDocumentTypeId(Long cDocumentTypeId) {
        this.cDocumentTypeId = cDocumentTypeId;
    }

    public String getCDocumentTypeName() {
        return cDocumentTypeName;
    }

    public void setCDocumentTypeName(String cDocumentTypeName) {
        this.cDocumentTypeName = cDocumentTypeName;
    }

    public Long getCCurrencyId() {
        return cCurrencyId;
    }

    public void setCCurrencyId(Long cCurrencyId) {
        this.cCurrencyId = cCurrencyId;
    }

    public String getCCurrencyName() {
        return cCurrencyName;
    }

    public void setCCurrencyName(String cCurrencyName) {
        this.cCurrencyName = cCurrencyName;
    }
    public Long getCUomId() {
        return cUomId;
    }

    public void setCUomId(Long cUnitOfMeasureId) {
        this.cUomId = cUnitOfMeasureId;
    }


    public String getCUomName() {
        return cUomName;
    }

    public void setCUomName(String cUomName) {
        this.cUomName = cUomName;
    }

    public Long getCVendorId() {
        return cVendorId;
    }

    public void setCVendorId(Long cVendorId) {
        this.cVendorId = cVendorId;
    }

    public String getCVendorName() {
        return cVendorName;
    }

    public void setCVendorName(String cVendorName) {
        this.cVendorName = cVendorName;
    }

    public Long getMBrandId() {
        return mBrandId;
    }

    public void setMBrandId(Long mBrandId) {
        this.mBrandId = mBrandId;
    }

    public String getMBrandName() {
        return mBrandName;
    }

    public void setMBrandName(String mBrandName) {
        this.mBrandName = mBrandName;
    }

    public Long getMProductId() {
        return mProductId;
    }

    public void setMProductId(Long cProductId) {
        this.mProductId = cProductId;
    }

    public String getMProductCode() {
        return mProductCode;
    }

    public void setMProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getMProductShortName() {
        return mProductShortName;
    }

    public void setMProductShortName(String mProductShortName) {
        this.mProductShortName = mProductShortName;
    }

    public String getMProductDescription() {
        return mProductDescription;
    }

    public void setMProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public String getMProductName() {
        return mProductCode + " - " + mProductShortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MProductCatalogDTO)) {
            return false;
        }

        return id != null && id.equals(((MProductCatalogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MProductCatalogDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", weight=" + getWeight() +
            ", price=" + getPrice() +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", cGalleryId=" + getCGalleryId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", cDocumentTypeId=" + getCDocumentTypeId() +
            ", cCurrencyId=" + getCCurrencyId() +
            ", cUomId=" + getCUomId() +
            ", cVendorId=" + getCVendorId() +
            ", mBrandId=" + getMBrandId() +
            ", mProductId=" + getMProductId() +
            "}";
    }
}
