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
 * A CAnnouncementResult.
 */
@Entity
@Table(name = "c_announcement_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CAnnouncementResult extends AbstractAuditingEntity{

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

    @Column(name = "document_no")
    private String documentNo;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cAnnouncementResults")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cAnnouncementResults")
    private MBidding bidding;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cAnnouncementResults")
    private MBiddingSchedule biddingSchedule;

    @ManyToOne
    @JsonIgnoreProperties("cAnnouncementResults")
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

    public CAnnouncementResult description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public CAnnouncementResult publishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public CAnnouncementResult documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public UUID getUid() {
        return uid;
    }

    public CAnnouncementResult uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CAnnouncementResult active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CAnnouncementResult adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MBidding getBidding() {
        return bidding;
    }

    public CAnnouncementResult bidding(MBidding mBidding) {
        this.bidding = mBidding;
        return this;
    }

    public void setBidding(MBidding mBidding) {
        this.bidding = mBidding;
    }

    public MBiddingSchedule getBiddingSchedule() {
        return biddingSchedule;
    }

    public CAnnouncementResult biddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
        return this;
    }

    public void setBiddingSchedule(MBiddingSchedule mBiddingSchedule) {
        this.biddingSchedule = mBiddingSchedule;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public CAnnouncementResult attachment(CAttachment cAttachment) {
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
        if (!(o instanceof CAnnouncementResult)) {
            return false;
        }
        return id != null && id.equals(((CAnnouncementResult) o).id);
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
        return "CAnnouncementResult{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
