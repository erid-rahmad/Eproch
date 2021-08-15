package com.bhp.opusb.service.dto;

import java.io.Serializable;
import java.util.List;

public class MRfqViewDetailDTO implements Serializable {
    private List<MRfqSubmissionDTO> submissions;

    public List<MRfqSubmissionDTO> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<MRfqSubmissionDTO> submissions) {
        this.submissions = submissions;
    }
}