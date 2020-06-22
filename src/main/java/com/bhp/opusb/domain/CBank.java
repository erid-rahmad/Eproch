package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CBank.
 */
@Entity
@Table(name = "c_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CBank extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "description")
    private String description;

    @Pattern(regexp = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$")
    @Column(name = "swift_code", unique = true)
    private String swiftCode;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CBank name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public CBank value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShortName() {
        return shortName;
    }

    public CBank shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public CBank description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public CBank swiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
        return this;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public Boolean isActive() {
        return active;
    }

    public CBank active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CBank)) {
            return false;
        }
        return id != null && id.equals(((CBank) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CBank{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", swiftCode='" + getSwiftCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
