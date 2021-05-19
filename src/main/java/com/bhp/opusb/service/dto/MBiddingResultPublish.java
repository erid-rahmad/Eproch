package com.bhp.opusb.service.dto;

import com.bhp.opusb.domain.MBiddingEvalResult;

import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBiddingEvalResult} entity.
 */
public class MBiddingResultPublish implements Serializable {

    CAnnouncementResultDTO cAnnouncementResultDTO;
    List<MBiddingEvalResultDTO> mBiddingEvalResult;
    private List<AdUserDTO> users;

    public List<AdUserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<AdUserDTO> users) {
        this.users = users;
    }

    public CAnnouncementResultDTO getcAnnouncementResultDTO() {
        return cAnnouncementResultDTO;
    }

    public void setcAnnouncementResultDTO(CAnnouncementResultDTO cAnnouncementResultDTO) {
        this.cAnnouncementResultDTO = cAnnouncementResultDTO;
    }

    public List<MBiddingEvalResultDTO> getmBiddingEvalResult() {
        return mBiddingEvalResult;
    }

    public void setmBiddingEvalResult(List<MBiddingEvalResultDTO> mBiddingEvalResult) {
        this.mBiddingEvalResult = mBiddingEvalResult;
    }

    @Override
    public String toString() {
        return "MBiddingResultPublish{" +
            "cAnnouncementResultDTO=" + cAnnouncementResultDTO +
            ", mBiddingEvalResult=" + mBiddingEvalResult +
            ", users=" + users +
            '}';
    }
}
