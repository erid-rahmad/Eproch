package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubmissionLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubmissionLineDTO.class);
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO1 = new MBiddingSubmissionLineDTO();
        mBiddingSubmissionLineDTO1.setId(1L);
        MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO2 = new MBiddingSubmissionLineDTO();
        assertThat(mBiddingSubmissionLineDTO1).isNotEqualTo(mBiddingSubmissionLineDTO2);
        mBiddingSubmissionLineDTO2.setId(mBiddingSubmissionLineDTO1.getId());
        assertThat(mBiddingSubmissionLineDTO1).isEqualTo(mBiddingSubmissionLineDTO2);
        mBiddingSubmissionLineDTO2.setId(2L);
        assertThat(mBiddingSubmissionLineDTO1).isNotEqualTo(mBiddingSubmissionLineDTO2);
        mBiddingSubmissionLineDTO1.setId(null);
        assertThat(mBiddingSubmissionLineDTO1).isNotEqualTo(mBiddingSubmissionLineDTO2);
    }
}
