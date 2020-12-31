package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MProductCatalog.
 */
@Entity
@Table(name = "m_product_catalog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MProductCatalog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Size(max = 100)
    @Column(name = "short_description", length = 100, nullable = false)
    private String shortDescription;

    @Column(name = "height")
    private Double height;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    /**
     * Next action for the document.
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "document_action", length = 10, nullable = false)
    private String documentAction;

    /**
     * Current document status.
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "document_status", length = 10, nullable = false)
    private String documentStatus;

    /**
     * Document is rejected if approved = false and processed = true.
     */
    @Column(name = "approved")
    private Boolean approved;

    /**
     * Determine whether the document is already processed or not.
     */
    @Column(name = "processed")
    private Boolean processed;

    /**
     * Mandatory if the document is rejected.
     */
    @Size(max = 50)
    @Column(name = "rejected_reason", length = 50)
    private String rejectedReason;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private ADOrganization adOrganizationId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private CDocumentType cDocumentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private CCurrency cCurrency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private CUnitOfMeasure cUom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private CVendor cVendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mProductCatalogs", allowSetters = true)
    private CProduct mProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MProductCatalog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MProductCatalog description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public MProductCatalog shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getHeight() {
        return height;
    }

    public MProductCatalog height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public MProductCatalog length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public MProductCatalog width(Double width) {
        this.width = width;
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public MProductCatalog weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MProductCatalog price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public MProductCatalog expiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public MProductCatalog documentAction(String documentAction) {
        this.documentAction = documentAction;
        return this;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public MProductCatalog documentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public MProductCatalog approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public MProductCatalog processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public MProductCatalog rejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public UUID getUid() {
        return uid;
    }

    public MProductCatalog uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MProductCatalog active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganizationId() {
        return adOrganizationId;
    }

    public MProductCatalog adOrganizationId(ADOrganization aDOrganization) {
        this.adOrganizationId = aDOrganization;
        return this;
    }

    public void setAdOrganizationId(ADOrganization aDOrganization) {
        this.adOrganizationId = aDOrganization;
    }

    public CDocumentType getCDocumentType() {
        return cDocumentType;
    }

    public MProductCatalog cDocumentType(CDocumentType cDocumentType) {
        this.cDocumentType = cDocumentType;
        return this;
    }

    public void setCDocumentType(CDocumentType cDocumentType) {
        this.cDocumentType = cDocumentType;
    }

    public CCurrency getCCurrency() {
        return cCurrency;
    }

    public MProductCatalog cCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
        return this;
    }

    public void setCCurrency(CCurrency cCurrency) {
        this.cCurrency = cCurrency;
    }

    public CUnitOfMeasure getCUom() {
        return cUom;
    }

    public MProductCatalog cUom(CUnitOfMeasure cUnitOfMeasure) {
        this.cUom = cUnitOfMeasure;
        return this;
    }

    public void setCUom(CUnitOfMeasure cUnitOfMeasure) {
        this.cUom = cUnitOfMeasure;
    }

    public CVendor getCVendor() {
        return cVendor;
    }

    public MProductCatalog cVendor(CVendor cVendor) {
        this.cVendor = cVendor;
        return this;
    }

    public void setCVendor(CVendor cVendor) {
        this.cVendor = cVendor;
    }

    public CProduct getMProduct() {
        return mProduct;
    }

    public MProductCatalog mProduct(CProduct cProduct) {
        this.mProduct = cProduct;
        return this;
    }

    public void setMProduct(CProduct cProduct) {
        this.mProduct = cProduct;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MProductCatalog)) {
            return false;
        }
        return id != null && id.equals(((MProductCatalog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MProductCatalog{" +
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
            "}";
    }
}
