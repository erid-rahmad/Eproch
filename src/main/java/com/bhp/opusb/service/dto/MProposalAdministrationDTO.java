package com.bhp.opusb.service.dto;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalAdministration} entity.
 */
public class MProposalAdministrationDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private String answer;

    private Boolean documentEvaluation;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long biddingSubmissionId;
    private String biddingSubmissionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isDocumentEvaluation() {
        return documentEvaluation;
    }

    public void setDocumentEvaluation(Boolean documentEvaluation) {
        this.documentEvaluation = documentEvaluation;
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

    public Long getBiddingSubmissionId() {
        return biddingSubmissionId;
    }

    public void setBiddingSubmissionId(Long mBiddingSubmissionId) {
        this.biddingSubmissionId = mBiddingSubmissionId;
    }

    public String getBiddingSubmissionName() {
        return biddingSubmissionName;
    }

    public void setBiddingSubmissionName(String biddingSubmissionName) {
        this.biddingSubmissionName = biddingSubmissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MProposalAdministrationDTO mProposalAdministrationDTO = (MProposalAdministrationDTO) o;
        if (mProposalAdministrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mProposalAdministrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MProposalAdministrationDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", documentEvaluation='" + isDocumentEvaluation() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", biddingSubmissionId=" + getBiddingSubmissionId() +
            "}";
    }
}
