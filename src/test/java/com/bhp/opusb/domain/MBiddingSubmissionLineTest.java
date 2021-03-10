package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubmissionLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubmissionLine.class);
        MBiddingSubmissionLine mBiddingSubmissionLine1 = new MBiddingSubmissionLine();
        mBiddingSubmissionLine1.setId(1L);
        MBiddingSubmissionLine mBiddingSubmissionLine2 = new MBiddingSubmissionLine();
        mBiddingSubmissionLine2.setId(mBiddingSubmissionLine1.getId());
        assertThat(mBiddingSubmissionLine1).isEqualTo(mBiddingSubmissionLine2);
        mBiddingSubmissionLine2.setId(2L);
        assertThat(mBiddingSubmissionLine1).isNotEqualTo(mBiddingSubmissionLine2);
        mBiddingSubmissionLine1.setId(null);
        assertThat(mBiddingSubmissionLine1).isNotEqualTo(mBiddingSubmissionLine2);
    }
}
