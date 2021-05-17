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
 * A CPrequalificationEventLine.
 */
@Entity
@Table(name = "c_prequalification_event_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPrequalificationEventLine extends AbstractAuditingEntity {

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
    @JsonIgnoreProperties("cPrequalificationEventLines")
    private CPrequalificationEvent prequalificationEvent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalificationEventLines")
    private CPrequalificationStep prequalificationStep;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalificationEventLines")
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

    public CPrequalificationEventLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPrequalificationEventLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CPrequalificationEvent getPrequalificationEvent() {
        return prequalificationEvent;
    }

    public CPrequalificationEventLine prequalificationEvent(CPrequalificationEvent cPrequalificationEvent) {
        this.prequalificationEvent = cPrequalificationEvent;
        return this;
    }

    public void setPrequalificationEvent(CPrequalificationEvent cPrequalificationEvent) {
        this.prequalificationEvent = cPrequalificationEvent;
    }

    public CPrequalificationStep getPrequalificationStep() {
        return prequalificationStep;
    }

    public CPrequalificationEventLine prequalificationStep(CPrequalificationStep cPrequalificationStep) {
        this.prequalificationStep = cPrequalificationStep;
        return this;
    }

    public void setPrequalificationStep(CPrequalificationStep cPrequalificationStep) {
        this.prequalificationStep = cPrequalificationStep;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPrequalificationEventLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPrequalificationEventLine)) {
            return false;
        }
        return id != null && id.equals(((CPrequalificationEventLine) o).id);
    }

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPrequalificationEventLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
