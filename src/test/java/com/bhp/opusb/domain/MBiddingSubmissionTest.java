package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubmissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubmission.class);
        MBiddingSubmission mBiddingSubmission1 = new MBiddingSubmission();
        mBiddingSubmission1.setId(1L);
        MBiddingSubmission mBiddingSubmission2 = new MBiddingSubmission();
        mBiddingSubmission2.setId(mBiddingSubmission1.getId());
        assertThat(mBiddingSubmission1).isEqualTo(mBiddingSubmission2);
        mBiddingSubmission2.setId(2L);
        assertThat(mBiddingSubmission1).isNotEqualTo(mBiddingSubmission2);
        mBiddingSubmission1.setId(null);
        assertThat(mBiddingSubmission1).isNotEqualTo(mBiddingSubmission2);
    }
}
