package com.bhp.opusb.domain;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MPreBidMeetingAttachment.
 */
@Entity
@Table(name = "m_pre_bid_meeting_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPreBidMeetingAttachment extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CAttachment cAttachment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreBidMeetingAttachments")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreBidMeetingAttachments")
    private MPreBidMeeting preBidMeeting;

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

    public MPreBidMeetingAttachment uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPreBidMeetingAttachment active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CAttachment getCAttachment() {
        return cAttachment;
    }

    public MPreBidMeetingAttachment cAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
        return this;
    }

    public void setCAttachment(CAttachment cAttachment) {
        this.cAttachment = cAttachment;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPreBidMeetingAttachment adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPreBidMeeting getPreBidMeeting() {
        return preBidMeeting;
    }

    public MPreBidMeetingAttachment preBidMeeting(MPreBidMeeting mPreBidMeeting) {
        this.preBidMeeting = mPreBidMeeting;
        return this;
    }

    public void setPreBidMeeting(MPreBidMeeting mPreBidMeeting) {
        this.preBidMeeting = mPreBidMeeting;
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
        if (!(o instanceof MPreBidMeetingAttachment)) {
            return false;
        }
        return id != null && id.equals(((MPreBidMeetingAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPreBidMeetingAttachment{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
