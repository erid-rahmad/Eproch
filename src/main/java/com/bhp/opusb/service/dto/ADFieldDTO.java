package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADColumnType;

import io.swagger.annotations.ApiModelProperty;

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

    private Integer columnOffset;

    private Integer columnSpan;

    private Integer rowNo;

    /**
     * Don't use the actual column in the table. Instead, refers to a specific DTO's field.
     */
    @ApiModelProperty(value = "Don't use the actual column in the table. Instead, refers to a specific DTO's field.")
    private String virtualColumnName;

    /**
     * It's automatically filled by the dbSync process.\nupdatable = false.
     */
    @ApiModelProperty(value = "It's automatically filled by the dbSync process. updatable = false.")
    private Boolean mandatory;

    /**
     * This should be displayed only if mandatory = false.
     */
    @ApiModelProperty(value = "This should be displayed only if mandatory = false.")
    private String mandatoryLogic;

    private Boolean updatable;

    private Boolean alwaysUpdatable;

    private Boolean copyable;

    private String defaultValue;

    private String formatPattern;

    /**
     * The minimum character length of the string column.
     */
    @ApiModelProperty(value = "The minimum character length of the string column.")
    private Integer minLength;

    /**
     * The maximum character length of the string column.
     */
    @ApiModelProperty(value = "The maximum character length of the string column.")
    private Integer maxLength;

    /**
     * The minimum number value of the numeric column.
     */
    @ApiModelProperty(value = "The minimum number value of the numeric column.")
    private Long minValue;

    /**
     * The minimum number value of the numeric column.
     */
    @ApiModelProperty(value = "The minimum number value of the numeric column.")
    private Long maxValue;

    private ADColumnType type;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adReferenceId;
    private String adReferenceName;

    private Long adColumnId;
    private String adColumnName;

    private Long adValidationRuleId;
    private String adValidationRuleName;

    private Long adButtonId;
    private String adButtonName;

    private Long adTabId;
    private String adTabName;
    
    private List<AdCalloutDTO> adCallouts = new ArrayList<>();
    private ADReferenceDTO adReference;
    private AdValidationRuleDTO adValidationRule;
    private ADColumnDTO adColumn;
    private AdButtonDTO adButton;
    
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

    public Integer getColumnOffset() {
        return columnOffset;
    }

    public void setColumnOffset(Integer columnOffset) {
        this.columnOffset = columnOffset;
    }

    public Integer getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public String getVirtualColumnName() {
        return virtualColumnName;
    }

    public void setVirtualColumnName(String virtualColumnName) {
        this.virtualColumnName = virtualColumnName;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMandatoryLogic() {
        return mandatoryLogic;
    }

    public void setMandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
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

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public ADColumnType getType() {
        return type;
    }

    public void setType(ADColumnType type) {
        this.type = type;
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

    public String getAdColumnName() {
        return adColumnName;
    }

    public void setAdColumnName(String adColumnName) {
        this.adColumnName = adColumnName;
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

    public Long getAdButtonId() {
        return adButtonId;
    }

    public void setAdButtonId(Long adButtonId) {
        this.adButtonId = adButtonId;
    }

    public String getAdButtonName() {
        return adButtonName;
    }

    public void setAdButtonName(String adButtonName) {
        this.adButtonName = adButtonName;
    }

    public Long getAdTabId() {
        return adTabId;
    }

    public void setAdTabId(Long aDTabId) {
        this.adTabId = aDTabId;
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

    public List<AdCalloutDTO> getAdCallouts() {
        return adCallouts;
    }

    public void setAdCallouts(List<AdCalloutDTO> adCallouts) {
        this.adCallouts = adCallouts;
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

    public AdButtonDTO getAdButton() {
        return adButton;
    }

    public void setAdButton(AdButtonDTO adButton) {
        this.adButton = adButton;
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
            ", columnOffset=" + getColumnOffset() +
            ", columnSpan=" + getColumnSpan() +
            ", rowNo=" + getRowNo() +
            ", virtualColumnName='" + getVirtualColumnName() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", updatable='" + isUpdatable() + "'" +
            ", alwaysUpdatable='" + isAlwaysUpdatable() + "'" +
            ", copyable='" + isCopyable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", type='" + getType() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adReferenceId=" + getAdReferenceId() +
            ", adColumnId=" + getAdColumnId() +
            ", adValidationRuleId=" + getAdValidationRuleId() +
            ", adButtonId=" + getAdButtonId() +
            ", adTabId=" + getAdTabId() +
            "}";
    }
}
