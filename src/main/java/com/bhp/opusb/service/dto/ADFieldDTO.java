package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADField} entity.
 */
public class ADFieldDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private UUID uid;

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

    private String readOnlyLogic;

    private Boolean writable;

    private Integer columnNo;

    private Integer columnSpan;

    private Boolean updatable;

    private Boolean alwaysUpdatable;

    private Boolean copyable;

    private String defaultValue;

    private String formatPattern;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adReferenceId;
    private String adReferenceName;
    private Long adValidationRuleId;
    private String adValidationRuleName;
    private Long adColumnId;
    private String adColumnName;

    private Long adTabId;
    private String adTabName;
    
    private ADReferenceDTO adReference;
    private AdValidationRuleDTO adValidationRule;
    private ADColumnDTO adColumn;

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

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
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

    public Boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public Boolean isAlwaysUpdatable() {
        return alwaysUpdatable;
    }

    public void setAlwaysUpdatable(Boolean alwaysUpdatable) {
        this.alwaysUpdatable = alwaysUpdatable;
    }

    public Boolean isCopyable() {
        return copyable;
    }

    public void setCopyable(Boolean copyable) {
        this.copyable = copyable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
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

    public Long getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(Long aDReferenceId) {
        this.adReferenceId = aDReferenceId;
    }

    public String getAdReferenceName() {
        return adReferenceName;
    }

    public void setAdReferenceName(String adReferenceName) {
        this.adReferenceName = adReferenceName;
    }

    public Long getAdColumnId() {
        return adColumnId;
    }

    public void setAdColumnId(Long aDColumnId) {
        this.adColumnId = aDColumnId;
    }

    public Long getAdValidationRuleId() {
        return adValidationRuleId;
    }

    public void setAdValidationRuleId(Long adValidationRuleId) {
        this.adValidationRuleId = adValidationRuleId;
    }

    public String getAdValidationRuleName() {
        return adValidationRuleName;
    }

    public void setAdValidationRuleName(String adValidationRuleName) {
        this.adValidationRuleName = adValidationRuleName;
    }

    public String getAdTabName() {
        return adTabName;
    }

    public void setAdTabName(String adTabName) {
        this.adTabName = adTabName;
    }

    public AdValidationRuleDTO getAdValidationRule() {
        return adValidationRule;
    }

    public void setAdValidationRule(AdValidationRuleDTO adValidationRule) {
        this.adValidationRule = adValidationRule;
    }

    public Long getAdTabId() {
        return adTabId;
    }

    public void setAdTabId(Long aDTabId) {
        this.adTabId = aDTabId;
    }

    public ADReferenceDTO getAdReference() {
        return adReference;
    }

    public void setAdReference(ADReferenceDTO adReference) {
        this.adReference = adReference;
    }

    public ADColumnDTO getAdColumn() {
        return adColumn;
    }

    public void setAdColumn(ADColumnDTO adColumn) {
        this.adColumn = adColumn;
    }

    public String getAdColumnName() {
        return adColumnName;
    }

    public void setAdColumnName(String adColumnName) {
        this.adColumnName = adColumnName;
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
            ", uid='" + getUid() + "'" +
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
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", writable='" + isWritable() + "'" +
            ", columnNo=" + getColumnNo() +
            ", columnSpan=" + getColumnSpan() +
            ", updatable='" + isUpdatable() + "'" +
            ", alwaysUpdatable='" + isAlwaysUpdatable() + "'" +
            ", copyable='" + isCopyable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adReferenceId=" + getAdReferenceId() +
            ", adColumnId=" + getAdColumnId() +
            ", adValidationRuleId=" + getAdValidationRuleId() +
            ", adTabId=" + getAdTabId() +
            "}";
    }
}
