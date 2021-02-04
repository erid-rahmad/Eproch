package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.bhp.opusb.domain.PaDashboard} entity.
 */
@ApiModel(description = "The PaDashboard entity.\n@author Ananta Aryadewa")
public class PaDashboardDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    private String description;

    /**
     * The minimum number of columns.
     */
    @ApiModelProperty(value = "The minimum number of columns.")
    private Integer minColumns;

    /**
     * The maximum number of columns. Set to -1 for unlimited columns.
     */
    @ApiModelProperty(value = "The maximum number of columns. Set to -1 for unlimited columns.")
    private Integer maxColumns;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getMinColumns() {
        return minColumns;
    }

    public void setMinColumns(Integer minColumns) {
        this.minColumns = minColumns;
    }

    public Integer getMaxColumns() {
        return maxColumns;
    }

    public void setMaxColumns(Integer maxColumns) {
        this.maxColumns = maxColumns;
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

        PaDashboardDTO paDashboardDTO = (PaDashboardDTO) o;
        if (paDashboardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paDashboardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaDashboardDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", minColumns=" + getMinColumns() +
            ", maxColumns=" + getMaxColumns() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
