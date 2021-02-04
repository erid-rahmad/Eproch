package com.bhp.opusb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The AdWatchList entity.\n@author Ananta Aryadewa
 */
@Entity
@Table(name = "ad_watch_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdWatchList extends AbstractAuditingEntity {

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

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "adWatchList")
    @Fetch(FetchMode.JOIN)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private List<AdWatchListItem> adWatchListItems = new ArrayList<>();

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    @JsonIgnoreProperties("adWatchLists")
    private ADOrganization adOrganization;

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

    public AdWatchList name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AdWatchList description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUid() {
        return uid;
    }

    public AdWatchList uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public AdWatchList active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<AdWatchListItem> getAdWatchListItems() {
        return adWatchListItems;
    }

    public AdWatchList adWatchListItems(List<AdWatchListItem> adWatchListItems) {
        this.adWatchListItems = adWatchListItems;
        return this;
    }

    public AdWatchList addAdWatchListItem(AdWatchListItem adWatchListItem) {
        this.adWatchListItems.add(adWatchListItem);
        adWatchListItem.setAdWatchList(this);
        return this;
    }

    public AdWatchList removeAdWatchListItem(AdWatchListItem adWatchListItem) {
        this.adWatchListItems.remove(adWatchListItem);
        adWatchListItem.setAdWatchList(null);
        return this;
    }

    public void setAdWatchListItems(List<AdWatchListItem> adWatchListItems) {
        this.adWatchListItems = adWatchListItems;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public AdWatchList adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
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
        if (!(o instanceof AdWatchList)) {
            return false;
        }
        return id != null && id.equals(((AdWatchList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdWatchList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
