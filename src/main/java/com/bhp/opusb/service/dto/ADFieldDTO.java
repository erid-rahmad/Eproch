package com.bhp.opusb.service.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADField} entity.
 */
public class ADFieldDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String hint;

    private String staticText;

    private Boolean staticField;

    private Boolean labelOnly;

    private Boolean showLabel;

    private Boolean showInGrid;

    private Boolean showInDetail;

    private Integer gridSequence;

    private Integer detailSequence;

    private String displayLogic;

    private Boolean writable;

    private Integer columnNo;

    private Integer columnSpan;

    private Boolean active;


    private Long adClientId;

    private Long adOrganizationId;
    
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getStaticText() {
        return staticText;
    }

    public void setStaticText(String staticText) {
        this.staticText = staticText;
    }

    public Boolean isStaticField() {
        return staticField;
    }

    public void setStaticField(Boolean staticField) {
        this.staticField = staticField;
    }

    public Boolean isLabelOnly() {
        return labelOnly;
    }

    public void setLabelOnly(Boolean labelOnly) {
        this.labelOnly = labelOnly;
    }

    public Boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    public Boolean isShowInGrid() {
        return showInGrid;
    }

    public void setShowInGrid(Boolean showInGrid) {
        this.showInGrid = showInGrid;
    }

    public Boolean isShowInDetail() {
        return showInDetail;
    }

    public void setShowInDetail(Boolean showInDetail) {
        this.showInDetail = showInDetail;
    }

    public Integer getGridSequence() {
        return gridSequence;
    }

    public void setGridSequence(Integer gridSequence) {
        this.gridSequence = gridSequence;
    }

    public Integer getDetailSequence() {
        return detailSequence;
    }

    public void setDetailSequence(Integer detailSequence) {
        this.detailSequence = detailSequence;
    }

    public String getDisplayLogic() {
        return displayLogic;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
    }

    public Boolean isWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Integer getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdClientId() {
        return adClientId;
    }

    public void setAdClientId(Long aDClientId) {
        this.adClientId = aDClientId;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADFieldDTO aDFieldDTO = (ADFieldDTO) o;
        if (aDFieldDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDFieldDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADFieldDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", hint='" + getHint() + "'" +
            ", staticText='" + getStaticText() + "'" +
            ", staticField='" + isStaticField() + "'" +
            ", labelOnly='" + isLabelOnly() + "'" +
            ", showLabel='" + isShowLabel() + "'" +
            ", showInGrid='" + isShowInGrid() + "'" +
            ", showInDetail='" + isShowInDetail() + "'" +
            ", gridSequence=" + getGridSequence() +
            ", detailSequence=" + getDetailSequence() +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", writable='" + isWritable() + "'" +
            ", columnNo=" + getColumnNo() +
            ", columnSpan=" + getColumnSpan() +
            ", active='" + isActive() + "'" +
            ", adClientId=" + getAdClientId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            "}";
    }
}
