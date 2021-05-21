package com.bhp.opusb.service.dto;

import java.util.List;

public class MPreBidMeetingVM extends MPreBidMeetingDTO {

  private List<MPreBidMeetingParticipantDTO> addedAttendees;

  /**
   * List of vendorIds to be removed from the MPreBidMeetingParticipant list.
   */
  private List<Long> removedAttendees;
  
  public List<MPreBidMeetingParticipantDTO> getAddedAttendees() {
    return addedAttendees;
  }

  public void setAddedAttendees(List<MPreBidMeetingParticipantDTO> addedAttendees) {
    this.addedAttendees = addedAttendees;
  }

  public List<Long> getRemovedAttendees() {
    return removedAttendees;
  }

  public void setRemovedAttendees(List<Long> removedAttendees) {
    this.removedAttendees = removedAttendees;
  }
}
