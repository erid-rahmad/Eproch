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
import javax.validation.constraints.Size;

import com.bhp.opusb.domain.enumeration.PaDashboardItemType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The PaDashboardItem entity.\n@author Ananta Aryadewa
 */
@Entity
@Table(name = "pa_dashboard_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaDashboardItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "column_no", nullable = false)
    private Integer columnNo;

    @NotNull
    @Column(name = "row_no", nullable = false)
    private Integer rowNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaDashboardItemType type;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("paDashboardItems")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("paDashboardItems")
    private AdWatchList adWatchList;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("paDashboardItems")
    private PaDashboard paDashboard;

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

    public PaDashboardItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PaDashboardItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getColumnNo() {
        return columnNo;
    }

    public PaDashboardItem columnNo(Integer columnNo) {
        this.columnNo = columnNo;
        return this;
    }

    public void setColumnNo(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public PaDashboardItem rowNo(Integer rowNo) {
        this.rowNo = rowNo;
        return this;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public PaDashboardItemType getType() {
        return type;
    }

    public PaDashboardItem type(PaDashboardItemType type) {
        this.type = type;
        return this;
    }

    public void setType(PaDashboardItemType type) {
        this.type = type;
    }

    public UUID getUid() {
        return uid;
    }

    public PaDashboardItem uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public PaDashboardItem active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public PaDashboardItem adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdWatchList getAdWatchList() {
        return adWatchList;
    }

    public PaDashboardItem adWatchList(AdWatchList adWatchList) {
        this.adWatchList = adWatchList;
        return this;
    }

    public void setAdWatchList(AdWatchList adWatchList) {
        this.adWatchList = adWatchList;
    }

    public PaDashboard getPaDashboard() {
        return paDashboard;
    }

    public PaDashboardItem paDashboard(PaDashboard paDashboard) {
        this.paDashboard = paDashboard;
        return this;
    }

    public void setPaDashboard(PaDashboard paDashboard) {
        this.paDashboard = paDashboard;
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
        if (!(o instanceof PaDashboardItem)) {
            return false;
        }
        return id != null && id.equals(((PaDashboardItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaDashboardItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", columnNo=" + getColumnNo() +
            ", rowNo=" + getRowNo() +
            ", type='" + getType() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
