package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A MSubmissionSubItem.
 */
@Entity
@Table(name = "m_submission_sub_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MSubmissionSubItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "proposed_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal proposedPrice;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mSubmissionSubItems", allowSetters = true)
    private MBiddingSubItem biddingSubItem;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mSubmissionSubItems", allowSetters = true)
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "mSubmissionSubItems", allowSetters = true)
    private MBiddingSubmissionLine mBiddingSubmissionLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public MSubmissionSubItem proposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
        return this;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public UUID getUid() {
        return uid;
    }

    public MSubmissionSubItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MSubmissionSubItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBiddingSubItem getBiddingSubItem() {
        return biddingSubItem;
    }

    public MSubmissionSubItem biddingSubItem(MBiddingSubItem mBiddingSubItem) {
        this.biddingSubItem = mBiddingSubItem;
        return this;
    }

    public void setBiddingSubItem(MBiddingSubItem mBiddingSubItem) {
        this.biddingSubItem = mBiddingSubItem;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MSubmissionSubItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBiddingSubmissionLine getMBiddingSubmissionLine() {
        return mBiddingSubmissionLine;
    }

    public MSubmissionSubItem mBiddingSubmissionLine(MBiddingSubmissionLine mBiddingSubmissionLine) {
        this.mBiddingSubmissionLine = mBiddingSubmissionLine;
        return this;
    }

    public void setMBiddingSubmissionLine(MBiddingSubmissionLine mBiddingSubmissionLine) {
        this.mBiddingSubmissionLine = mBiddingSubmissionLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MSubmissionSubItem)) {
            return false;
        }
        return id != null && id.equals(((MSubmissionSubItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MSubmissionSubItem{" +
            "id=" + getId() +
            ", proposedPrice=" + getProposedPrice() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
