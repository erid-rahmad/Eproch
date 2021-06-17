package com.bhp.opusb.domain;

import java.time.ZonedDateTime;
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
import org.hibernate.annotations.Formula;

/**
 * A MBiddingInvitation.
 */
@Entity
@Table(name = "m_bidding_invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingInvitation extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "invitation_status")
    private String invitationStatus;

    @Column(name = "reason")
    private String reason;

    @Column(name = "answer_date")
    private ZonedDateTime answerDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingInvitations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingInvitations")
    private CAnnouncement announcement;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingInvitations")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingInvitations")
    private CVendor vendor;

    @Formula("(select concat(cl.address_1,' ',cl.address_2,' ',cl.address_3,' ',cl.address_4) from c_location cl where cl.id = ("+
    "select cvl.location_id from c_vendor_location cvl where cvl.vendor_id = ("+
    "select mbi.vendor_id from m_bidding_invitation mbi where mbi.id = id) order by cvl.tax_invoice_address desc limit 1))")
    private String location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public MBiddingInvitation invitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
        return this;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getReason() {
        return reason;
    }

    public MBiddingInvitation reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getAnswerDate() {
        return answerDate;
    }

    public MBiddingInvitation answerDate(ZonedDateTime answerDate) {
        this.answerDate = answerDate;
        return this;
    }

    public void setAnswerDate(ZonedDateTime answerDate) {
        this.answerDate = answerDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingInvitation uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingInvitation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingInvitation adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CAnnouncement getAnnouncement() {
        return announcement;
    }

    public MBiddingInvitation announcement(CAnnouncement cAnnouncement) {
        this.announcement = cAnnouncement;
        return this;
    }

    public void setAnnouncement(CAnnouncement cAnnouncement) {
        this.announcement = cAnnouncement;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingInvitation bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MBiddingInvitation vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBiddingInvitation)) {
            return false;
        }
        return id != null && id.equals(((MBiddingInvitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingInvitation{" +
            "id=" + getId() +
            ", invitationStatus='" + getInvitationStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", answerDate='" + getAnswerDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
