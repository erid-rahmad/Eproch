package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A MPrequalRegistration.
 */
@Entity
@Table(name = "m_prequal_registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalRegistration extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registration_status")
    private String registrationStatus;

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
    @JsonIgnoreProperties("mPrequalRegistrations")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalRegistrations")
    private MPrequalAnnouncement announcement;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalRegistrations")
    private MPrequalificationInformation prequalification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalRegistrations")
    private CVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public MPrequalRegistration registrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
        return this;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getReason() {
        return reason;
    }

    public MPrequalRegistration reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getAnswerDate() {
        return answerDate;
    }

    public MPrequalRegistration answerDate(ZonedDateTime answerDate) {
        this.answerDate = answerDate;
        return this;
    }

    public void setAnswerDate(ZonedDateTime answerDate) {
        this.answerDate = answerDate;
    }

    public UUID getUid() {
        return uid;
    }

    public MPrequalRegistration uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalRegistration active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalRegistration adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public MPrequalAnnouncement getAnnouncement() {
        return announcement;
    }

    public MPrequalRegistration announcement(MPrequalAnnouncement mPrequalAnnouncement) {
        this.announcement = mPrequalAnnouncement;
        return this;
    }

    public void setAnnouncement(MPrequalAnnouncement mPrequalAnnouncement) {
        this.announcement = mPrequalAnnouncement;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPrequalRegistration prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }

    public CVendor getVendor() {
        return vendor;
    }

    public MPrequalRegistration vendor(CVendor cVendor) {
        this.vendor = cVendor;
        return this;
    }

    public void setVendor(CVendor cVendor) {
        this.vendor = cVendor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPrequalRegistration)) {
            return false;
        }
        return id != null && id.equals(((MPrequalRegistration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalRegistration{" +
            "id=" + getId() +
            ", registrationStatus='" + getRegistrationStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", answerDate='" + getAnswerDate() + "'" +
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
