package com.bhp.opusb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CBiddingSubCriteria} entity.
 */
public class CBiddingSubCriteriaDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String evaluationType;

    private Boolean multipleOptions;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingCriteriaId;
    private String biddingCriteriaName;
    private List<CBiddingSubCriteriaLineDTO> criteriaLineDTO;

    public List<CBiddingSubCriteriaLineDTO> getCriteriaLineDTO() {
        return criteriaLineDTO;
    }

    public void setCriteriaLineDTO(List<CBiddingSubCriteriaLineDTO> criteriaLineDTO) {
        this.criteriaLineDTO = criteriaLineDTO;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Boolean isMultipleOptions() {
        return multipleOptions;
    }

    public void setMultipleOptions(Boolean multipleOptions) {
        this.multipleOptions = multipleOptions;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAdOrganizationId() {
        return adOrganizationId;
    }

    public void setAdOrganizationId(Long aDOrganizationId) {
        this.adOrganizationId = aDOrganizationId;
    }

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public Long getBiddingCriteriaId() {
        return biddingCriteriaId;
    }

    public void setBiddingCriteriaId(Long cBiddingCriteriaId) {
        this.biddingCriteriaId = cBiddingCriteriaId;
    }

    public String getBiddingCriteriaName() {
        return biddingCriteriaName;
    }

    public void setBiddingCriteriaName(String biddingCriteriaName) {
        this.biddingCriteriaName = biddingCriteriaName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO = (CBiddingSubCriteriaDTO) o;
        if (cBiddingSubCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cBiddingSubCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CBiddingSubCriteriaDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", evaluationType='" + evaluationType + '\'' +
            ", multipleOptions=" + multipleOptions +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", biddingCriteriaId=" + biddingCriteriaId +
            ", biddingCriteriaName='" + biddingCriteriaName + '\'' +
            ", criteriaLineDTO=" + criteriaLineDTO +
            '}';
    }
}
