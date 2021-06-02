package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MVendorConfirmationLine.
 */
@Entity
@Table(name = "m_vendor_confirmation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorConfirmationLine extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationLines")
    private CVendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("mVendorConfirmationLines")
    private MBiddingEvalResult biddingEvalResult;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmationLines")
    private MVendorConfirmation vendorConfirmation;

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MVendorConfirmationLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorConfirmationLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public MVendorConfirmationLine status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorConfirmationLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MVendorConfirmationLine vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public MBiddingEvalResult getBiddingEvalResult() {
        return biddingEvalResult;
    }

    public MVendorConfirmationLine biddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
        return this;
    }

    public void setBiddingEvalResult(MBiddingEvalResult mBiddingEvalResult) {
        this.biddingEvalResult = mBiddingEvalResult;
    }

    public MVendorConfirmation getVendorConfirmation() {
        return vendorConfirmation;
    }

    public MVendorConfirmationLine vendorConfirmation(MVendorConfirmation mVendorConfirmation) {
        this.vendorConfirmation = mVendorConfirmation;
        return this;
    }

    public void setVendorConfirmation(MVendorConfirmation mVendorConfirmation) {
        this.vendorConfirmation = mVendorConfirmation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorConfirmationLine)) {
            return false;
        }
        return id != null && id.equals(((MVendorConfirmationLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorConfirmationLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
