package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A CPrequalMethodLine.
 */
@Entity
@Table(name = "c_prequal_method_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPrequalMethodLine extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "type", length = 10, nullable = false)
    private String type;

    @DecimalMax(value = "100")
    @Column(name = "weight", precision = 21, scale = 2)
    private BigDecimal weight;

    @Column(name = "passing_grade", precision = 21, scale = 2)
    private BigDecimal passingGrade;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodLines")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cPrequalMethodLines")
    private CPrequalificationMethod prequalMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public CPrequalMethodLine type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public CPrequalMethodLine weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPassingGrade() {
        return passingGrade;
    }

    public CPrequalMethodLine passingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
        return this;
    }

    public void setPassingGrade(BigDecimal passingGrade) {
        this.passingGrade = passingGrade;
    }

    public UUID getUid() {
        return uid;
    }

    public CPrequalMethodLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public CPrequalMethodLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public CPrequalMethodLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public CPrequalificationMethod getPrequalMethod() {
        return prequalMethod;
    }

    public CPrequalMethodLine prequalMethod(CPrequalificationMethod cPrequalificationMethod) {
        this.prequalMethod = cPrequalificationMethod;
        return this;
    }

    public void setPrequalMethod(CPrequalificationMethod cPrequalificationMethod) {
        this.prequalMethod = cPrequalificationMethod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPrequalMethodLine)) {
            return false;
        }
        return id != null && id.equals(((CPrequalMethodLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPrequalMethodLine{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", weight=" + getWeight() +
            ", passingGrade=" + getPassingGrade() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

    @PrePersist
    public void prePersist() {
        uid = UUID.randomUUID();
    }
}
