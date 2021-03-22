package com.bhp.opusb.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * A MBiddingSubItem.
 */
@Entity
@Table(name = "m_bidding_sub_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingSubItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "biddingSubItem")
    @Fetch(FetchMode.JOIN)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OrderBy("lineNo, id")
    @JsonManagedReference
    private List<MBiddingSubItemLine> mBiddingSubItemLines = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingSubItems")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingSubItems")
    private CProduct product;

    @OneToOne(mappedBy = "subItem")
    @JsonIgnore
    @JsonBackReference
    private MBiddingLine biddingLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public MBiddingSubItem totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingSubItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingSubItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<MBiddingSubItemLine> getMBiddingSubItemLines() {
        return mBiddingSubItemLines;
    }

    public MBiddingSubItem mBiddingSubItemLines(List<MBiddingSubItemLine> mBiddingSubItemLines) {
        this.mBiddingSubItemLines = mBiddingSubItemLines;
        return this;
    }

    public MBiddingSubItem addMBiddingSubItemLine(MBiddingSubItemLine mBiddingSubItemLine) {
        this.mBiddingSubItemLines.add(mBiddingSubItemLine);
        mBiddingSubItemLine.setBiddingSubItem(this);
        return this;
    }

    public MBiddingSubItem removeMBiddingSubItemLine(MBiddingSubItemLine mBiddingSubItemLine) {
        this.mBiddingSubItemLines.remove(mBiddingSubItemLine);
        mBiddingSubItemLine.setBiddingSubItem(null);
        return this;
    }

    public void setMBiddingSubItemLines(List<MBiddingSubItemLine> mBiddingSubItemLines) {
        this.mBiddingSubItemLines = mBiddingSubItemLines;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingSubItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CProduct getProduct() {
        return product;
    }

    public MBiddingSubItem product(CProduct cProduct) {
        this.product = cProduct;
        return this;
    }

    public void setProduct(CProduct cProduct) {
        this.product = cProduct;
    }

    public MBiddingLine getBiddingLine() {
        return biddingLine;
    }

    public MBiddingSubItem biddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
        return this;
    }

    public void setBiddingLine(MBiddingLine mBiddingLine) {
        this.biddingLine = mBiddingLine;
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
        if (!(o instanceof MBiddingSubItem)) {
            return false;
        }
        return id != null && id.equals(((MBiddingSubItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingSubItem{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
