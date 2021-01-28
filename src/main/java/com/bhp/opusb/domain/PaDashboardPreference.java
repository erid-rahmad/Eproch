package com.bhp.opusb.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Per-user dashboard preference.\n@author Ananta Aryadewa
 */
@Entity
@Table(name = "pa_dashboard_preference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaDashboardPreference extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "column_no", nullable = false)
    private Integer columnNo;

    @NotNull
    @Column(name = "row_no", nullable = false)
    private Integer rowNo;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("paDashboardPreferences")
    private ADOrganization adOrganization;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("paDashboardPreferences")
    private AdUser adUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("paDashboardPreferences")
    private PaDashboardItem paDashboardItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public PaDashboardPreference columnNo(Integer columnNo) {
        this.columnNo = columnNo;
        return this;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public PaDashboardPreference rowNo(Integer rowNo) {
        this.rowNo = rowNo;
        return this;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public UUID getUid() {
        return uid;
    }

    public PaDashboardPreference uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public PaDashboardPreference active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public PaDashboardPreference adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public PaDashboardPreference adUser(AdUser adUser) {
        this.adUser = adUser;
        return this;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }

    public PaDashboardItem getPaDashboardItem() {
        return paDashboardItem;
    }

    public PaDashboardPreference paDashboardItem(PaDashboardItem paDashboardItem) {
        this.paDashboardItem = paDashboardItem;
        return this;
    }

    public void setPaDashboardItem(PaDashboardItem paDashboardItem) {
        this.paDashboardItem = paDashboardItem;
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
        if (!(o instanceof PaDashboardPreference)) {
            return false;
        }
        return id != null && id.equals(((PaDashboardPreference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaDashboardPreference{" +
            "id=" + getId() +
            ", columnNo=" + getColumnNo() +
            ", rowNo=" + getRowNo() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
