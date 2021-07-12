package com.bhp.opusb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A MBlacklistLine.
 */
@Entity
@Table(name = "m_blacklist_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MBlacklistLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "active")
    private Boolean active;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "notes")
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBlacklistLines")
    private MBlacklist blacklist;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mBlacklistLines")
    private ADOrganization adOrganization;

    @ManyToOne
    @JsonIgnoreProperties("mBlacklistLines")
    private AdUser pic;

    @ManyToOne
    @JsonIgnoreProperties("mBlacklistLines")
    private CFunctionary functionary;

    @PrePersist
    public void assignUUID() {
        this.uid = UUID.randomUUID();
        this.active = true;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public MBlacklistLine uid(UUID uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public MBlacklistLine active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public MBlacklistLine notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MBlacklist getBlacklist() {
        return blacklist;
    }

    public MBlacklistLine blacklist(MBlacklist mBlacklist) {
        this.blacklist = mBlacklist;
        return this;
    }

    public void setBlacklist(MBlacklist mBlacklist) {
        this.blacklist = mBlacklist;
    }

    public ADOrganization getAdOrganization() {
        return adOrganization;
    }

    public MBlacklistLine adOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
        return this;
    }

    public void setAdOrganization(ADOrganization aDOrganization) {
        this.adOrganization = aDOrganization;
    }

    public AdUser getPic() {
        return pic;
    }

    public MBlacklistLine pic(AdUser adUser) {
        this.pic = adUser;
        return this;
    }

    public void setPic(AdUser adUser) {
        this.pic = adUser;
    }

    public CFunctionary getFunctionary() {
        return functionary;
    }

    public MBlacklistLine functionary(CFunctionary cFunctionary) {
        this.functionary = cFunctionary;
        return this;
    }

    public void setFunctionary(CFunctionary cFunctionary) {
        this.functionary = cFunctionary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MBlacklistLine)) {
            return false;
        }
        return id != null && id.equals(((MBlacklistLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MBlacklistLine{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
