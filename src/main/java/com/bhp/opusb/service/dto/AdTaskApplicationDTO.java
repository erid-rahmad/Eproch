package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdTaskApplication} entity.
 */
public class AdTaskApplicationDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

    private Boolean active;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z_\\-]+$")
    private String value;

    @NotNull
    private String uri;

    private String metadataUri;

    private String version;

    private Boolean overrideExisting;

    private Boolean deployed;


    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMetadataUri() {
        return metadataUri;
    }

    public void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isOverrideExisting() {
        return overrideExisting;
    }

    public void setOverrideExisting(Boolean overrideExisting) {
        this.overrideExisting = overrideExisting;
    }

    public Boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(Boolean deployed) {
        this.deployed = deployed;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdTaskApplicationDTO adTaskApplicationDTO = (AdTaskApplicationDTO) o;
        if (adTaskApplicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adTaskApplicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdTaskApplicationDTO{" +
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
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
