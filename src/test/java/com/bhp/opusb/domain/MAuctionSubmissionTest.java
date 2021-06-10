package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmission.class);
        MAuctionSubmission mAuctionSubmission1 = new MAuctionSubmission();
        mAuctionSubmission1.setId(1L);
        MAuctionSubmission mAuctionSubmission2 = new MAuctionSubmission();
        mAuctionSubmission2.setId(mAuctionSubmission1.getId());
        assertThat(mAuctionSubmission1).isEqualTo(mAuctionSubmission2);
        mAuctionSubmission2.setId(2L);
        assertThat(mAuctionSubmission1).isNotEqualTo(mAuctionSubmission2);
        mAuctionSubmission1.setId(null);
        assertThat(mAuctionSubmission1).isNotEqualTo(mAuctionSubmission2);
    }
}
