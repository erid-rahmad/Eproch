package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.CEvaluationMethodCriteria} entity.
 */
public class CEvaluationMethodCriteriaDTO implements Serializable {

    private Long id;

    private UUID uid;

    private Boolean active;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long evaluationMethodLineId;
    private String evaluationMethodLineName;

    public String getAdOrganizationName() {
        return adOrganizationName;
    }

    public void setAdOrganizationName(String adOrganizationName) {
        this.adOrganizationName = adOrganizationName;
    }

    public String getEvaluationMethodLineName() {
        return evaluationMethodLineName;
    }

    public void setEvaluationMethodLineName(String evaluationMethodLineName) {
        this.evaluationMethodLineName = evaluationMethodLineName;
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

    public Long getEvaluationMethodLineId() {
        return evaluationMethodLineId;
    }

    public void setEvaluationMethodLineId(Long cEvaluationMethodLineId) {
        this.evaluationMethodLineId = cEvaluationMethodLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO = (CEvaluationMethodCriteriaDTO) o;
        if (cEvaluationMethodCriteriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cEvaluationMethodCriteriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEvaluationMethodCriteriaDTO{" +
            "id=" + id +
            ", uid=" + uid +
            ", active=" + active +
            ", adOrganizationId=" + adOrganizationId +
            ", adOrganizationName='" + adOrganizationName + '\'' +
            ", evaluationMethodLineId=" + evaluationMethodLineId +
            ", evaluationMethodLineName='" + evaluationMethodLineName + '\'' +
            '}';
    }
}
