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
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdTaskApplication.
 */
@Entity
@Table(name = "ad_task_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdTaskApplication extends AbstractAuditingEntity {

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
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z_\\-]+$")
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "metadata_uri")
    private String metadataUri;

    @Column(name = "version")
    private String version;

    @Column(name = "override_existing")
    private Boolean overrideExisting;

    @Column(name = "deployed")
    private Boolean deployed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("adTaskApplications")
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

    public AdTaskApplication uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdTaskApplication active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public AdTaskApplication name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public AdTaskApplication value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUri() {
        return uri;
    }

    public AdTaskApplication uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMetadataUri() {
        return metadataUri;
    }

    public AdTaskApplication metadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
        return this;
    }

    public void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    public String getVersion() {
        return version;
    }

    public AdTaskApplication version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isOverrideExisting() {
        return overrideExisting;
    }

    public AdTaskApplication overrideExisting(Boolean overrideExisting) {
        this.overrideExisting = overrideExisting;
        return this;
    }

    public void setOverrideExisting(Boolean overrideExisting) {
        this.overrideExisting = overrideExisting;
    }

    public Boolean isDeployed() {
        return deployed;
    }

    public AdTaskApplication deployed(Boolean deployed) {
        this.deployed = deployed;
        return this;
    }

    public void setDeployed(Boolean deployed) {
        this.deployed = deployed;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdTaskApplication adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof AdTaskApplication)) {
            return false;
        }
        return id != null && id.equals(((AdTaskApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdTaskApplication{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", uri='" + getUri() + "'" +
            ", metadataUri='" + getMetadataUri() + "'" +
            ", version='" + getVersion() + "'" +
            ", overrideExisting='" + isOverrideExisting() + "'" +
            ", deployed='" + isDeployed() + "'" +
            "}";
    }
}
