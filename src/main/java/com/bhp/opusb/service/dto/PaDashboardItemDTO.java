package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.PaDashboardItemType;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the {@link com.bhp.opusb.domain.PaDashboardItem} entity.
 */
@ApiModel(description = "The PaDashboardItem entity.\n@author Ananta Aryadewa")
public class PaDashboardItemDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private String description;

    @NotNull
    private Integer columnNo;

    @NotNull
    private Integer rowNo;

    @NotNull
    private PaDashboardItemType type;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adWatchListId;
    private String adWatchListName;

    private Long paDashboardId;
    private String paDashboardName;
    
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

    public Integer getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public PaDashboardItemType getType() {
        return type;
    }

    public void setType(PaDashboardItemType type) {
        this.type = type;
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

    public Long getAdWatchListId() {
        return adWatchListId;
    }

    public void setAdWatchListId(Long adWatchListId) {
        this.adWatchListId = adWatchListId;
    }

    public String getAdWatchListName() {
        return adWatchListName;
    }

    public void setAdWatchListName(String adWatchListName) {
        this.adWatchListName = adWatchListName;
    }

    public Long getPaDashboardId() {
        return paDashboardId;
    }

    public void setPaDashboardId(Long paDashboardId) {
        this.paDashboardId = paDashboardId;
    }

    public String getPaDashboardName() {
        return paDashboardName;
    }

    public void setPaDashboardName(String paDashboardName) {
        this.paDashboardName = paDashboardName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaDashboardItemDTO paDashboardItemDTO = (PaDashboardItemDTO) o;
        if (paDashboardItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paDashboardItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaDashboardItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", columnNo=" + getColumnNo() +
            ", rowNo=" + getRowNo() +
            ", type='" + getType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adWatchListId=" + getAdWatchListId() +
            ", paDashboardId=" + getPaDashboardId() +
            "}";
    }
}
