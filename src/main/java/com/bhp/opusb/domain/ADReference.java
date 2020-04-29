package com.bhp.opusb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.bhp.opusb.domain.enumeration.ADReferenceType;

/**
 * A ADReference.
 */
@Entity
@Table(name = "ad_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADReference extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ADReferenceType referenceType;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "adReference")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ADReferenceList> aDReferenceLists = new HashSet<>();

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

    public ADReference name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public ADReference value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public ADReference description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ADReferenceType getReferenceType() {
        return referenceType;
    }

    public ADReference referenceType(ADReferenceType referenceType) {
        this.referenceType = referenceType;
        return this;
    }

    public void setReferenceType(ADReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean isActive() {
        return active;
    }

    public ADReference active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ADReferenceList> getADReferenceLists() {
        return aDReferenceLists;
    }

    public ADReference aDReferenceLists(Set<ADReferenceList> aDReferenceLists) {
        this.aDReferenceLists = aDReferenceLists;
        return this;
    }

    public ADReference addADReferenceList(ADReferenceList aDReferenceList) {
        this.aDReferenceLists.add(aDReferenceList);
        aDReferenceList.setAdReference(this);
        return this;
    }

    public ADReference removeADReferenceList(ADReferenceList aDReferenceList) {
        this.aDReferenceLists.remove(aDReferenceList);
        aDReferenceList.setAdReference(null);
        return this;
    }

    public void setADReferenceLists(Set<ADReferenceList> aDReferenceLists) {
        this.aDReferenceLists = aDReferenceLists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ADReference)) {
            return false;
        }
        return id != null && id.equals(((ADReference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ADReference{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", referenceType='" + getReferenceType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
