package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdCalloutTarget.
 */
@Entity
@Table(name = "ad_callout_target")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdCalloutTarget extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Size(max = 30)
    @Column(name = "source_field", length = 30)
    private String sourceField;

    @NotNull
    @Size(max = 30)
    @Column(name = "target_name", length = 30, nullable = false)
    private String targetName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adCalloutTargets")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adCalloutTargets")
    private AdCallout callout;

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

    public AdCalloutTarget uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdCalloutTarget active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSourceField() {
        return sourceField;
    }

    public AdCalloutTarget sourceField(String sourceField) {
        this.sourceField = sourceField;
        return this;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getTargetName() {
        return targetName;
    }

    public AdCalloutTarget targetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdCalloutTarget adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdCallout getCallout() {
        return callout;
    }

    public AdCalloutTarget callout(AdCallout adCallout) {
        this.callout = adCallout;
        return this;
    }

    public void setCallout(AdCallout adCallout) {
        this.callout = adCallout;
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
        if (!(o instanceof AdCalloutTarget)) {
            return false;
        }
        return id != null && id.equals(((AdCalloutTarget) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdCalloutTarget{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", sourceField='" + getSourceField() + "'" +
            ", targetName='" + getTargetName() + "'" +
            "}";
    }
}
