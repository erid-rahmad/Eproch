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

    private String description;

    private Boolean key;

    private ADColumnType type;

    private Boolean mandatory;

    private String mandatoryLogic;

    private String readOnlyLogic;

    private Boolean updatable;

    private String defaultValue;

    private String formatPattern;

    private Integer minValue;

    private Integer maxValue;

    private Boolean active;


    private Long adClientId;

    private Long adOrganizationId;

    private Long adReferenceId;

    private Long adTableId;
    
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

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
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

    public Long getAdReferenceId() {
        return adReferenceId;
    }

    public void setAdReferenceId(Long aDReferenceId) {
        this.adReferenceId = aDReferenceId;
    }

    public Long getAdTableId() {
        return adTableId;
    }

    public void setAdTableId(Long aDTableId) {
        this.adTableId = aDTableId;
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
            ", description='" + getDescription() + "'" +
            ", key='" + isKey() + "'" +
            ", type='" + getType() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", updatable='" + isUpdatable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", active='" + isActive() + "'" +
            ", adClientId=" + getAdClientId() +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", adReferenceId=" + getAdReferenceId() +
            ", adTableId=" + getAdTableId() +
            "}";
    }
}
