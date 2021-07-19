package com.bhp.opusb.service.dto;

import java.util.List;

public class MPrequalAnnouncementPublishDTO {
  private MPrequalAnnouncementDTO announcement;
  private MPrequalificationInformationDTO prequalification;
  private List<AdUserDTO> users;
  private List<MPrequalVendorSuggestionDTO> vendor;

  public List<MPrequalVendorSuggestionDTO> getVendor() {
    return vendor;
  }

  public void setVendor(List<MPrequalVendorSuggestionDTO> vendor) {
    this.vendor = vendor;
  }

  public MPrequalAnnouncementDTO getAnnouncement() {
    return announcement;
  }

  public void setAnnouncement(MPrequalAnnouncementDTO announcement) {
    this.announcement = announcement;
  }

  public MPrequalificationInformationDTO getPrequalification() {
    return prequalification;
  }

  public void setPrequalification(MPrequalificationInformationDTO prequalification) {
    this.prequalification = prequalification;
  }

  public List<AdUserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<AdUserDTO> users) {
    this.users = users;
  }
}
