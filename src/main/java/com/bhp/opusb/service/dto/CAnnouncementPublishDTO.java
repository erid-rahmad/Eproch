package com.bhp.opusb.service.dto;

import java.util.List;

public class CAnnouncementPublishDTO {
  private CAnnouncementDTO announcement;
  private MBiddingDTO bidding;
  private List<AdUserDTO> users;

  public CAnnouncementDTO getAnnouncement() {
    return announcement;
  }

  public void setAnnouncement(CAnnouncementDTO announcement) {
    this.announcement = announcement;
  }

  public MBiddingDTO getBidding() {
    return bidding;
  }

  public void setBidding(MBiddingDTO bidding) {
    this.bidding = bidding;
  }

  public List<AdUserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<AdUserDTO> users) {
    this.users = users;
  }
}
