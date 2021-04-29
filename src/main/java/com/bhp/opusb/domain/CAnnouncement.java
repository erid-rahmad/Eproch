package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A CAnnouncement.
 */
@Entity
@Table(name = "c_announcement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "publish_date")
    private ZonedDateTime publishDate;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("cAnnouncements")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("cAnnouncements")
    private MBidding bidding;

    @ManyToOne
    @JsonIgnoreProperties("cAnnouncements")
    private MBiddingSchedule biddingSchedule;

    @ManyToOne
    @JsonIgnoreProperties("cAnnouncements")
    private CAttachment attachment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public CAnnouncement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public CAnnouncement publishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public UUID getUid() {
        return uid;
    }

    public CAnnouncement uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CAnnouncement active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CAnnouncement adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public CAnnouncement bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public CAnnouncement biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public CAnnouncement attachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
        return this;
    }

    public void setAttachment(CAttachment cAttachment) {
        this.attachment = cAttachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CAnnouncement)) {
            return false;
        }
        return id != null && id.equals(((CAnnouncement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CAnnouncement{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
