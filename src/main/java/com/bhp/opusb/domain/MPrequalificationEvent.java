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
 * A MPrequalificationEvent.
 */
@Entity
@Table(name = "m_prequalification_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MPrequalificationEvent extends AbstractAuditingEntity implements Serializable {

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
    @JsonIgnoreProperties("mPrequalificationEvents")
    private MPrequalificationInformation prequalification;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvents")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvents")
    private CPrequalificationEvent event;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mPrequalificationEvents")
    private CPrequalificationMethod method;

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

    public MPrequalificationEvent uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MPrequalificationEvent active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MPrequalificationInformation getPrequalification() {
        return prequalification;
    }

    public MPrequalificationEvent prequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
        return this;
    }

    public void setPrequalification(MPrequalificationInformation mPrequalificationInformation) {
        this.prequalification = mPrequalificationInformation;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MPrequalificationEvent adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CPrequalificationEvent getEvent() {
        return event;
    }

    public MPrequalificationEvent event(CPrequalificationEvent cPrequalificationEvent) {
        this.event = cPrequalificationEvent;
        return this;
    }

    public void setEvent(CPrequalificationEvent cPrequalificationEvent) {
        this.event = cPrequalificationEvent;
    }

    public CPrequalificationMethod getMethod() {
        return method;
    }

    public MPrequalificationEvent method(CPrequalificationMethod cPrequalificationMethod) {
        this.method = cPrequalificationMethod;
        return this;
    }

    public void setMethod(CPrequalificationMethod cPrequalificationMethod) {
        this.method = cPrequalificationMethod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MPrequalificationEvent)) {
            return false;
        }
        return id != null && id.equals(((MPrequalificationEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MPrequalificationEvent{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void assignUid(){
        this.uid = UUID.randomUUID();
        this.active = true;
    }
}
