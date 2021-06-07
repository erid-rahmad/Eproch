package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

/**
 * A MBiddingLine.
 */
@Entity
@Table(name = "m_bidding_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingLine extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "line_no")
    private Integer lineNo;

    @NotNull
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "ceiling_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal ceilingPrice;

    @Column(name = "total_ceiling_price", precision = 21, scale = 2)
    private BigDecimal totalCeilingPrice;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(unique = true)
    @JsonManagedReference
    private MBiddingSubItem subItem;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingLines")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingLines")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingLines")
    private CUnitOfMeasure uom;

    @Formula("(select mbnpl.price_negotiation from m_bid_nego_price_line mbnpl where mbnpl.bidding_line_id=id)")
    private BigDecimal proposedPrice;

    @Formula("(select mbnpl.total_negotiation_price from m_bid_nego_price_line mbnpl where mbnpl.bidding_line_id = id)")
    private BigDecimal totalPriceSubmission;

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

    public MBiddingLine lineNo(Integer lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MBiddingLine quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public MBiddingLine ceilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
        return this;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getTotalCeilingPrice() {
        return totalCeilingPrice;
    }

    public MBiddingLine totalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
        return this;
    }

    public void setTotalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public MBiddingLine deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemark() {
        return remark;
    }

    public MBiddingLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBiddingSubItem getSubItem() {
        return subItem;
    }

    public MBiddingLine subItem(MBiddingSubItem mBiddingSubItem) {
        this.subItem = mBiddingSubItem;
        return this;
    }

    public void setSubItem(MBiddingSubItem mBiddingSubItem) {
        this.subItem = mBiddingSubItem;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingLine bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MBiddingLine costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CProduct getProduct() {
        return product;
    }

    public MBiddingLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MBiddingLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MBiddingLine proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public BigDecimal getTotalPriceSubmission() {
        return totalPriceSubmission;
    }

    public MBiddingLine totalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
        return this;
    }

    public void setTotalPriceSubmission(BigDecimal totalPriceSubmission) {
        this.totalPriceSubmission = totalPriceSubmission;
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
        if (!(o instanceof MBiddingLine)) {
            return false;
        }
        return id != null && id.equals(((MBiddingLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingLine{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", quantity=" + getQuantity() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", totalCeilingPrice=" + getTotalCeilingPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
