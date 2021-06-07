package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MVendorConfirmation.
 */
@Entity
@Table(name = "m_vendor_confirmation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MVendorConfirmation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmations")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmations")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("mVendorConfirmations")
    private CCurrency currency;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmations")
    private CCostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mVendorConfirmations")
    private AdUser pic;

    @Formula("(select mvcl.status from m_vendor_confirmation_line mvcl where mvcl.vendor_confirmation_id = id)")
    private String status;

    @Formula("(select coalesce(max(mvcc.id),0) from m_vendor_confirmation_contract mvcc where "
    + "mvcc.publish_date is not null and "
    + "mvcc.vendor_confirmation_line_id = (select mvcl.id from m_vendor_confirmation_line mvcl where mvcl.vendor_confirmation_id = id))")
    private Long latestContractId;

    @Formula("(select count(mvcl.id) from m_vendor_confirmation_line mvcl where mvcl.vendor_confirmation_id = id)")
    private Integer selectedWinners;

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

    public MVendorConfirmation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MVendorConfirmation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MVendorConfirmation bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MVendorConfirmation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CCurrency getCurrency() {
        return currency;
    }

    public MVendorConfirmation currency(CCurrency cCurrency) {
        this.currency = cCurrency;
        return this;
    }

    public void setCurrency(CCurrency cCurrency) {
        this.currency = cCurrency;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public MVendorConfirmation costCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
        return this;
    }

    public void setCostCenter(CCostCenter cCostCenter) {
        this.costCenter = cCostCenter;
    }

    public AdUser getPic() {
        return pic;
    }

    public MVendorConfirmation pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
    }

    public String getStatus(){
        return this.status;
    }

    public MVendorConfirmation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Integer getSelectedWinners(){
        return this.selectedWinners;
    }

    public void setSelectedWinners(Integer selectedWinners){
        this.selectedWinners = selectedWinners;
    }

    public Long getLatestContractId(){
        return this.latestContractId;
    }

    public MVendorConfirmation latestContractId(Long latestContractId) {
        this.latestContractId = latestContractId;
        return this;
    }

    public void setLatestContractId(Long latestContractId){
        this.latestContractId = latestContractId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MVendorConfirmation)) {
            return false;
        }
        return id != null && id.equals(((MVendorConfirmation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MVendorConfirmation{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
