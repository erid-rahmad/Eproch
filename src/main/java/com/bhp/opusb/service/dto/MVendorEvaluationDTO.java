package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MVendorEvaluation} entity.
 */
public class MVendorEvaluationDTO extends AbstractAuditingDTO {
    
    private Long id;

    @NotNull
    private BigDecimal score = new BigDecimal(0);

    private LocalDate evaluationDate;

    private ZonedDateTime dateTrx = ZonedDateTime.now();

    @Size(max = 30)
    private String documentNo;

    @NotNull
    @Size(max = 10)
    private String documentAction = "SMT";

    @NotNull
    @Size(max = 12)
    private String documentStatus = "DRF";

    private Boolean approved;

    private Boolean processed;

    private ZonedDateTime dateApprove;

    private ZonedDateTime dateReject;

    private String rejectedReason;

    private UUID uid;

    private Boolean active = true;


    private Long adOrganizationId;
    private String adOrganizationName;

    private Long contractId;
    private String contractNo;
    private String contractName;
    private Long evaluationTypeId;
    private String evaluationTypeName;
    private String contractEvaluationPeriod;

    private Long reviewerUserId;
    private String reviewerUserName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public ZonedDateTime getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(ZonedDateTime dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentAction() {
        return documentAction;
    }

    public void setDocumentAction(String documentAction) {
        this.documentAction = documentAction;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(ZonedDateTime dateApprove) {
        this.dateApprove = dateApprove;
    }

    public ZonedDateTime getDateReject() {
        return dateReject;
    }

    public void setDateReject(ZonedDateTime dateReject) {
        this.dateReject = dateReject;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long mContractId) {
        this.contractId = mContractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getEvaluationTypeId() {
        return evaluationTypeId;
    }

    public void setEvaluationTypeId(Long evaluationTypeId) {
        this.evaluationTypeId = evaluationTypeId;
    }

    public String getEvaluationTypeName() {
        return evaluationTypeName;
    }

    public void setEvaluationTypeName(String evaluationTypeName) {
        this.evaluationTypeName = evaluationTypeName;
    }

    public String getContractEvaluationPeriod() {
        return contractEvaluationPeriod;
    }

    public void setContractEvaluationPeriod(String contractEvaluationPeriod) {
        this.contractEvaluationPeriod = contractEvaluationPeriod;
    }

    public Long getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId(Long adUserId) {
        this.reviewerUserId = adUserId;
    }

    public String getReviewerUserName() {
        return reviewerUserName;
    }

    public void setReviewerUserName(String reviewerUserName) {
        this.reviewerUserName = reviewerUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MVendorEvaluationDTO mVendorEvaluationDTO = (MVendorEvaluationDTO) o;
        if (mVendorEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mVendorEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MVendorEvaluationDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", evaluationDate='" + getEvaluationDate() + "'" +
            ", dateTrx='" + getDateTrx() + "'" +
            ", documentNo='" + getDocumentNo() + "'" +
            ", documentAction='" + getDocumentAction() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", approved='" + isApproved() + "'" +
            ", processed='" + isProcessed() + "'" +
            ", dateApprove='" + getDateApprove() + "'" +
            ", dateReject='" + getDateReject() + "'" +
            ", rejectedReason='" + getRejectedReason() + "'" +
            ", uid='" + getUid() + "'" +
            ", active='" + isActive() + "'" +
            ", adOrganizationId=" + getAdOrganizationId() +
            ", contractId=" + getContractId() +
            ", reviewerId=" + getReviewerUserId() +
            "}";
    }
}
