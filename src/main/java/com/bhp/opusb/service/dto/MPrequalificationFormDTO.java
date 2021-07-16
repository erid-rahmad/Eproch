package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.enumeration.MPrequalificationProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationInformation} entity.
 */
public class MPrequalificationFormDTO extends MPrequalificationInformationDTO {
    private static final long serialVersionUID = 1L;
    private MPrequalificationProcess step;

    // Vendor Invitation Step.
    private List<MPrequalificationInvitationDTO> vendorInvitations = new ArrayList<>();
    private List<MPrequalificationInvitationDTO> removedVendorInvitations = new ArrayList<>();
    private List<MPrequalVendorSuggestionDTO> vendorSuggestions = new ArrayList<>();
    private List<MPrequalVendorSuggestionDTO> removedVendorSuggestions = new ArrayList<>();

    // Prequalification Event Step.
    private MPrequalificationEventDTO event = new MPrequalificationEventDTO();

    // Prequalification Schedule Step.
    private List<MPrequalificationScheduleDTO> preqSchedules = new ArrayList<>();
    
    public MPrequalificationProcess getStep() {
        return step;
    }
    public List<MPrequalificationScheduleDTO> getPreqSchedules() {
        return preqSchedules;
    }
    public void setPreqSchedules(List<MPrequalificationScheduleDTO> preqSchedules) {
        this.preqSchedules = preqSchedules;
    }
    public MPrequalificationEventDTO getEvent() {
        return event;
    }
    public void setEvent(MPrequalificationEventDTO event) {
        this.event = event;
    }
    public List<MPrequalVendorSuggestionDTO> getRemovedVendorSuggestions() {
        return removedVendorSuggestions;
    }
    public void setRemovedVendorSuggestions(List<MPrequalVendorSuggestionDTO> removedVendorSuggestions) {
        this.removedVendorSuggestions = removedVendorSuggestions;
    }
    public List<MPrequalVendorSuggestionDTO> getVendorSuggestions() {
        return vendorSuggestions;
    }
    public void setVendorSuggestions(List<MPrequalVendorSuggestionDTO> vendorSuggestions) {
        this.vendorSuggestions = vendorSuggestions;
    }
    public List<MPrequalificationInvitationDTO> getRemovedVendorInvitations() {
        return removedVendorInvitations;
    }
    public void setRemovedVendorInvitations(List<MPrequalificationInvitationDTO> removedVendorInvitations) {
        this.removedVendorInvitations = removedVendorInvitations;
    }
    public List<MPrequalificationInvitationDTO> getVendorInvitations() {
        return vendorInvitations;
    }
    public void setVendorInvitations(List<MPrequalificationInvitationDTO> vendorInvitations) {
        this.vendorInvitations = vendorInvitations;
    }
    public void setStep(MPrequalificationProcess step) {
        this.step = step;
    }
}
