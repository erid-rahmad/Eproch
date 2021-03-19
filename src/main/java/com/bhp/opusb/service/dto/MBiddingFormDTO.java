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

  // Vendor Invitation Step.
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
}