package com.bhp.opusb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bhp.opusb.domain.enumeration.ADColumnType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ADColumn.
 */
@Entity
@Table(name = "ad_column")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADColumn extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "sql_name", nullable = false)
    private String sqlName;

    @Column(name = "description")
    private String description;

    @Column(name = "field_length")
    private Long fieldLength;

    @Column(name = "key")
    private Boolean key;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ADColumnType type;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "mandatory_logic")
    private String mandatoryLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "updatable")
    private Boolean updatable;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "format_pattern")
    private String formatPattern;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDColumns")
    private ADClient adClient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDColumns")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("aDColumns")
    private ADReference adReference;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDColumns")
    @JsonBackReference
    private ADTable adTable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public ADColumn() {}

    public ADColumn(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ADColumn name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqlName() {
        return sqlName;
    }

    public ADColumn sqlName(String sqlName) {
        this.sqlName = sqlName;
        return this;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getDescription() {
        return description;
    }

    public ADColumn description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFieldLength() {
        return fieldLength;
    }

    public ADColumn fieldLength(Long fieldLength) {
        this.fieldLength = fieldLength;
        return this;
    }

    public void setFieldLength(Long fieldLength) {
        this.fieldLength = fieldLength;
    }

    public Boolean isKey() {
        return key;
    }

    public ADColumn key(Boolean key) {
        this.key = key;
        return this;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public ADColumnType getType() {
        return type;
    }

    public ADColumn type(ADColumnType type) {
        this.type = type;
        return this;
    }

    public void setType(ADColumnType type) {
        this.type = type;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public ADColumn mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMandatoryLogic() {
        return mandatoryLogic;
    }

    public ADColumn mandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
        return this;
    }

    public void setMandatoryLogic(String mandatoryLogic) {
        this.mandatoryLogic = mandatoryLogic;
    }

    public String getReadOnlyLogic() {
        return readOnlyLogic;
    }

    public ADColumn readOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
        return this;
    }

    public void setReadOnlyLogic(String readOnlyLogic) {
        this.readOnlyLogic = readOnlyLogic;
    }

    public Boolean isUpdatable() {
        return updatable;
    }

    public ADColumn updatable(Boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ADColumn defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public ADColumn formatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
        return this;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public ADColumn minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public ADColumn maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Long getMinValue() {
        return minValue;
    }

    public ADColumn minValue(Long minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public ADColumn maxValue(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean isActive() {
        return active;
    }

    public ADColumn active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADClient getAdClient() {
        return adClient;
    }

    public ADColumn adClient(ADClient aDClient) {
        this.adClient = aDClient;
        return this;
    }

    public void setAdClient(ADClient aDClient) {
        this.adClient = aDClient;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public ADColumn adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public ADReference getAdReference() {
        return adReference;
    }

    public ADColumn adReference(ADReference aDReference) {
        this.adReference = aDReference;
        return this;
    }

    public void setAdReference(ADReference aDReference) {
        this.adReference = aDReference;
    }

    public ADTable getAdTable() {
        return adTable;
    }

    public ADColumn adTable(ADTable aDTable) {
        this.adTable = aDTable;
        return this;
    }

    public void setAdTable(ADTable aDTable) {
        this.adTable = aDTable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ADColumn)) {
            return false;
        }
        return id != null && id.equals(((ADColumn) o).id)
            && (name == null || name.equals(((ADColumn) o).name));
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADColumn{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sqlName='" + getSqlName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fieldLength=" + getFieldLength() +
            ", key='" + isKey() + "'" +
            ", type='" + getType() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", mandatoryLogic='" + getMandatoryLogic() + "'" +
            ", readOnlyLogic='" + getReadOnlyLogic() + "'" +
            ", updatable='" + isUpdatable() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", formatPattern='" + getFormatPattern() + "'" +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
