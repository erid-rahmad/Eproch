package com.bhp.opusb.service.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADColumnType;

/**
 * A DTO for the {@link com.bhp.opusb.domain.ADColumn} entity.
 */
public class ADColumnDTO extends AbstractAuditingDTO {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String sqlName;

    private String description;

    private Long fieldLength;

    private Boolean key;

    private ADColumnType type;

    private Boolean foreignKey;

    private String importedTable;

    private String importedColumn;

    private Boolean mandatory;

    private String mandatoryLogic;

    private String displayLogic;

    private String readOnlyLogic;

    private Boolean updatable;

    private Boolean alwaysUpdatable;

    private Boolean copyable;

    private String defaultValue;

    private String formatPattern;

    private Integer minLength;

    private Integer maxLength;

    private Long minValue;

    private Long maxValue;

    private Boolean identifier;

    private Boolean defaultSelection;

    private Integer selectionSequence;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long adReferenceId;
    private String adReferenceName;

    private Long adValidationRuleId;
    private String adValidationRuleName;
    private Long adTableId;
    
    private ADReferenceDTO adReference;
    private AdValidationRuleDTO adValidationRule;

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

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Long fieldLength) {
        this.fieldLength = fieldLength;
    }

    public Boolean isKey() {
        return key;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public ADColumnType getType() {
        return type;
    }

    public void setType(ADColumnType type) {
        this.type = type;
    }

    public Boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getImportedTable() {
        return importedTable;
    }

    public void setImportedTable(String importedTable) {
        this.importedTable = importedTable;
    }

    public String getImportedColumn() {
        return importedColumn;
    }

    public void setImportedColumn(String importedColumn) {
        this.importedColumn = importedColumn;
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

    public Boolean isIdentifier() {
        return identifier;
    }

    public void setIdentifier(Boolean identifier) {
        this.identifier = identifier;
    }

    public Boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(Boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public Integer getSelectionSequence() {
        return selectionSequence;
    }

    public void setSelectionSequence(Integer selectionSequence) {
        this.selectionSequence = selectionSequence;
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

    public Long getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(Long aDTableId) {
        this.adTableId = aDTableId;
    }

    public ADReferenceDTO getAdReference() {
        return adReference;
    }

    public void setAdReference(ADReferenceDTO adReference) {
        this.adReference = adReference;
    }

    public AdValidationRuleDTO getAdValidationRule() {
        return adValidationRule;
    }

    public void setAdValidationRule(AdValidationRuleDTO adValidationRule) {
        this.adValidationRule = adValidationRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADColumnDTO aDColumnDTO = (ADColumnDTO) o;
        if (aDColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aDColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ADColumnDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sqlName='" + getSqlName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fieldLength=" + getFieldLength() +
            ", key='" + isKey() + "'" +
            ", type='" + getType() + "'" +
            ", foreignKey='" + isForeignKey() + "'" +
            ", importedTable='" + getImportedTable() + "'" +
            ", importedColumn='" + getImportedColumn() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", displayLogic='" + getDisplayLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", updatable='" + isUpdatable() + "'" +
            ", alwaysUpdatable='" + isAlwaysUpdatable() + "'" +
            ", copyable='" + isCopyable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", identifier='" + isIdentifier() + "'" +
            ", defaultSelection='" + isDefaultSelection() + "'" +
            ", selectionSequence=" + getSelectionSequence() +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adReferenceId=" + getAdReferenceId() +
            ", adValidationRuleId=" + getAdValidationRuleId() +
            ", adTableId=" + getAdTableId() +
            "}";
    }
}
