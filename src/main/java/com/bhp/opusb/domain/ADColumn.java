package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
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

    @Column(name = "uid")
    private UUID uid;

    /**
     * The JPA entity field name (usually in a camelCased format). It's
     * automatically filled by the dbSync process.
     * updatable = false.
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The native database column name (usually in a kebab_cased format). It's
     * automatically filled by the dbSync process.
     * updatable = false.
     */
    @NotNull
    @Column(name = "sql_name", nullable = false)
    private String sqlName;

    @Column(name = "description")
    private String description;

    @Column(name = "field_length")
    private Long fieldLength;

    /**
     * Whether it is a primary key column or not. It's automatically filled by the
     * dbSync process.
     * updatable = false.
     */
    @Column(name = "key")
    private Boolean key;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ADColumnType type;

    /**
     * Whether it is a foreign key column or not. It's automatically filled by the
     * dbSync process.
     * updatable = false.
     */
    @Column(name = "foreign_key")
    private Boolean foreignKey;

    /**
     * The name of the linked primary key table if foreignKey is true. It's
     * automatically filled by the dbSync process.
     * updatable = false.
     */
    @Column(name = "imported_table")
    private String importedTable;

    /**
     * The column name of the linked primary key if foreignKey is true. It's
     * automatically filled by the dbSync process.
     * updatable = false.
     */
    @Column(name = "imported_column")
    private String importedColumn;

    /**
     * It's automatically filled by the dbSync process.
     * updatable = false.
     */
    @Column(name = "mandatory")
    private Boolean mandatory;

    /**
     * This should be displayed only if mandatory = false.
     */
    @Column(name = "mandatory_logic")
    private String mandatoryLogic;

    @Column(name = "display_logic")
    private String displayLogic;

    @Column(name = "read_only_logic")
    private String readOnlyLogic;

    @Column(name = "updatable")
    private Boolean updatable = true;

    @Column(name = "always_updatable")
    private Boolean alwaysUpdatable;

    @Column(name = "copyable")
    private Boolean copyable = true;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "format_pattern")
    private String formatPattern;

    /**
     * The minimum character length of the string column.
     */
    @Column(name = "min_length")
    private Integer minLength;

    /**
     * The maximum character length of the string column.
     */
    @Column(name = "max_length")
    private Integer maxLength;

    /**
     * The minimum number value of the numeric column.
     */
    @Column(name = "min_value")
    private Long minValue;

    /**
     * The minimum number value of the numeric column.
     */
    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "identifier")
    private Boolean identifier;

    /**
     * Determine that the field is displayed by default in the search form.
     */
    @Column(name = "default_selection")
    private Boolean defaultSelection;

    /**
     * Determine the field sequence in the search form.
     */
    @Column(name = "selection_sequence")
    private Integer selectionSequence;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aDColumns")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("aDColumns")
    private ADReference adReference;

    @ManyToOne
    @JsonIgnoreProperties("aDColumns")
    private AdValidationRule adValidationRule;

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

    public UUID getUid() {
        return uid;
    }

    public ADColumn uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
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

    public Boolean isForeignKey() {
        return foreignKey;
    }

    public ADColumn foreignKey(Boolean foreignKey) {
        this.foreignKey = foreignKey;
        return this;
    }

    public void setForeignKey(Boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getImportedTable() {
        return importedTable;
    }

    public ADColumn importedTable(String importedTable) {
        this.importedTable = importedTable;
        return this;
    }

    public void setImportedTable(String importedTable) {
        this.importedTable = importedTable;
    }

    public String getImportedColumn() {
        return importedColumn;
    }

    public ADColumn importedColumn(String importedColumn) {
        this.importedColumn = importedColumn;
        return this;
    }

    public void setImportedColumn(String importedColumn) {
        this.importedColumn = importedColumn;
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

    public String getDisplayLogic() {
        return displayLogic;
    }

    public ADColumn displayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
        return this;
    }

    public void setDisplayLogic(String displayLogic) {
        this.displayLogic = displayLogic;
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

    public Boolean isAlwaysUpdatable() {
        return alwaysUpdatable;
    }

    public ADColumn alwaysUpdatable(Boolean alwaysUpdatable) {
        this.alwaysUpdatable = alwaysUpdatable;
        return this;
    }

    public void setAlwaysUpdatable(Boolean alwaysUpdatable) {
        this.alwaysUpdatable = alwaysUpdatable;
    }

    public Boolean isCopyable() {
        return copyable;
    }

    public ADColumn copyable(Boolean copyable) {
        this.copyable = copyable;
        return this;
    }

    public void setCopyable(Boolean copyable) {
        this.copyable = copyable;
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

    public Boolean isIdentifier() {
        return identifier;
    }

    public ADColumn identifier(Boolean identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(Boolean identifier) {
        this.identifier = identifier;
    }

    public Boolean isDefaultSelection() {
        return defaultSelection;
    }

    public ADColumn defaultSelection(Boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
        return this;
    }

    public void setDefaultSelection(Boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public Integer getSelectionSequence() {
        return selectionSequence;
    }

    public ADColumn selectionSequence(Integer selectionSequence) {
        this.selectionSequence = selectionSequence;
        return this;
    }

    public void setSelectionSequence(Integer selectionSequence) {
        this.selectionSequence = selectionSequence;
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

    public AdValidationRule getAdValidationRule() {
        return adValidationRule;
    }

    public ADColumn adValidationRule(AdValidationRule adValidationRule) {
        this.adValidationRule = adValidationRule;
        return this;
    }

    public void setAdValidationRule(AdValidationRule adValidationRule) {
        this.adValidationRule = adValidationRule;
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

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
    }

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
            ", uid='" + getUid() + "'" +
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
            "}";
    }
}
