package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubmissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubmissionDTO.class);
        MBiddingSubmissionDTO mBiddingSubmissionDTO1 = new MBiddingSubmissionDTO();
        mBiddingSubmissionDTO1.setId(1L);
        MBiddingSubmissionDTO mBiddingSubmissionDTO2 = new MBiddingSubmissionDTO();
        assertThat(mBiddingSubmissionDTO1).isNotEqualTo(mBiddingSubmissionDTO2);
        mBiddingSubmissionDTO2.setId(mBiddingSubmissionDTO1.getId());
        assertThat(mBiddingSubmissionDTO1).isEqualTo(mBiddingSubmissionDTO2);
        mBiddingSubmissionDTO2.setId(2L);
        assertThat(mBiddingSubmissionDTO1).isNotEqualTo(mBiddingSubmissionDTO2);
        mBiddingSubmissionDTO1.setId(null);
        assertThat(mBiddingSubmissionDTO1).isNotEqualTo(mBiddingSubmissionDTO2);
    }
}
