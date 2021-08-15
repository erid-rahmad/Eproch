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
 * A MPrequalificationResult.
 */
@Entity
@Table(name = "m_prequalification_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalificationResult extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("mPrequalificationResults")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationResults")
    private MPrequalAnnouncementResult announcementResult;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationResults")
    private MPrequalificationInformation prequalification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationResults")
    private CVendor vendor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationResults")
    private MPrequalificationSubmission submission;

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

    public MPrequalificationResult uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalificationResult active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalificationResult adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalAnnouncementResult getAnnouncementResult() {
        return announcementResult;
    }

    public MPrequalificationResult announcementResult(MPrequalAnnouncementResult mPrequalAnnouncementResult) {
        this.announcementResult = mPrequalAnnouncementResult;
        return this;
    }

    public void setAnnouncementResult(MPrequalAnnouncementResult mPrequalAnnouncementResult) {
        this.announcementResult = mPrequalAnnouncementResult;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPrequalificationResult prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MPrequalificationResult vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }

    public MPrequalificationSubmission getSubmission() {
        return submission;
    }

    public MPrequalificationResult submission(MPrequalificationSubmission mPrequalificationSubmission) {
        this.submission = mPrequalificationSubmission;
        return this;
    }

    public void setSubmission(MPrequalificationSubmission mPrequalificationSubmission) {
        this.submission = mPrequalificationSubmission;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPrequalificationResult)) {
            return false;
        }
        return id != null && id.equals(((MPrequalificationResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalificationResult{" +
            "id=" + getId() +
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
