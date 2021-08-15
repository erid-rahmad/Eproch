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
 * A MPrequalAnnouncementResult.
 */
@Entity
@Table(name = "m_prequal_announcement_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalAnnouncementResult extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description_pass", nullable = false)
    private String descriptionPass;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description_fail", nullable = false)
    private String descriptionFail;

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
    @JsonIgnoreProperties("mPrequalAnnouncementResults")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalAnnouncementResults")
    private MPrequalificationInformation prequalification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalAnnouncementResults")
    private MPrequalificationSchedule prequalificationSchedule;

    @ManyToOne
    @JsonIgnoreProperties("mPrequalAnnouncementResults")
    private CAttachment attachment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptionPass() {
        return descriptionPass;
    }

    public MPrequalAnnouncementResult descriptionPass(String descriptionPass) {
        this.descriptionPass = descriptionPass;
        return this;
    }

    public void setDescriptionPass(String descriptionPass) {
        this.descriptionPass = descriptionPass;
    }

    public String getDescriptionFail() {
        return descriptionFail;
    }

    public MPrequalAnnouncementResult descriptionFail(String descriptionFail) {
        this.descriptionFail = descriptionFail;
        return this;
    }

    public void setDescriptionFail(String descriptionFail) {
        this.descriptionFail = descriptionFail;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public MPrequalAnnouncementResult publishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public MPrequalAnnouncementResult documentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public UUID getUid() {
        return uid;
    }

    public MPrequalAnnouncementResult uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalAnnouncementResult active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalAnnouncementResult adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPrequalAnnouncementResult prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }

    public MPrequalificationSchedule getPrequalificationSchedule() {
        return prequalificationSchedule;
    }

    public MPrequalAnnouncementResult prequalificationSchedule(MPrequalificationSchedule mPrequalificationSchedule) {
        this.prequalificationSchedule = mPrequalificationSchedule;
        return this;
    }

    public void setPrequalificationSchedule(MPrequalificationSchedule mPrequalificationSchedule) {
        this.prequalificationSchedule = mPrequalificationSchedule;
    }

    public CAttachment getAttachment() {
        return attachment;
    }

    public MPrequalAnnouncementResult attachment(CAttachment cAttachment) {
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
        if (!(o instanceof MPrequalAnnouncementResult)) {
            return false;
        }
        return id != null && id.equals(((MPrequalAnnouncementResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalAnnouncementResult{" +
            "id=" + getId() +
            ", descriptionPass='" + getDescriptionPass() + "'" +
            ", descriptionFail='" + getDescriptionFail() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
