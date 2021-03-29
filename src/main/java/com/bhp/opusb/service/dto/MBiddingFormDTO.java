package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bhp.opusb.domain.enumeration.MBiddingProcess;

public class MBiddingFormDTO extends MBiddingDTO {

  private static final long serialVersionUID = 1L;
  private MBiddingProcess step;

  // Bidding Information Step.
  private List<MBiddingLineDTO> biddingLines = new ArrayList<>();
  private List<MBiddingLineDTO> removedBiddingLines = new ArrayList<>();
  private List<MProjectInformationDTO> projectInformations = new ArrayList<>();
  private List<MProjectInformationDTO> removedProjectInformations = new ArrayList<>();

  // Event Schedule Step.
  private List<MBiddingScheduleDTO> biddingSchedules = new ArrayList<>();
  private List<MBiddingScheduleDTO> removedBiddingSchedules = new ArrayList<>();
  private List<MDocumentScheduleDTO> documentSchedules = new ArrayList<>();
  private List<MDocumentScheduleDTO> removedDocumentSchedules = new ArrayList<>();

  // Vendor Invitation Step.
  private List<MVendorInvitationDTO> vendorInvitations = new ArrayList<>();
  private List<MVendorSuggestionDTO> vendorSuggestions = new ArrayList<>();

  // Vendor Scoring Step.
  private List<MVendorScoringDTO> vendorScorings = new ArrayList<>();

  public MBiddingProcess getStep() {
    return step;
  }

  public void setStep(MBiddingProcess step) {
    this.step = step;
  }

  public List<MBiddingLineDTO> getBiddingLines() {
    return biddingLines;
  }

  public void setBiddingLines(List<MBiddingLineDTO> biddingLines) {
    this.biddingLines = biddingLines;
  }

  public List<MBiddingLineDTO> getRemovedBiddingLines() {
    return removedBiddingLines;
  }

  public void setRemovedBiddingLines(List<MBiddingLineDTO> removedBiddingLines) {
    this.removedBiddingLines = removedBiddingLines;
  }

  public List<MProjectInformationDTO> getProjectInformations() {
    return projectInformations;
  }

  public void setProjectInformations(List<MProjectInformationDTO> projectInformations) {
    this.projectInformations = projectInformations;
  }

  public List<MProjectInformationDTO> getRemovedProjectInformations() {
    return removedProjectInformations;
  }

  public void setRemovedProjectInformations(List<MProjectInformationDTO> removedProjectInformations) {
    this.removedProjectInformations = removedProjectInformations;
  }

  public List<MBiddingScheduleDTO> getBiddingSchedules() {
    return biddingSchedules;
  }

  public void setBiddingSchedules(List<MBiddingScheduleDTO> biddingSchedules) {
    this.biddingSchedules = biddingSchedules;
  }

  public List<MBiddingScheduleDTO> getRemovedBiddingSchedules() {
    return removedBiddingSchedules;
  }

  public void setRemovedBiddingSchedules(List<MBiddingScheduleDTO> removedBiddingSchedules) {
    this.removedBiddingSchedules = removedBiddingSchedules;
  }

  public List<MDocumentScheduleDTO> getDocumentSchedules() {
    return documentSchedules;
  }

  public void setDocumentSchedules(List<MDocumentScheduleDTO> documentSchedules) {
    this.documentSchedules = documentSchedules;
  }

  public List<MDocumentScheduleDTO> getRemovedDocumentSchedules() {
    return removedDocumentSchedules;
  }

  public void setRemovedDocumentSchedules(List<MDocumentScheduleDTO> removedDocumentSchedules) {
    this.removedDocumentSchedules = removedDocumentSchedules;
  }

  public List<MVendorInvitationDTO> getVendorInvitations() {
    return vendorInvitations;
  }

  public void setVendorInvitations(List<MVendorInvitationDTO> vendorInvitations) {
    this.vendorInvitations = vendorInvitations;
  }

  public List<MVendorSuggestionDTO> getVendorSuggestions() {
    return vendorSuggestions;
  }

  public void setVendorSuggestions(List<MVendorSuggestionDTO> vendorSuggestions) {
    this.vendorSuggestions = vendorSuggestions;
  }

  public List<MVendorScoringDTO> getVendorScorings() {
    return vendorScorings;
  }

  public void setVendorScorings(List<MVendorScoringDTO> vendorScorings) {
    this.vendorScorings = vendorScorings;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    MBiddingFormDTO dto = (MBiddingFormDTO) o;
    return this.step.equals(dto.step);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStep());
  }

  @Override
  public String toString() {
      return "MBiddingFormDTO{" +
          "id=" + getId() +
          ", name='" + getName() + "'" +
          ", vendorSelection='" + getVendorSelection() + "'" +
          ", ceilingPrice=" + getCeilingPrice() +
          ", estimatedPrice=" + getEstimatedPrice() +
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
          ", costCenterId=" + getCostCenterId() +
          ", currencyId=" + getCurrencyId() +
          ", requisitionId=" + getRequisitionId() +
          ", referenceTypeId=" + getReferenceTypeId() +
          ", biddingTypeId=" + getBiddingTypeId() +
          ", eventTypeId=" + getEventTypeId() +
          ", adUserId=" + getAdUserUserId() +
          ", step=" + getStep() +
          ", biddingLines=" + getBiddingLines() +
          "}";
  }
}
