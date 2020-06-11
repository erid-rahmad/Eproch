package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.AdValidationRule} entity.
 */
public class AdValidationRuleDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private UUID uid;

    @NotNull
    private String name;

    private String description;

    private String query;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

        AdValidationRuleDTO adValidationRuleDTO = (AdValidationRuleDTO) o;
        if (adValidationRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adValidationRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdValidationRuleDTO{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", query='" + getQuery() + "'" +
            ", adValidationRuleId=" + getAdOrganizationId() +
            "}";
    }
}
