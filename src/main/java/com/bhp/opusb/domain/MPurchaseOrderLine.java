package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MPurchaseOrderLine.
 */
@Entity
@Table(name = "m_purchase_order_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPurchaseOrderLine extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Column(name = "date_promised")
    private LocalDate datePromised;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @NotNull
    @Column(name = "order_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal orderAmount;

    @NotNull
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "unit_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "remark")
    private String remark;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private MPurchaseOrder purchaseOrder;

    @ManyToOne
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private MRequisition requisition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private CWarehouse warehouse;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private CUnitOfMeasure uom;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("mPurchaseOrderLines")
    private CVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxId() {
        return taxId;
    }

    public MPurchaseOrderLine taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public MPurchaseOrderLine documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public MPurchaseOrderLine datePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
        return this;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MPurchaseOrderLine dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public MPurchaseOrderLine orderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MPurchaseOrderLine quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public MPurchaseOrderLine unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public MPurchaseOrderLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UUID getUid() {
        return uid;
    }

    public MPurchaseOrderLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPurchaseOrderLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MPurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public MPurchaseOrderLine purchaseOrder(MPurchaseOrder mPurchaseOrder) {
        this.purchaseOrder = mPurchaseOrder;
        return this;
    }

    public void setPurchaseOrder(MPurchaseOrder mPurchaseOrder) {
        this.purchaseOrder = mPurchaseOrder;
    }

    public MRequisition getRequisition() {
        return requisition;
    }

    public MPurchaseOrderLine requisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
        return this;
    }

    public void setRequisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPurchaseOrderLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MPurchaseOrderLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CWarehouse getWarehouse() {
        return warehouse;
    }

    public MPurchaseOrderLine warehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
        return this;
    }

    public void setWarehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MPurchaseOrderLine costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MPurchaseOrderLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MPurchaseOrderLine vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPurchaseOrderLine)) {
            return false;
        }
        return id != null && id.equals(((MPurchaseOrderLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPurchaseOrderLine{" +
            "id=" + getId() +
            ", taxId='" + getTaxId() + "'" +
            ", documentDate='" + getDocumentDate() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
