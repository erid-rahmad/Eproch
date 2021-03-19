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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MRequisitionLine.
 */
@Entity
@Table(name = "m_requisition_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRequisitionLine extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "document_date")
    private LocalDate documentDate;

    @Column(name = "date_promised")
    private LocalDate datePromised;

    @Column(name = "date_required")
    private LocalDate dateRequired;

    @NotNull
    @Column(name = "requisition_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal requisitionAmount;

    @NotNull
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "quantity_ordered", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantityOrdered;

    @NotNull
    @Column(name = "quantity_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantityBalance;

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
    @JsonIgnoreProperties("mRequisitionLines")
    private MRequisition requisition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRequisitionLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRequisitionLines")
    private CProduct product;

    @ManyToOne
    @JsonIgnoreProperties("mRequisitionLines")
    private CWarehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("mRequisitionLines")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mRequisitionLines")
    private CUnitOfMeasure uom;

    @ManyToOne
    @JsonIgnoreProperties("mRequisitionLines")
    private CVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public MRequisitionLine lineNo(Integer lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public MRequisitionLine documentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getDatePromised() {
        return datePromised;
    }

    public MRequisitionLine datePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
        return this;
    }

    public void setDatePromised(LocalDate datePromised) {
        this.datePromised = datePromised;
    }

    public LocalDate getDateRequired() {
        return dateRequired;
    }

    public MRequisitionLine dateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
        return this;
    }

    public void setDateRequired(LocalDate dateRequired) {
        this.dateRequired = dateRequired;
    }

    public BigDecimal getRequisitionAmount() {
        return requisitionAmount;
    }

    public MRequisitionLine requisitionAmount(BigDecimal requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
        return this;
    }

    public void setRequisitionAmount(BigDecimal requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MRequisitionLine quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantityOrdered() {
        return quantityOrdered;
    }

    public MRequisitionLine quantityOrdered(BigDecimal quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
        return this;
    }

    public void setQuantityOrdered(BigDecimal quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getQuantityBalance() {
        return quantityBalance;
    }

    public MRequisitionLine quantityBalance(BigDecimal quantityBalance) {
        this.quantityBalance = quantityBalance;
        return this;
    }

    public void setQuantityBalance(BigDecimal quantityBalance) {
        this.quantityBalance = quantityBalance;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public MRequisitionLine unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public MRequisitionLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UUID getUid() {
        return uid;
    }

    public MRequisitionLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MRequisitionLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MRequisition getRequisition() {
        return requisition;
    }

    public MRequisitionLine requisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
        return this;
    }

    public void setRequisition(MRequisition mRequisition) {
        this.requisition = mRequisition;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MRequisitionLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MRequisitionLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CWarehouse getWarehouse() {
        return warehouse;
    }

    public MRequisitionLine warehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
        return this;
    }

    public void setWarehouse(CWarehouse cWarehouse) {
        this.warehouse = cWarehouse;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MRequisitionLine costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MRequisitionLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MRequisitionLine vendor(CVendor cVendor) {
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
        if (!(o instanceof MRequisitionLine)) {
            return false;
        }
        return id != null && id.equals(((MRequisitionLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MRequisitionLine{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", documentDate='" + getDocumentDate() + "'" +
            ", datePromised='" + getDatePromised() + "'" +
            ", dateRequired='" + getDateRequired() + "'" +
            ", requisitionAmount=" + getRequisitionAmount() +
            ", quantity=" + getQuantity() +
            ", quantityOrdered=" + getQuantityOrdered() +
            ", quantityBalance=" + getQuantityBalance() +
            ", unitPrice=" + getUnitPrice() +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
