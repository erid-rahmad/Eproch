package com.bhp.opusb.domain;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MBiddingSchedule.
 */
@Entity
@Table(name = "m_bidding_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBiddingSchedule extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "biddingSchedule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MBiddingScheduleAttachment> mBiddingScheduleAttachments = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingSchedules")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingSchedules")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBiddingSchedules")
    private CEventTypeline eventTypeLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public MBiddingSchedule startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public MBiddingSchedule endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MBiddingSchedule uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBiddingSchedule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<MBiddingScheduleAttachment> getMBiddingScheduleAttachments() {
        return mBiddingScheduleAttachments;
    }

    public MBiddingSchedule mBiddingScheduleAttachments(Set<MBiddingScheduleAttachment> mBiddingScheduleAttachments) {
        this.mBiddingScheduleAttachments = mBiddingScheduleAttachments;
        return this;
    }

    public MBiddingSchedule addMBiddingScheduleAttachment(MBiddingScheduleAttachment mBiddingScheduleAttachment) {
        this.mBiddingScheduleAttachments.add(mBiddingScheduleAttachment);
        mBiddingScheduleAttachment.setBiddingSchedule(this);
        return this;
    }

    public MBiddingSchedule removeMBiddingScheduleAttachment(MBiddingScheduleAttachment mBiddingScheduleAttachment) {
        this.mBiddingScheduleAttachments.remove(mBiddingScheduleAttachment);
        mBiddingScheduleAttachment.setBiddingSchedule(null);
        return this;
    }

    public void setMBiddingScheduleAttachments(Set<MBiddingScheduleAttachment> mBiddingScheduleAttachments) {
        this.mBiddingScheduleAttachments = mBiddingScheduleAttachments;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public MBiddingSchedule bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBiddingSchedule adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CEventTypeline getEventTypeLine() {
        return eventTypeLine;
    }

    public MBiddingSchedule eventTypeLine(CEventTypeline cEventTypeline) {
        this.eventTypeLine = cEventTypeline;
        return this;
    }

    public void setEventTypeLine(CEventTypeline cEventTypeline) {
        this.eventTypeLine = cEventTypeline;
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
        if (!(o instanceof MBiddingSchedule)) {
            return false;
        }
        return id != null && id.equals(((MBiddingSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBiddingSchedule{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
