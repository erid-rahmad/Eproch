package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvalResult} entity.
 */
public class MPrequalResultPublish implements Serializable {

    private MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO;
    private List<MPrequalificationSubmissionDTO> mPrequalificationSubmission;
    private List<AdUserDTO> users;

    public List<AdUserDTO> getUsers() {
        return users;
    }

    public List<MPrequalificationSubmissionDTO> getmPrequalificationSubmission() {
        return mPrequalificationSubmission;
    }

    public void setmPrequalificationSubmission(List<MPrequalificationSubmissionDTO> mPrequalificationSubmission) {
        this.mPrequalificationSubmission = mPrequalificationSubmission;
    }

    public MPrequalAnnouncementResultDTO getmPrequalAnnouncementResultDTO() {
        return mPrequalAnnouncementResultDTO;
    }

    public void setmPrequalAnnouncementResultDTO(MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO) {
        this.mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultDTO;
    }

    public void setUsers(List<AdUserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "MBiddingResultPublish{" +
            "mPrequalAnnouncementResultDTO=" + mPrequalAnnouncementResultDTO +
            ", mPrequalificationSubmission=" + mPrequalificationSubmission +
            ", users=" + users +
            '}';
    }
}
