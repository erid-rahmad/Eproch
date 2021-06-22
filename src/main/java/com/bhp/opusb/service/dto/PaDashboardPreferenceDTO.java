package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the {@link com.bhp.opusb.domain.PaDashboardPreference} entity.
 */
@ApiModel(description = "Per-user dashboard preference.\n@author Ananta Aryadewa")
public class PaDashboardPreferenceDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Integer columnNo;

    @NotNull
    private Integer rowNo;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adUserUserId;
    private String adUserUserName;
    
    private Long paDashboardItemId;
    private String paDashboardItemName;
    private PaDashboardItemDTO paDashboardItem;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAdUserUserId() {
        return adUserUserId;
    }

    public void setAdUserUserId(Long adUserUserId) {
        this.adUserUserId = adUserUserId;
    }

    public String getAdUserUserName() {
        return adUserUserName;
    }

    public void setAdUserUserName(String adUserUserName) {
        this.adUserUserName = adUserUserName;
    }

    public Long getPaDashboardItemId() {
        return paDashboardItemId;
    }

    public void setPaDashboardItemId(Long paDashboardItemId) {
        this.paDashboardItemId = paDashboardItemId;
    }

    public String getPaDashboardItemName() {
        return paDashboardItemName;
    }

    public void setPaDashboardItemName(String paDashboardItemName) {
        this.paDashboardItemName = paDashboardItemName;
    }

    public PaDashboardItemDTO getPaDashboardItem() {
        return paDashboardItem;
    }

    public void setPaDashboardItem(PaDashboardItemDTO paDashboardItem) {
        this.paDashboardItem = paDashboardItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaDashboardPreferenceDTO paDashboardPreferenceDTO = (PaDashboardPreferenceDTO) o;
        if (paDashboardPreferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paDashboardPreferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaDashboardPreferenceDTO{" +
            "id=" + getId() +
            ", columnNo=" + getColumnNo() +
            ", rowNo=" + getRowNo() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adUserId=" + getAdUserUserId() +
            ", paDashboardItemId=" + getPaDashboardItemId() +
            "}";
    }
}
