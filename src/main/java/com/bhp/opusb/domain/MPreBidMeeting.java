package com.bhp.opusb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

/**
 * A MPreBidMeeting.
 */
@Entity
@Table(name = "m_pre_bid_meeting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPreBidMeeting extends AbstractAuditingEntity {

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
    private MBiddingSchedule biddingSchedule;

    @OneToMany(mappedBy = "preBidMeeting")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Where(clause = "active = true")
    private List<MPreBidMeetingAttachment> mPreBidMeetingAttachments = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPreBidMeetings")
    private ADOrganization adOrganization;

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

    public MPreBidMeeting uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPreBidMeeting active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public MPreBidMeeting biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }

    public List<MPreBidMeetingAttachment> getMPreBidMeetingAttachments() {
        return mPreBidMeetingAttachments;
    }

    public MPreBidMeeting mPreBidMeetingAttachments(List<MPreBidMeetingAttachment> mPreBidMeetingAttachments) {
        this.mPreBidMeetingAttachments = mPreBidMeetingAttachments;
        return this;
    }

    public MPreBidMeeting addMPreBidMeetingAttachment(MPreBidMeetingAttachment mPreBidMeetingAttachment) {
        this.mPreBidMeetingAttachments.add(mPreBidMeetingAttachment);
        mPreBidMeetingAttachment.setPreBidMeeting(this);
        return this;
    }

    public MPreBidMeeting removeMPreBidMeetingAttachment(MPreBidMeetingAttachment mPreBidMeetingAttachment) {
        this.mPreBidMeetingAttachments.remove(mPreBidMeetingAttachment);
        mPreBidMeetingAttachment.setPreBidMeeting(null);
        return this;
    }

    public void setMPreBidMeetingAttachments(List<MPreBidMeetingAttachment> mPreBidMeetingAttachments) {
        this.mPreBidMeetingAttachments = mPreBidMeetingAttachments;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPreBidMeeting adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof MPreBidMeeting)) {
            return false;
        }
        return id != null && id.equals(((MPreBidMeeting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPreBidMeeting{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
