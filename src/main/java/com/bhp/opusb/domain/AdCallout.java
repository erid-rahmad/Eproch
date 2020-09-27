package com.bhp.opusb.domain;

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdCallout.
 */
@Entity
@Table(name = "ad_callout")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdCallout extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Size(max = 15)
    @Column(name = "type", length = 15, nullable = false)
    private String type;

    @OneToMany(mappedBy = "callout")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdCalloutTarget> adCalloutTargets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adCallouts")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("adCallouts")
    private AdTrigger trigger;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adCallouts")
    private ADField field;

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

    public AdCallout uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdCallout active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdCallout name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AdCallout description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public AdCallout type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<AdCalloutTarget> getAdCalloutTargets() {
        return adCalloutTargets;
    }

    public AdCallout adCalloutTargets(Set<AdCalloutTarget> adCalloutTargets) {
        this.adCalloutTargets = adCalloutTargets;
        return this;
    }

    public AdCallout addAdCalloutTarget(AdCalloutTarget adCalloutTarget) {
        this.adCalloutTargets.add(adCalloutTarget);
        adCalloutTarget.setCallout(this);
        return this;
    }

    public AdCallout removeAdCalloutTarget(AdCalloutTarget adCalloutTarget) {
        this.adCalloutTargets.remove(adCalloutTarget);
        adCalloutTarget.setCallout(null);
        return this;
    }

    public void setAdCalloutTargets(Set<AdCalloutTarget> adCalloutTargets) {
        this.adCalloutTargets = adCalloutTargets;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdCallout adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdTrigger getTrigger() {
        return trigger;
    }

    public AdCallout trigger(AdTrigger adTrigger) {
        this.trigger = adTrigger;
        return this;
    }

    public void setTrigger(AdTrigger adTrigger) {
        this.trigger = adTrigger;
    }

    public ADField getField() {
        return field;
    }

    public AdCallout field(ADField aDField) {
        this.field = aDField;
        return this;
    }

    public void setField(ADField aDField) {
        this.field = aDField;
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
        if (!(o instanceof AdCallout)) {
            return false;
        }
        return id != null && id.equals(((AdCallout) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdCallout{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
