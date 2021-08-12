package com.bhp.opusb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A MContractLine.
 */
@Entity
@Table(name = "m_contract_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MContractLine extends AbstractAuditingEntity {

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

    @Column(name = "quantity_balance", precision = 21, scale = 2)
    private BigDecimal quantityBalance;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractLines")
    private MContract contract;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractLines")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractLines")
    private CProduct product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mContractLines")
    private CUnitOfMeasure uom;

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

    public MContractLine lineNo(Integer lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MContractLine quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantityBalance() {
        return quantityBalance;
    }

    public MContractLine quantityBalance(BigDecimal quantityBalance) {
        this.quantityBalance = quantityBalance;
        return this;
    }

    public void setQuantityBalance(BigDecimal quantityBalance) {
        this.quantityBalance = quantityBalance;
    }

    public BigDecimal getCeilingPrice() {
        return ceilingPrice;
    }

    public MContractLine ceilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
        return this;
    }

    public void setCeilingPrice(BigDecimal ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public BigDecimal getTotalCeilingPrice() {
        return totalCeilingPrice;
    }

    public MContractLine totalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
        return this;
    }

    public void setTotalCeilingPrice(BigDecimal totalCeilingPrice) {
        this.totalCeilingPrice = totalCeilingPrice;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public MContractLine deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemark() {
        return remark;
    }

    public MContractLine remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UUID getUid() {
        return uid;
    }

    public MContractLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MContractLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MContract getContract() {
        return contract;
    }

    public MContractLine contract(MContract mContract) {
        this.contract = mContract;
        return this;
    }

    public void setContract(MContract mContract) {
        this.contract = mContract;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MContractLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MContractLine costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public CProduct getProduct() {
        return product;
    }

    public MContractLine product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public CUnitOfMeasure getUom() {
        return uom;
    }

    public MContractLine uom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
        return this;
    }

    public void setUom(CUnitOfMeasure cUnitOfMeasure) {
        this.uom = cUnitOfMeasure;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MContractLine)) {
            return false;
        }
        return id != null && id.equals(((MContractLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }


    @Override
    public String toString() {
        return "MContractLine{" +
            "id=" + getId() +
            ", lineNo=" + getLineNo() +
            ", quantity=" + getQuantity() +
            ", quantityBalance=" + getQuantityBalance() +
            ", ceilingPrice=" + getCeilingPrice() +
            ", totalCeilingPrice=" + getTotalCeilingPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
